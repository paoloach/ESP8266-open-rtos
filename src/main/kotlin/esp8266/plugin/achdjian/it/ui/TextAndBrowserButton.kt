package esp8266.plugin.achdjian.it.ui

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.TextComponentAccessor
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.TextFieldWithHistoryWithBrowseButton
import com.intellij.ui.components.installFileCompletionAndBrowseDialog
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel

class TextAndBrowserButton(text: String, folderSelected: (virtualFile: VirtualFile) -> Unit) : JPanel() {
    init {
        layout = GridLayout(1, 2)
        add(JLabel(text))
        val folderToInclude = TextFieldWithHistoryWithBrowseButton()
        add(folderToInclude)

        val editor = folderToInclude.childComponent.textEditor

        installFileCompletionAndBrowseDialog(
                null,
                folderToInclude,
                editor,
                "Folder to include",
                FileChooserDescriptorFactory.createSingleFolderDescriptor(),
                TextComponentAccessor.TEXT_FIELD_WITH_HISTORY_WHOLE_TEXT) {
            folderSelected(it)
            it.path

        }
    }
}