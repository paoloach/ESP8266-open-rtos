package esp8266.plugin.achdjian.it.ui

import com.intellij.BundleBase
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.layout.Row
import java.awt.GridLayout
import java.awt.event.ItemEvent
import javax.swing.JCheckBox
import javax.swing.JPanel

fun panel(title: String? = null, init : LayoutBuilder.()->Any): JPanel {
    val panel = JPanel()
    title?.let{ addTitleBorder(it, panel) }
    val layoutBuilder = LayoutBuilder()
    layoutBuilder.init()
    layoutBuilder.build(panel)
    return panel
}

fun addTitleBorder(title: String, panel: JPanel) {
    val border = IdeBorderFactory.createTitledBorder(title, false)
    panel.border = border
    border.acceptMinimumSize(panel)
}


