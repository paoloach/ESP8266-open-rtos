package esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.BoolValue
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import java.awt.GridLayout
import java.awt.event.ItemEvent
import java.awt.event.ItemListener
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import kotlin.properties.Delegates

open class BoolConfigEntry(text: String,
                      configEntry: String,
                      defaultValue: Boolean = false,
                      dependsOn: List<BoolValue>,
                      private val associated: List<BoolConfigEntry> = listOf())
    : ConfigurationEntry(text, configEntry, dependsOn), ItemListener, BoolValue {
    protected val listener = ArrayList<ConfigurationEntry>()
    override var value: Boolean by Delegates.observable(defaultValue) { _, _, _ ->
        listener.forEach { it.dependsChangValue() }
    }

    constructor(text: String, configEntry: String, defaultValue: Boolean = false, dependsOn: BoolValue, associated: List<BoolConfigEntry> = listOf()) : this(text, configEntry, defaultValue, listOf(dependsOn), associated)
    constructor(text: String, configEntry: String, value: Boolean, associated: List<BoolConfigEntry>) : this(text, configEntry, value, listOf(), associated)
    constructor(text: String, configEntry: String, defaultValue: Boolean = false) : this(text, configEntry, defaultValue, listOf(), listOf())


    override fun set(key: String, value: String) {
        if (isConfig(key))
            this.value = value == "1" || value.compareTo("true", true) == 0
    }


    override fun itemStateChanged(event: ItemEvent) {
        value = event.stateChange == ItemEvent.SELECTED
        associated.forEach { it.value = value }
    }

    override fun addConfigution(configurations: MutableMap<String, String>) {
        if (value) {
            configEntry.forEach { configurations[it] = "1" }
            associated.forEach { it.configEntry.forEach { ce -> configurations[ce] = "1" } }
        }
    }

    override fun createRow(): JComponent {
        val panel = JPanel()
        panel.layout = GridLayout(1, 2)
        panel.add(JLabel(text))
        val checkBox = JCheckBox()
        checkBox.addItemListener(this)
        checkBox.isSelected = value
        panel.add(checkBox)
        panel.isVisible = dependsOn.all { it.value == true }
        this.panel = panel
        return panel
    }


    override fun addListener(configurationEntry: ConfigurationEntry) = listener.add(configurationEntry)
}

class BooleanInteger(text: String,
                     configEntry: String,
                     defaultValue: Boolean = false,
                     val valueTrue: Int,
                     val valueFalse: Int):BoolConfigEntry(text, configEntry, defaultValue){
    override fun addConfigution(configurations: MutableMap<String, String>) {
        val valueInt = if (value) valueTrue else valueFalse
        configEntry.forEach { configurations[it] = "$valueInt" }
    }

}

class Not(val boolValue: BoolValue) : BoolValue {
    override var value: Boolean
        get() = !boolValue.value
        set(value) {
            boolValue.value = !value
        }

    override fun addListener(configurationEntry: ConfigurationEntry): Boolean = boolValue.addListener((configurationEntry))

}

class And(val aValue: BoolValue, val bValue: BoolValue) : BoolValue {
    override var value: Boolean
        get() = aValue.value && bValue.value
        set(value) {
            aValue.value = value
            bValue.value = value
        }

    override fun addListener(configurationEntry: ConfigurationEntry): Boolean {
        aValue.addListener((configurationEntry))
        bValue.addListener((configurationEntry))
        return true
    }

}

class Or(val aValue: BoolValue, val bValue: BoolValue) : BoolValue {
    override var value: Boolean
        get() = aValue.value || bValue.value
        set(value) {
            aValue.value = value
            bValue.value = value
        }

    override fun addListener(configurationEntry: ConfigurationEntry): Boolean {
        aValue.addListener((configurationEntry))
        bValue.addListener((configurationEntry))
        return true
    }

}