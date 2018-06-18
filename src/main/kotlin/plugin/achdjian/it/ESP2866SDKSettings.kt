package plugin.achdjian.it

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import plugin.achdjian.it.ui.panel

class ESP2866SDKSettings(val project: Project) : ProjectComponent, Configurable {
    var sdkHome: VirtualFile? = null
    var ccPath: VirtualFile?=null
    var cxxPath: VirtualFile?=null
    var objCopyPath: VirtualFile?=null
    var arPath: VirtualFile?=null

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
        ccPath?.canonicalPath?.let{state.CCPath = it}
        cxxPath?.canonicalPath?.let{state.CXXPath = it}
        objCopyPath?.canonicalPath?.let{state.ObjCopyPATH = it}
        arPath?.canonicalPath?.let{state.ARPath = it}
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
        row("GCC path: ") {
            textFieldWithHistoryWithBrowseButton(
                    project = project,
                    browseDialogTitle = "ESP2866 free sdk gcc path",
                    value = state.CCPath,
                    fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor(),
                    fileChosen = {
                        ccPath = it
                        it.path
                    })
        }
        row("G++ path: ") {
            textFieldWithHistoryWithBrowseButton(
                    project = project,
                    browseDialogTitle = "ESP2866 free sdk g++ path",
                    value = state.CCPath,
                    fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor(),
                    fileChosen = {
                        cxxPath = it
                        it.path
                    })
        }
        row("Objcpy path: ") {
            textFieldWithHistoryWithBrowseButton(
                    project = project,
                    browseDialogTitle = "ESP2866 free sdk objcopy path",
                    value = state.CCPath,
                    fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor(),
                    fileChosen = {
                        objCopyPath = it
                        it.path
                    })
        }
        row("AR path: ") {
            textFieldWithHistoryWithBrowseButton(
                    project = project,
                    browseDialogTitle = "ESP2866 free sdk AR path",
                    value = state.CCPath,
                    fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor(),
                    fileChosen = {
                        arPath = it
                        it.path
                    })
        }
    }

    override fun reset() {

    }
}