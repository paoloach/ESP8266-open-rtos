package esp8266.plugin.achdjian.it.wizard.espressif.rtos

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.jetbrains.cidr.cpp.cmake.CMakeSettings
import com.jetbrains.cidr.cpp.cmake.projectWizard.generators.CMakeAbstractCProjectGenerator
import com.jetbrains.cidr.cpp.cmake.projectWizard.generators.settings.CMakeProjectSettings
import com.jetbrains.cidr.cpp.cmake.workspace.CMakeWorkspace
import esp8266.plugin.achdjian.it.wizard.createCMakeCrossTool
import javax.swing.JComponent

class ESP8266EspressifRTOSProjectGenerator : CMakeAbstractCProjectGenerator() {

    val wizardData = MenuWizardData()
    val settingPanel = ESP8266EspressifWizardPanel(createSettingsPanel(), wizardData.entriesMenu)

    override fun createSourceFiles(projectName: String, path: VirtualFile): Array<VirtualFile> {
        val creator = creatorFactory(wizardData)

        createEspressifRTORSubCMake(projectName, wizardData, path, creator)
        createCMakeCrossTool(path, this)

        createSdkConfigFile(wizardData, path, creator)
        return arrayOf(createEspressifRTOSMain(path, this, creator))
    }

    override fun getCMakeFileContent(projectName: String): String {
        return createEspressifRTOSCMake(projectName, wizardData, creatorFactory(wizardData))
    }

    override fun getName(): String = "C ESP8266 espressif rtos"

    override fun getSettingsPanel(): JComponent? = settingPanel

    override fun generateProject(project: Project, path: VirtualFile, cmakeSetting: CMakeProjectSettings, module: Module) {
        super.generateProject(project, path, cmakeSetting, module)
        val cMakeWorkspace = CMakeWorkspace.getInstance(project)
        val settings = cMakeWorkspace.settings
        var releaseProfile = CMakeSettings.Profile("Release", "Release", "", "-DCMAKE_TOOLCHAIN_FILE=CrossCompiler.cmake", true, HashMap(), null, null)

        settings.profiles = listOf(releaseProfile)
    }

    private fun creatorFactory(wizardMenu: MenuWizardData) =Creator3_1()
}

