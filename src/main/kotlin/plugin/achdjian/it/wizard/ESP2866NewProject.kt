package plugin.achdjian.it.wizard

import com.intellij.BundleBase
import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.ide.wizard.CommitStepException
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.layout.Row
import com.intellij.ui.layout.panel
import plugin.achdjian.it.ESP2866SDKSettings
import plugin.achdjian.it.ESP8266SettingsState
import java.awt.event.ItemEvent
import javax.swing.JCheckBox
import javax.swing.JComponent

fun Row.checkBox(text: String, actionListener: (event: ItemEvent) -> Unit) {
    val checkBox = JCheckBox(BundleBase.replaceMnemonicAmpersand(text))
    checkBox.addItemListener(actionListener)
    checkBox()
}


class ESP2866NewProject(val context: WizardContext) : ModuleWizardStep() {
    companion object {
        private val LOG = Logger.getInstance(ESP2866NewProject::class.java)
    }

    val extras = arrayListOf("ad7709x", "ads111x", "bearssl", "bh1750", "bme680", "bmp180", "bmp280", "ccs811", "cpp_support", "crc_generic",
            "dhcpserver", "dht", "ds1302", "ds1307", "ds18b20", "ds3231", "dsm", "fatfs", "fonts", "fram",
            "hd44780", "hmc5883l", "http-parser", "http_client_ota", "httpd", "i2c", "i2s_dma", "ina3221", "jsmn", "l3gd20h",
            "libesphttpd", "lis3dh", "lis3mdl", "lsm303d", "max7219", "mbedtls", "mcp4725", "mdnsresponder", "ms561101ba03", "multipwm @ 44ecea5",
            "onewire", "paho_mqtt_c", "pca9685", "pcf8574", "pcf8591", "pwm", "rboot-ota", "sdio", "sht3x", "sntp",
            "softuart", "spiffs", "ssd1306", "stdin_uart_interrupt", "timekeeping", "tsl2561", "tsl4531", "ultrasonic", "wificfg", "ws2812",
            "ws2812_i2s")
    val extrasEnabled = HashMap<String, Boolean>()


    override fun getComponent(): JComponent {
        val extraPanel = panel(title = "Extra") {
            extras.forEach { name -> row() { checkBox(name, { extrasEnabled[name] = it.stateChange == ItemEvent.SELECTED }) } }
        }

        return JBScrollPane(extraPanel)
    }

    override fun updateDataModel() {
        val projectBuilder = context.projectBuilder
        if (projectBuilder is EPS2866ModuleBuilder) {
            projectBuilder.addModuleConfigurationUpdater(ConfigurationUpdater(extrasEnabled))
        }
        context.projectBuilder

    }


    @Throws(CommitStepException::class)
    override fun onWizardFinished() {
        println("project file directory: ${context.projectFileDirectory}")
        println("context : ${context}")
        println("base path: ${context.project}")
    }
}

class ConfigurationUpdater(val extras: Map<String, Boolean>) : ModuleBuilder.ModuleConfigurationUpdater() {

    override fun update(module: Module, rootModel: ModifiableRootModel) {
        val state = module.project.getComponent(ESP8266SettingsState::class.java, ESP2866SDKSettings.DEFAULT) as ESP8266SettingsState
        state.extras = HashMap(extras)
        println("Update data model")
        rootModel.inheritSdk()
        println("Sdk name: ${rootModel.sdkName}")
    }
}

