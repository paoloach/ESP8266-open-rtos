package esp8266.plugin.achdjian.it.wizard.espressif.rtos

import com.intellij.openapi.vfs.VirtualFile
import esp8266.plugin.achdjian.it.wizard.getResourceAsString
import org.apache.commons.codec.Charsets

fun createEspressifRTOSMain(path: VirtualFile, requestor:Any): VirtualFile {
    var main_c = getResourceAsString("templates/espressif/main.c")
    val fileMainUser = path.findOrCreateChildData(requestor, "main.c")
    fileMainUser.setBinaryContent(main_c.toByteArray(Charsets.UTF_8))

    return fileMainUser
}
