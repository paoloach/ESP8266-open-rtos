package plugin.achdjian.it.wizard

import com.intellij.BundleBase
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.layout.Row
import com.intellij.ui.layout.panel
import java.awt.BorderLayout
import java.awt.event.ItemEvent
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener


fun Row.checkBox(text: String, actionListener: (event: ItemEvent) -> Unit) {
    val checkBox = JCheckBox(BundleBase.replaceMnemonicAmpersand(text))
    checkBox.addItemListener(actionListener)
    checkBox()
}

fun Row.comboBox(options: Array<String>, selected: String? = null, actionListener: (event: ItemEvent) -> Unit) {
    val checkBox = ComboBox(options)
    checkBox.addItemListener(actionListener)
    if (selected != null)
        checkBox.selectedItem = selected
    checkBox()
}

class ESP2866WizardPanel(clionPanel: JPanel, val wizardData: WizardData) : JPanel(BorderLayout()), DocumentListener {
    override fun changedUpdate(documentEvent: DocumentEvent?) {
        wizardData.espPort = documentEvent?.document.toString()
    }

    override fun insertUpdate(documentEvent: DocumentEvent?) {}

    override fun removeUpdate(documentEvent: DocumentEvent?) {}

    companion object {
        val extraModules = arrayListOf("ad7709x", "ads111x", "bearssl", "bh1750", "bme680", "bmp180", "bmp280", "ccs811", "cpp_support", "crc_generic",
                "dhcpserver", "dht", "ds1302", "ds1307", "ds18b20", "ds3231", "dsm", "fatfs", "fonts", "fram",
                "hd44780", "hmc5883l", "http-parser", "http_client_ota", "httpd", "i2c", "i2s_dma", "ina3221", "jsmn", "l3gd20h",
                "libesphttpd", "lis3dh", "lis3mdl", "lsm303d", "max7219", "mbedtls", "mcp4725", "mdnsresponder", "ms561101ba03", "multipwm @ 44ecea5",
                "onewire", "paho_mqtt_c", "pca9685", "pcf8574", "pcf8591", "pwm", "rboot-ota", "sdio", "sht3x", "sntp",
                "softuart", "spiffs", "ssd1306", "stdin_uart_interrupt", "timekeeping", "tsl2561", "tsl4531", "ultrasonic", "wificfg", "ws2812",
                "ws2812_i2s")
        val availableSize = arrayOf("256KB", "512KB", "1MB", "2MB", "4MB")
        val availableMode = arrayOf("qio", "qout", "dio")
        val availableSpeed = arrayOf("80", "40", "26", "20")
    }

    init {
        val textArea = JTextArea()
        textArea.text = "/dev/ttyUSB0"
        textArea.document.addDocumentListener(this)

        add(clionPanel, BorderLayout.PAGE_START)
        val p =panel() {
            row("flash Size") { comboBox(availableSize, "512KB", { wizardData.flashSize = it.item.toString() }) }
            row("Flash Mode") { comboBox(availableMode, "qio", { wizardData.flashMode = it.item.toString() }) }
            row("Flash Speed") { comboBox(availableSpeed, "40", { wizardData.flashSpeed = it.item.toString() }) }
            row("ESP port") { textArea }
            row( ){checkBox("Float Support", {wizardData.floatSupport = it.stateChange == ItemEvent.SELECTED})}
        }

        add(p)
        add(panel(title = "Extras") {
            extraModules.forEach { name -> row() { checkBox(name, { wizardData.extras[name] = it.stateChange == ItemEvent.SELECTED }) } }
        }, BorderLayout.PAGE_END)
    }
}