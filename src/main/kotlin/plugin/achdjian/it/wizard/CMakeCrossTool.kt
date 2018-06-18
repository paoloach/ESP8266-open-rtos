package plugin.achdjian.it.wizard

import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.vfs.VirtualFile
import org.apache.commons.codec.Charsets
import plugin.achdjian.it.ESP2866SDKSettings
import plugin.achdjian.it.ESP8266SettingsState

fun createCMakeCrossTool(path: VirtualFile,  requestor:Any ):VirtualFile{
    var crossTool = getResourceAsString("templates/CrossCompiler.cmake")

    val projectManager = ProjectManager.getInstance()
    val setting = projectManager.defaultProject.getComponent(ESP8266SettingsState::class.java, ESP2866SDKSettings.DEFAULT) as ESP8266SettingsState
    crossTool =crossTool
            .replace("__{C_COMPILER_PATH}__", setting.CCPath)
            .replace("__{CXX_COMPILER_PATH}__", setting.CXXPath)
            .replace("__{OBJCOPY_PATH}__", setting.ObjCopyPATH)
            .replace("__{AR_PATH}__", setting.ARPath)

    val fileCrossCompiler = path.findOrCreateChildData(requestor, "CrossCompiler.cmake")
    fileCrossCompiler.setBinaryContent(crossTool.toByteArray(Charsets.UTF_8))

    return fileCrossCompiler

}