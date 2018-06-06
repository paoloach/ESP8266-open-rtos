package plugin.achdjian.it

import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.util.InvalidDataException
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.components.JBTextField
import com.intellij.ui.components.fields.valueEditors.TextFieldValueEditor

import java.util.function.Supplier

abstract class FileChooseInput protected constructor(valueName: String, defValue: VirtualFile) : TextFieldWithBrowseButton(JBTextField()) {
    protected val editor: TextFieldValueEditor<VirtualFile>
    private val fileDescriptor: FileChooserDescriptor

    val valueName: String?
        get() = editor.valueName

    fun getDefaultLocation(): VirtualFile? = VfsUtil.getUserHomeDir()

    init {

        editor = FileTextFieldValueEditor(valueName, defValue)
        fileDescriptor = createFileChooserDescriptor().withFileFilter({ validateFile(it) })
        installPathCompletion(fileDescriptor)
        addActionListener { e ->
            var virtualFile: VirtualFile? = null
            val text = textField.text
            if (text != null && !text.isEmpty())
                try {
                    virtualFile = parseTextToFile(text)
                } catch (ignored: InvalidDataException) {
                    virtualFile = LocalFileSystem.getInstance().findFileByPath(text)
                }

            if (virtualFile == null) {
                virtualFile = getDefaultLocation()
            }
            val chosenFile = FileChooser.chooseFile(fileDescriptor, null, virtualFile)
            if (chosenFile != null) {
                textField.text = fileToTextValue(chosenFile)
            }
        }
    }

    protected open fun fileToTextValue(file: VirtualFile): String? {
        return file.canonicalPath
    }

    protected abstract fun validateFile(virtualFile: VirtualFile): Boolean

    protected abstract fun createFileChooserDescriptor(): FileChooserDescriptor

    @Throws(ConfigurationException::class)
    fun validateContent() {
        editor.validateContent()
    }

    protected open fun parseTextToFile(text: String?): VirtualFile {
        val file = if (text == null)
            editor.defaultValue
        else
            LocalFileSystem.getInstance().findFileByPath(text)
        if (file == null || !validateFile(file)) {
            throw InvalidDataException("is invalid")
        }
        return file
    }
}

class FileTextFieldValueEditor internal constructor(valueName: String, defValue: VirtualFile) : TextFieldValueEditor<VirtualFile>(this@FileChooseInput.textField, valueName, defValue) {

        override fun parseValue(text: String?): VirtualFile {
            return parseTextToFile(text)
        }

        override fun valueToString(value: VirtualFile): String {
            return value.path
        }

        override fun isValid(virtualFile: VirtualFile): Boolean {
            return this@FileChooseInput.validateFile(virtualFile)
        }
    }

    companion object {

        val BOARD_FOLDER = "board"
    }
}