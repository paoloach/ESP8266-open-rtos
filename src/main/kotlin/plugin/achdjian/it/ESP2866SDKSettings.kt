package plugin.achdjian.it

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.TextComponentAccessor
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.TextFieldWithHistoryWithBrowseButton
import com.intellij.ui.components.installFileCompletionAndBrowseDialog
import com.intellij.ui.layout.panel

class ESP2866SDKSettings(private val project: Project) : ProjectComponent, Configurable {
    private var ccPath: VirtualFile?=null
    private var cxxPath: VirtualFile?=null
    private var rtosPath: VirtualFile?=null
    private var esptool2Path: VirtualFile? = null

    companion object {
        const val GCC = "xtensa-lx106-elf-gcc"
        const val CXX = "xtensa-lx106-elf-g++"
        val DEFAULT = ESP8266SettingsState( GCC,CXX )
    }

    override fun isModified(): Boolean {
        val state = ApplicationManager.getApplication().getComponent(ESP8266SettingsState::class.java,ESP2866SDKSettings.DEFAULT) as ESP8266SettingsState

        val modified = (state.ccPath != ccPath?.canonicalPath)
                || (state.cxxPath != cxxPath?.canonicalPath)
                || (state.rtosPath != rtosPath?.canonicalPath)
                || (state.esptool2 != esptool2Path?.canonicalPath)
        System.out.println("Is modified: $modified")
        return modified
    }

    override fun getDisplayName() = "ESP2866"


    override fun apply() {
        val state = ApplicationManager.getApplication().getComponent(ESP8266SettingsState::class.java,ESP2866SDKSettings.DEFAULT) as ESP8266SettingsState
        ccPath?.canonicalPath?.let{state.ccPath = it}
        cxxPath?.canonicalPath?.let{state.cxxPath = it}
        rtosPath?.canonicalPath?.let{state.rtosPath = it}
        esptool2Path?.canonicalPath?.let{state.esptool2 = it}
    }

    override fun disposeComponent() {

    }

    override fun createComponent() = panel {
        val state = project.getComponent(ESP8266SettingsState::class.java, DEFAULT) as ESP8266SettingsState
        row("ESP RTOS path: "){
            val component = TextFieldWithHistoryWithBrowseButton()
            val editor = component.childComponent.textEditor
            editor.text = state.rtosPath
            installFileCompletionAndBrowseDialog(
                    project,
                    component,
                    editor,
                    "ESP2866 free rtos path",
                    FileChooserDescriptorFactory.createSingleFolderDescriptor(),
                    TextComponentAccessor.TEXT_FIELD_WITH_HISTORY_WHOLE_TEXT){
                rtosPath = it
                it.path
            }
            component()
        }
        row("esptool2 path: "){
            val component = TextFieldWithHistoryWithBrowseButton()
            val editor = component.childComponent.textEditor
            editor.text = state.rtosPath
            installFileCompletionAndBrowseDialog(
                    project,
                    component,
                    editor,
                    "ESP2866 esptool2 path",
                    FileChooserDescriptorFactory.createSingleFileDescriptor(),
                    TextComponentAccessor.TEXT_FIELD_WITH_HISTORY_WHOLE_TEXT){
                esptool2Path = it
                it.path
            }
            component()
        }
        row("GCC path: ") {
            val component = TextFieldWithHistoryWithBrowseButton()
            val editor = component.childComponent.textEditor
            editor.text = state.ccPath
            installFileCompletionAndBrowseDialog(
                    project,
                    component,
                    editor,
                    "ESP2866 free sdk gcc path",
                    FileChooserDescriptorFactory.createSingleFileDescriptor(),
                    TextComponentAccessor.TEXT_FIELD_WITH_HISTORY_WHOLE_TEXT){
                ccPath = it
                it.path
            }
            component()
        }
        row("G++ path: ") {
            val component = TextFieldWithHistoryWithBrowseButton()
            val editor = component.childComponent.textEditor
            editor.text = state.cxxPath
            installFileCompletionAndBrowseDialog(
                    project,
                    component,
                    editor,
                    "ESP2866 free sdk g++ path",
                    FileChooserDescriptorFactory.createSingleFileDescriptor(),
                    TextComponentAccessor.TEXT_FIELD_WITH_HISTORY_WHOLE_TEXT){
                        cxxPath = it
                        it.path
            }
            component()
        }

    }

    override fun reset() {

    }
}