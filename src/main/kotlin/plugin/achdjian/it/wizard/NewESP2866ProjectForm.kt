package plugin.achdjian.it.wizard

import com.intellij.ide.profiler.dummy.main
import com.intellij.openapi.diagnostic.Logger
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.layout.panel
import com.jetbrains.cidr.cpp.cmake.projectWizard.CMakeProjectStepAdapter
import java.awt.event.ItemEvent
import javax.swing.JComponent
import javax.swing.JPanel

class NewESP2866ProjectForm(val defaultProjectName:String, val defaultProjectPath:String) : CMakeProjectStepAdapter() {

    companion object {
        private val LOG = Logger.getInstance(NewESP2866ProjectForm::class.java)
        val extras = arrayListOf("ad7709x", "ads111x", "bearssl", "bh1750", "bme680", "bmp180", "bmp280", "ccs811", "cpp_support", "crc_generic",
                "dhcpserver", "dht", "ds1302", "ds1307", "ds18b20", "ds3231", "dsm", "fatfs", "fonts", "fram",
                "hd44780", "hmc5883l", "http-parser", "http_client_ota", "httpd", "i2c", "i2s_dma", "ina3221", "jsmn", "l3gd20h",
                "libesphttpd", "lis3dh", "lis3mdl", "lsm303d", "max7219", "mbedtls", "mcp4725", "mdnsresponder", "ms561101ba03", "multipwm @ 44ecea5",
                "onewire", "paho_mqtt_c", "pca9685", "pcf8574", "pcf8591", "pwm", "rboot-ota", "sdio", "sht3x", "sntp",
                "softuart", "spiffs", "ssd1306", "stdin_uart_interrupt", "timekeeping", "tsl2561", "tsl4531", "ultrasonic", "wificfg", "ws2812",
                "ws2812_i2s")
    }


    val extrasEnabled = HashMap<String, Boolean>()
    val mainPanel: JPanel

    init {
        val extraPanel = panel(title = "Extra") {
            extras.forEach { name -> row() { checkBox(name, { extrasEnabled[name] = it.stateChange == ItemEvent.SELECTED }) } }
        }

        mainPanel = panel(title="ESP2866 free rtos"){
            row(){JBScrollPane(extraPanel)}
        }
    }

    override fun init() = mainPanel.setVisible(true)

    override fun getPreferredFocusedComponent(): JComponent = mainPanel

    override fun getComponent(): JComponent = mainPanel

    override fun dispose() = mainPanel.setVisible(false)
}