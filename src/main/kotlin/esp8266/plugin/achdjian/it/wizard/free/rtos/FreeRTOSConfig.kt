package esp8266.plugin.achdjian.it.wizard.free.rtos

import com.intellij.openapi.vfs.VirtualFile
import esp8266.plugin.achdjian.it.wizard.getResourceAsString
import org.apache.commons.codec.Charsets

fun createFreeRTOSConfig(path: VirtualFile, requestor: Any): VirtualFile {
    val file = path.findOrCreateChildData(requestor, "FreeRTOSConfig.h")
    val content = getResourceAsString("templates/free/FreeRTOSConfig.h")
    file.setBinaryContent(content.toByteArray(Charsets.UTF_8))
    return file
}
