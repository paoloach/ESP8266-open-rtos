package plugin.achdjian.it

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.layout.panel


class ESP2866SDKSettings(val project: Project) : ProjectComponent, Configurable {
    var sdkHome: VirtualFile? = null

    companion object {
        val DEFAULT = ESP8266SettingsState()
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
        state.sdkHome = sdkHome?.canonicalPath
    }

    override fun disposeComponent() {

    }

    override fun createComponent() = panel() {
        val state = project.getComponent(ESP8266SettingsState::class.java, DEFAULT) as ESP8266SettingsState
        row("Root path: ") {
            textFieldWithBrowseButton(
                    browseDialogTitle = "ESP2866 free rtos path",
                    value = state.sdkHome,
                    fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor(),
                    fileChoosen = {
                        sdkHome = it
                        it.path
                    })
        }
    }

    override fun reset() {

    }
}