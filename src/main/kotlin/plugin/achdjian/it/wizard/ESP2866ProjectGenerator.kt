package plugin.achdjian.it.wizard

import com.intellij.openapi.vfs.VirtualFile
import com.jetbrains.cidr.cpp.cmake.projectWizard.CLionProjectWizardUtils
import com.jetbrains.cidr.cpp.cmake.projectWizard.generators.CMakeAbstractCProjectGenerator
import javax.swing.JComponent

class ESP2866ProjectGenerator:  CMakeAbstractCProjectGenerator(){

    val wizardData = WizardData()

    override fun createSourceFiles(projectName: String, path: VirtualFile): Array<VirtualFile> {
       return arrayOf(this.createProjectFileWithContent(path, "main.c", "#include <stdio.h>\n\nint main( )\n{\n    printf(\"Hello, World!\\n\");\n    return 0;\n}\n"))
    }

    override fun getCMakeFileContent(projectName: String): String {
        return CLionProjectWizardUtils.getCMakeFileHeader(projectName, getCMakeProjectSettings()) + "\nadd_executable( main.c)";
    }

    override fun getName(): String  = "C ESP2866 free rtos"

    override fun getSettingsPanel(): JComponent?  = ESP2866WizardPanel(createSettingsPanel(), wizardData)

}