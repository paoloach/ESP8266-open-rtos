package plugin.achdjian.it.wizard

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.jetbrains.cidr.cpp.cmake.CMakeSettings
import com.jetbrains.cidr.cpp.cmake.projectWizard.generators.CMakeAbstractCProjectGenerator
import com.jetbrains.cidr.cpp.cmake.projectWizard.generators.settings.CMakeProjectSettings
import com.jetbrains.cidr.cpp.cmake.workspace.CMakeWorkspace
import javax.swing.JComponent

class ESP2866ProjectGenerator : CMakeAbstractCProjectGenerator() {

    val wizardData = WizardData()

    override fun createSourceFiles(projectName: String, path: VirtualFile): Array<VirtualFile> {
        return arrayOf(
                createMainUser(path,this ),
                createFreeRTOSConfig(path, this),
                createCMakeCrossTool(path, this))
    }

    override fun getCMakeFileContent(projectName: String): String {
        return createCMake(wizardData, projectName)
    }

    override fun getName(): String = "C ESP2866 free rtos"

    override fun getSettingsPanel(): JComponent? = ESP2866WizardPanel(createSettingsPanel(), wizardData)

    override fun generateProject(project: Project, path: VirtualFile, cmakeSetting: CMakeProjectSettings, module: Module) {
        super.generateProject(project, path, cmakeSetting, module)
        val cMakeWorkspace = CMakeWorkspace.getInstance(project)
        val settings = cMakeWorkspace.settings
        val newProfiles = ArrayList<CMakeSettings.Profile>()
        settings.profiles.forEach {
            newProfiles.add(it.withGenerationOptions("-DCMAKE_TOOLCHAIN_FILE=CrossCompiler.cmake"))
        }
        settings.profiles = newProfiles
    }
}