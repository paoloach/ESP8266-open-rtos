package plugin.achdjian.it.wizard

import com.intellij.openapi.vfs.VirtualFile
import com.jetbrains.cidr.cpp.cmake.projectWizard.generators.CMakeAbstractCProjectGenerator
import javax.swing.JComponent

class ESP2866ProjectGenerator : CMakeAbstractCProjectGenerator() {

    val wizardData = WizardData()

    override fun createSourceFiles(projectName: String, path: VirtualFile): Array<VirtualFile> {
        return arrayOf(createMainUser(path,this ), createCMakeCrossTool(path, this))
    }

    override fun getCMakeFileContent(projectName: String): String {
        return createCMake(wizardData, projectName)
    }

    override fun getName(): String = "C ESP2866 free rtos"

    override fun getSettingsPanel(): JComponent? = ESP2866WizardPanel(createSettingsPanel(), wizardData)
}