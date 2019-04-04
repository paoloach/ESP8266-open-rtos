package esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations

import com.intellij.openapi.ui.ComboBox
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import java.awt.GridLayout
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class ChoiceConfigEntry(text: String, configEntry: String, private var choices: Map<BoolConfigEntry, String>, private val default: BoolConfigEntry, dependsOn: List<BoolConfigEntry>) : ConfigurationEntry(text, configEntry, dependsOn) {
    constructor(text: String, configEntry: String, choices: List<BoolConfigEntry>, default: BoolConfigEntry) : this(text, configEntry, choices.associate { Pair(it, "") }, default, listOf<BoolConfigEntry>())
    constructor(text: String, configEntry: String, choices: List<BoolConfigEntry>, default: BoolConfigEntry, dependsOn: BoolConfigEntry) : this(text, configEntry, choices.associate { Pair(it, "") }, default, listOf(dependsOn))
    constructor(text: String, configEntry: String, choices: Map<BoolConfigEntry, String>, default: BoolConfigEntry) : this(text, configEntry, choices, default, listOf())

    private var choiced = default
    val choiceText: String get() = choices[choiced].orEmpty()

    override fun isConfig(configKey: String): Boolean {
        return configEntry.any { it == configKey } || choices.keys.any { it.isConfig(configKey) }
    }


    override fun set(key: String, value: String) {
        if (configEntry.any { it == key }) {
            choices.filter { it.value == value }.forEach { choiced = it.key }
        }
        if (choices.any { it.key.isConfig(key) }) {
            choices.forEach {
                it.key.value = it.key.isConfig(key)
            }
        }
    }

    override fun addConfigution(configurations: MutableMap<String, String>) {
        if (dependsOn.all { it.value }) {
            if (choiceText.isNotBlank())
                configEntry.forEach { configurations[it] = choiceText }
            choiced.addConfigution(configurations)
        }
    }


    override fun createRow(): JComponent {
        val panel = JPanel()
        panel.layout = GridLayout(1, 2)
        panel.add(JLabel(text))
        val comboBox = ComboBox<String>()
        comboBox.addActionListener {
            default.value = false
            choices.keys.firstOrNull { c -> c.text == comboBox.selectedItem }?.let { c ->
                c.value = true;choiced = c
            }
        }
        choices.keys.forEach {
            comboBox.addItem(it.text)
        }
        comboBox.selectedItem = default.text
        panel.add(comboBox)
        panel.isVisible = dependsOn.all { it.value == true }
        this.panel = panel
        return panel
    }


}
