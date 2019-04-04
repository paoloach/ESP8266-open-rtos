package esp8266.plugin.achdjian.it.wizard.espressif.rtos

import javax.swing.JComponent
import javax.swing.JPanel


interface BoolValue{
    var value: Boolean
    fun addListener(configurationEntry: ConfigurationEntry):Boolean
}

abstract class ConfigurationEntry(val text: String, val configEntry: List<String>, var dependsOn: List<BoolValue> = listOf()) {
    var panel: JPanel? = null
    abstract fun createRow(): JComponent
    abstract fun addConfigution(configurations: MutableMap<String, String>)
    abstract fun set(key: String, value: String)

    open fun isConfig(configKey: String): Boolean {
        return configEntry.any { it == configKey }
    }

    fun dependsChangValue() {
        panel?.isVisible = dependsOn.all { it.value }
    }


    constructor(text: String, configEntry: String, dependsOn: List<BoolValue>) : this(text, listOf(configEntry), dependsOn = dependsOn)

    init {
        dependsOn.forEach {
            it.addListener(this)
        }

    }


}