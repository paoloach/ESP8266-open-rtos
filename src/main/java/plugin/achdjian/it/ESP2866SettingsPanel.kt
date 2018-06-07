package plugin.achdjian.it

import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.uiDesigner.core.GridLayoutManager
import javax.swing.JFileChooser
import javax.swing.JPanel

class ESP2866SettingsPanel : JPanel(GridLayoutManager(6, 3), true) {
    val sdkHome: TextFieldWithBrowseButton? = null
    init {
        (layout as GridLayoutManager).setColumnStretch(1, 10)
    }
}