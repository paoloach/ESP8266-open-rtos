package esp8266.plugin.achdjian.it.ui

import com.intellij.ui.IdeBorderFactory.*
import com.intellij.ui.TitledSeparator
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.UIUtil
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.border.TitledBorder

class TitleLabel : JBLabel() {
    override fun getFont(): Font {
        return UIUtil.getTitledBorderFont()
    }
}


class ButtonTitleBorder(title: String, val parent: Component, val statusChange: (close: Boolean) -> Any) : TitledBorder(title), MouseListener {

    override fun getBorderInsets(p0: Component?): Insets {
        val insets = Insets(inset.top + label.preferredSize.height, inset.left, inset.bottom, inset.right)
        println("insets: $insets")
        return insets
    }

    override fun isBorderOpaque(): Boolean = true

    companion object {
        const val CLOSE_IMG = "/images/drop-right-arrow.png"
        const val OPEN_IMG = "/images/drop-down-arrow.png"

    }

    private val label = TitleLabel()
    val button: JButton
    private val separator = JSeparator(SwingConstants.HORIZONTAL)
    private val closeImg: ImageIcon
    private val openImg: ImageIcon
    private var close = true
    private val inset = Insets(TITLED_BORDER_TOP_INSET, TITLED_BORDER_LEFT_INSET, TITLED_BORDER_BOTTOM_INSET, TITLED_BORDER_RIGHT_INSET)
    private var deltaButtonY=0
    init {
        label.text = title
        val imgSize = label.preferredSize.height / 2
        closeImg = ImageIcon(ImageIO.read(ButtonTitleBorder::class.java.classLoader.getResourceAsStream(CLOSE_IMG)).getScaledInstance(imgSize, imgSize, Image.SCALE_SMOOTH))
        openImg = ImageIcon(ImageIO.read(ButtonTitleBorder::class.java.classLoader.getResourceAsStream(OPEN_IMG)).getScaledInstance(imgSize, imgSize, Image.SCALE_SMOOTH))

        button = createButton(imgSize)
        parent.addMouseListener(this)
    }

    override fun paintBorder(c: Component, graphics: Graphics, x: Int, y: Int, width: Int, height: Int) {

        val labelSize = label.preferredSize
        label.size = labelSize

        deltaButtonY = inset.top+labelSize.height/2-button.height/2
        val buttonX = x + inset.left
        val buttonY = y + deltaButtonY

        graphics.translate(buttonX, buttonY)
        button.paint(graphics)

        val labelX = buttonX + button.width

        graphics.translate(labelX, -(labelSize.height/2-button.height/2))
        label.paint(graphics)

        val separatorX = labelX + labelSize.width + TitledSeparator.SEPARATOR_LEFT_INSET
        val separatorY = buttonY + if (UIUtil.isUnderAquaLookAndFeel()) 2 else labelSize.height / 2 - 1
        val separatorW = Math.max(0, width - separatorX - TitledSeparator.SEPARATOR_RIGHT_INSET)
        val separatorH = 2

        separator.setSize(separatorW, separatorH)
        graphics.translate(separatorX - labelX, separatorY - buttonY)
        separator.paint(graphics)
        graphics.translate(-separatorX, -separatorY)
    }

    private fun createButton(imgSize: Int): JButton {

        val button = JButton(closeImg)
        button.setBorder(BorderFactory.createEmptyBorder())
        button.setContentAreaFilled(false)
        button.size = Dimension(imgSize, imgSize)
        return button
    }

    private fun dispatchMouseEvent(event: MouseEvent) {
        if (button.visibleRect.contains(Point(event.point.x-inset.left, event.point.y-deltaButtonY))){
            if (close) {
                button.icon = openImg
                close = false
            } else {
                button.icon = closeImg
                close = true
            }
            parent.repaint()
            statusChange(close)
        }
    }

    override fun mouseReleased(event: MouseEvent) {}
    override fun mouseEntered(event: MouseEvent) {}
    override fun mouseClicked(event: MouseEvent) = dispatchMouseEvent(event)
    override fun mouseExited(event: MouseEvent) {}
    override fun mousePressed(event: MouseEvent){}
}