package esp8266.plugin.achdjian.it.wizard.espressif.rtos

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.jetbrains.cidr.cpp.cmake.CMakeSettings
import com.jetbrains.cidr.cpp.cmake.projectWizard.generators.CMakeAbstractCProjectGenerator
import com.jetbrains.cidr.cpp.cmake.projectWizard.generators.settings.CMakeProjectSettings
import com.jetbrains.cidr.cpp.cmake.workspace.CMakeWorkspace
import esp8266.plugin.achdjian.it.wizard.*
import javax.swing.JComponent

class ESP8266EspressifRTOSProjectGenerator : CMakeAbstractCProjectGenerator() {

    val wizardData = MenuWizardData()

    override fun createSourceFiles(projectName: String, path: VirtualFile): Array<VirtualFile> {
        val files =  arrayOf(
//                createMainUser(path, this),
//                createFreeRTOSConfig(path, this),
                createCMakeCrossTool(path, this))
        return files;
    }

    override fun getCMakeFileContent(projectName: String): String {
        //return createCMake(wizardData, projectName)
        return ""
    }

    override fun getName(): String = "C ESP8266 espressif rtos"

    override fun getSettingsPanel(): JComponent? = ESP8266EspressifWizardPanel(createSettingsPanel(), wizardData.entriesMenu)

    override fun generateProject(project: Project, path: VirtualFile, cmakeSetting: CMakeProjectSettings, module: Module) {
        super.generateProject(project, path, cmakeSetting, module)
        val cMakeWorkspace = CMakeWorkspace.getInstance(project)
        val settings = cMakeWorkspace.settings
        var releaseProfile = CMakeSettings.Profile("Release", "Release", "", "-DCMAKE_TOOLCHAIN_FILE=CrossCompiler.cmake",true, HashMap(), null, null)

        settings.profiles = listOf(releaseProfile)
    }
}