package plugin.achdjian.it.wizard

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.io.FileUtil
import java.io.File

fun create(wizardData: WizardData,  projectName: String ):String {
    val builder =StringBuilder()
    builder.append("")
    //FileUtil.writeToFile(cmakefile, "cmake_minimum_required(VERSION 3.10)\n")
    //FileUtil.writeToFile(cmakefile, "project($projectName)\n")
    return  builder.toString()
}