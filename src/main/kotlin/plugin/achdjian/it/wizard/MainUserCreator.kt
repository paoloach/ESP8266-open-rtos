package plugin.achdjian.it.wizard

import com.intellij.openapi.vfs.VirtualFile
import org.apache.commons.codec.Charsets

fun createMainUser(path: VirtualFile,  requestor:Any): VirtualFile {
    val fileMainUser = path.findOrCreateChildData(requestor, "mainUser.c")

    val builder = StringBuilder()
    builder
            .appendln("#include <espressif/esp_common.h>")
            .appendln("#include <espressif/user_interface.h>")
            .appendln("#include <esp/uart.h>")
            .appendln("#include <FreeRTOS.h>")
            .appendln("#include <task.h>")
            .appendln("")
            .appendln("void user_init(void) {")
            .appendln("    uart_set_baud(0, 115200);")
            .appendln("    printf(\"SDK version:%s\\n\", sdk_system_get_sdk_version());")
            .appendln("}")

    fileMainUser.setBinaryContent(builder.toString().toByteArray(Charsets.UTF_8))

    return fileMainUser
}
