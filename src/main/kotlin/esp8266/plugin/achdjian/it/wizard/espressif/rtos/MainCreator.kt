package esp8266.plugin.achdjian.it.wizard.espressif.rtos

import com.intellij.openapi.vfs.VirtualFile
import org.apache.commons.codec.Charsets

fun createEspressifRTOSMain(path: VirtualFile, requestor:Any): VirtualFile {
    val fileMainUser = path.findOrCreateChildData(requestor, "main.c")

    val builder = StringBuilder()

    builder
            .appendln("#include <string.h>")
            .appendln("#include \"uart.h\"")
            .appendln("#include \"nvs_flash.h\"")
            .appendln("")
            .appendln("void app_main(void) {")
            .appendln("    ESP_ERROR_CHECK( nvs_flash_init() );")
            .appendln("    UART_SetBaudrate(0, 115200);")
            .appendln("    printf(\"Hello word\");")
            .appendln("}")
    fileMainUser.setBinaryContent(builder.toString().toByteArray(Charsets.UTF_8))

    return fileMainUser
}
