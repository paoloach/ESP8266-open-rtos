package plugin.achdjian.it.wizard

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.vfs.VirtualFile
import org.apache.commons.codec.Charsets
import plugin.achdjian.it.ESP2866SDKSettings
import plugin.achdjian.it.ESP8266SettingsState

fun createCMakeCrossTool(path: VirtualFile,  requestor:Any ):VirtualFile{
    var crossTool = getResourceAsString("templates/CrossCompiler.cmake")

    val setting = ApplicationManager.getApplication().getComponent(ESP8266SettingsState::class.java,ESP2866SDKSettings.DEFAULT) as ESP8266SettingsState
    crossTool =crossTool
            .replace("__{C_COMPILER_PATH}__", setting.ccPath)
            .replace("__{CXX_COMPILER_PATH}__", setting.cxxPath)

    val fileCrossCompiler = path.findOrCreateChildData(requestor, "CrossCompiler.cmake")
    fileCrossCompiler.setBinaryContent(crossTool.toByteArray(Charsets.UTF_8))

    return fileCrossCompiler
}