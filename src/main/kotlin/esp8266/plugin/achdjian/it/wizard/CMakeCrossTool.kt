package esp8266.plugin.achdjian.it.wizard

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.vfs.VirtualFile
import org.apache.commons.codec.Charsets
import it.achdjian.plugin.esp8266.settings.ESP8266SDKSettings
import it.achdjian.plugin.esp8266.settings.ESP8266SettingsState
import it.achdjian.plugin.esp8266.settings.getESP8266Setting

fun createCMakeCrossTool(path: VirtualFile,  requestor:Any ):VirtualFile{
    var crossTool = getResourceAsString("templates/CrossCompiler.cmake")

    val setting =getESP8266Setting()
    crossTool =crossTool
            .replace("__{C_COMPILER_PATH}__", setting.ccPath)
            .replace("__{CXX_COMPILER_PATH}__", setting.cxxPath)

    val fileCrossCompiler = path.findOrCreateChildData(requestor, "CrossCompiler.cmake")
    fileCrossCompiler.setBinaryContent(crossTool.toByteArray(Charsets.UTF_8))

    return fileCrossCompiler
}