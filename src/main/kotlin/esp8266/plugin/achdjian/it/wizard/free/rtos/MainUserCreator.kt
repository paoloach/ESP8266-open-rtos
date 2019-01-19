package esp8266.plugin.achdjian.it.wizard.free.rtos

import com.intellij.openapi.vfs.VirtualFile
import esp8266.plugin.achdjian.it.wizard.getResourceAsString
import org.apache.commons.codec.Charsets

fun createFreeRTOSMainUser(path: VirtualFile, requestor:Any): VirtualFile {
    val fileMainUser = path.findOrCreateChildData(requestor, "mainUser.c")
    val content = getResourceAsString("templates/free/mainuser.c")
    fileMainUser.setBinaryContent(content.toByteArray(Charsets.UTF_8))
    return fileMainUser
}
