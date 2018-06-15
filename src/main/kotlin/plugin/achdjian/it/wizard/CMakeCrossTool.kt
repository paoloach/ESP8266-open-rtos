package plugin.achdjian.it.wizard

import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.vfs.VirtualFile

fun createCMakeCrossTool(path: VirtualFile,  requestor:Any ):VirtualFile{
    var crossTool = getResourceAsString("templates/CrossCompiler.cmake")

    val projectManager = ProjectManager.getInstance()
    if

    val fileCrossCompiler = path.findOrCreateChildData(requestor, "CrossCompiler.cmake")

    return fileCrossCompiler

}