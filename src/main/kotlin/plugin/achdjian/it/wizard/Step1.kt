package plugin.achdjian.it.wizard

import com.intellij.BundleBase
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.openapi.diagnostic.Logger
import com.intellij.ui.layout.Row
import com.intellij.ui.layout.panel
import java.awt.event.ItemEvent
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JScrollPane

fun Row.checkBox(text: String, actionListener: (event: ItemEvent) -> Unit) {
    val checkBox = JCheckBox(BundleBase.replaceMnemonicAmpersand(text))
    checkBox.addItemListener(actionListener)
    checkBox()
}

fun Row.ScrollPanel(text: String, actionListener: (event: ItemEvent) -> Unit) {
    val scrollPane = JScrollPane(BundleBase.replaceMnemonicAmpersand(text))
    scrollPane()
}

class Step1 : ModuleWizardStep() {
    companion object {
        private val LOG = Logger.getInstance(Step1::class.java)
    }

    val extras = arrayListOf("ad7709x","ads111x","bearssl","bh1750","bme680","bmp180","bmp280","ccs811","cpp_support","crc_generic",
            "dhcpserver","dht","ds1302","ds1307","ds18b20","ds3231","dsm","fatfs","fonts","fram",
            "hd44780","hmc5883l","http-parser","http_client_ota","httpd","i2c","i2s_dma","ina3221","jsmn","l3gd20h",
            "libesphttpd","lis3dh","lis3mdl","lsm303d","max7219","mbedtls","mcp4725","mdnsresponder","ms561101ba03","multipwm @ 44ecea5",
            "onewire","paho_mqtt_c","pca9685","pcf8574","pcf8591","pwm","rboot-ota","sdio","sht3x","sntp",
            "softuart","spiffs","ssd1306","stdin_uart_interrupt","timekeeping","tsl2561","tsl4531","ultrasonic","wificfg","ws2812",
            "ws2812_i2s")
    val extrasEnabled = HashMap<String,Boolean>()


    override fun getComponent(): JComponent = panel(title="extenal") {
        extras.forEach{name -> row(){checkBox(name,{extrasEnabled[name] = it.stateChange == ItemEvent.SELECTED})}}
    }

    override fun updateDataModel() {
        System.out.println("Update data model")
    }


}