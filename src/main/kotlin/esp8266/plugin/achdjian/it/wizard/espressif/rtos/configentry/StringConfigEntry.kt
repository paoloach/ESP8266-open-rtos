package esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import java.awt.GridLayout
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField


open class StringConfigEntry(text: String, configEntry: String, var value: String, dependsOn: List<BoolConfigEntry>) : ConfigurationEntry(text, configEntry, dependsOn) {
    /**
     * @param test the display text
     * @param configEntry the define key used
     * @param value the value assignd to configEntry
     */
    constructor(text: String, configEntry: String, value: String) : this(text, configEntry, value, listOf())
    constructor(text: String, configEntry: String, value: String, dependsOn: BoolConfigEntry) : this(text, configEntry, value, listOf(dependsOn))

    private val jTextField = JTextField()

    override fun set(key: String, value: String) {
        if (isConfig(key))
            this.value = value
    }

    override fun addConfigution(configurations: MutableMap<String, String>) {
        if (dependsOn.all { it.value }) {
            configEntry.forEach { configurations[it] = "\"$value\"" }
        }
    }

    override fun createRow(): JComponent {
        val panel = JPanel()
        panel.layout = GridLayout(1, 2)
        panel.add(JLabel(text))

        jTextField.text = value
        jTextField.addFocusListener(StringFocusListener(this))
        jTextField.addActionListener {
            value = jTextField.text
        }
        panel.add(jTextField)
        panel.isVisible = dependsOn.all { it.value == true }
        this.panel = panel
        return panel
    }

    fun updateValue() {
        value = jTextField.text
    }
}


class StringFocusListener(private val stringConfigEntry: StringConfigEntry) : FocusListener {
    override fun focusLost(p0: FocusEvent?) {
        stringConfigEntry.updateValue()
    }

    override fun focusGained(p0: FocusEvent?) {
    }

}