package plugin.achdjian.it.wizard

import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.layout.LCFlags
import com.intellij.ui.layout.panel
import com.jetbrains.cidr.cpp.cmake.projectWizard.CLionProjectWizardUtils
import com.jetbrains.cidr.cpp.cmake.projectWizard.generators.CMakeAbstractCProjectGenerator
import com.jetbrains.cidr.cpp.cmake.projectWizard.generators.settings.ui.CMakeSettingsPanel
import java.awt.BorderLayout
import java.awt.event.ItemEvent
import javax.swing.JComponent
import javax.swing.JPanel

class ESP2866ProjectGenerator:  CMakeAbstractCProjectGenerator(){
    val extras = arrayListOf("ad7709x", "ads111x", "bearssl", "bh1750", "bme680", "bmp180", "bmp280", "ccs811", "cpp_support", "crc_generic",
            "dhcpserver", "dht", "ds1302", "ds1307", "ds18b20", "ds3231", "dsm", "fatfs", "fonts", "fram",
            "hd44780", "hmc5883l", "http-parser", "http_client_ota", "httpd", "i2c", "i2s_dma", "ina3221", "jsmn", "l3gd20h",
            "libesphttpd", "lis3dh", "lis3mdl", "lsm303d", "max7219", "mbedtls", "mcp4725", "mdnsresponder", "ms561101ba03", "multipwm @ 44ecea5",
            "onewire", "paho_mqtt_c", "pca9685", "pcf8574", "pcf8591", "pwm", "rboot-ota", "sdio", "sht3x", "sntp",
            "softuart", "spiffs", "ssd1306", "stdin_uart_interrupt", "timekeeping", "tsl2561", "tsl4531", "ultrasonic", "wificfg", "ws2812",
            "ws2812_i2s")
    val extrasEnabled = HashMap<String, Boolean>()

    override fun createSourceFiles(p0: String, path: VirtualFile): Array<VirtualFile> {
       return arrayOf(this.createProjectFileWithContent(path, "main.c", "#include <stdio.h>\n\nint main( )\n{\n    printf(\"Hello, World!\\n\");\n    return 0;\n}\n"))
    }

    override fun getCMakeFileContent(projectName: String): String {
        return CLionProjectWizardUtils.getCMakeFileHeader(projectName, getCMakeProjectSettings()) + "\nadd_executable( main.c)";
    }


    override fun getName(): String  = "C ESP2866 free rtos"

    override fun getSettingsPanel(): JComponent? {
        val panel = JPanel(BorderLayout())
        val settingsPanel = createSettingsPanel()
        settingsPanel.initFields(myLanguageVersion, "static")

        val extraPanel = panel() {
            extras.forEach { name -> row() { checkBox(name, { extrasEnabled[name] = it.stateChange == ItemEvent.SELECTED }) } }
        }

        panel.add(settingsPanel, BorderLayout.PAGE_START)
        panel.add(extraPanel, BorderLayout.PAGE_END)

        return panel
    }



}