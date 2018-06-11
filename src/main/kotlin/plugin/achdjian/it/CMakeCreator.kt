package plugin.achdjian.it

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.io.FileUtil
import java.io.File

fun create(cmakefile: File, projectName: String ) {
    FileUtil.writeToFile(cmakefile, "cmake_minimum_required(VERSION 3.10)\n")
    FileUtil.writeToFile(cmakefile, "project($projectName)\n")

}