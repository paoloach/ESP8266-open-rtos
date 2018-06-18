package plugin.achdjian.it

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import plugin.achdjian.it.ui.panel

class ESP2866SDKSettings(val project: Project) : ProjectComponent, Configurable {
    var sdkHome: VirtualFile? = null

    companion object {
        val GCC = "xtensa-lx106-elf-gcc"
        val CXX = "xtensa-lx106-elf-g++"
        val OBJCOPY = "xtensa-lx106-elf-objcopy"
        val AR = "xtensa-lx106-elf-ar"
        val DEFAULT = ESP8266SettingsState("~", GCC,CXX ,OBJCOPY, AR)
    }

    override fun isModified(): Boolean {
        val state = project.getComponent(ESP8266SettingsState::class.java, DEFAULT) as ESP8266SettingsState

        val modified = state.sdkHome != sdkHome?.canonicalPath
        System.out.println("Is modified: $modified")
        return modified
    }

    override fun getDisplayName() = "ESP2866"


    override fun apply() {
        val state = project.getComponent(ESP8266SettingsState::class.java, DEFAULT) as ESP8266SettingsState
        System.out.println(sdkHome?.canonicalPath)
        sdkHome?.canonicalPath?.let { state.sdkHome = it }
    }

    override fun disposeComponent() {

    }

    override fun createComponent() = panel() {
        val state = project.getComponent(ESP8266SettingsState::class.java, DEFAULT) as ESP8266SettingsState
        row("Root path: "){
            textFieldWithHistoryWithBrowseButton(
                    project = project,
                    browseDialogTitle = "ESP2866 free rtos path",
                    value = state.sdkHome,
                    fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor(),
                    fileChosen = {
                        sdkHome = it
                        it.path
                    })
        }
    }

    override fun reset() {

    }
}