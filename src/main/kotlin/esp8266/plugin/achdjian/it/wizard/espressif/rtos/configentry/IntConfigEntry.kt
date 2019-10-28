package esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.BoolValue
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import java.awt.Color
import java.awt.GridLayout
import java.awt.event.KeyEvent
import java.text.NumberFormat
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class IntConfigEntry(text: String, configEntry: String, var value: Int, private val min: Int, private val max: Int, dependsOn: List<BoolValue>) : ConfigurationEntry(text, configEntry, dependsOn), DocumentListener {
    private val textField = IntegerTextField()


    constructor(text: String, configEntry: String, value: Int, min: Int, max: Int, dependsOn: BoolValue) :
            this(text, configEntry, value, min, max, listOf(dependsOn))

    constructor(text: String, configEntry: String, value: Int, min: Int, max: Int) :
            this(text, configEntry, value, min, max, listOf())

    override fun set(key: String, value: String) {
        this.value = value.toInt()
    }

    override fun addConfigution(configurations: MutableMap<String, String>) {
        if (dependsOn.all { it.value }) {
            configEntry.forEach { configurations[it] = value.toString() }
        }
    }

    override fun changedUpdate(documentEvent: DocumentEvent) {
        val length = documentEvent.document.length
        val str = documentEvent.document.getText(0, length)
        try {
            value = str.toInt()
            textField.background = IntegerTextField.NORMAL_BACKGROUND
        } catch (e: Exception) {
            textField.background = IntInputVerifier.ERROR_BACKGROUND
        }

    }

    override fun insertUpdate(documentEvent: DocumentEvent) {
        val length = documentEvent.document.length
        val str = documentEvent.document.getText(0, length)
        try {
            value = str.toInt()
            textField.background = IntegerTextField.NORMAL_BACKGROUND
        } catch (e: Exception) {
            textField.background = IntInputVerifier.ERROR_BACKGROUND
        }
    }

    override fun removeUpdate(documentEvent: DocumentEvent) {
        val length = documentEvent.document.length
        val str = documentEvent.document.getText(0, length)
        try {
            value = str.toInt()
            textField.background = IntegerTextField.NORMAL_BACKGROUND
        } catch (e: Exception) {
            textField.background = IntInputVerifier.ERROR_BACKGROUND
        }
    }

    override fun createRow(): JComponent {
        val panel = JPanel()
        panel.layout = GridLayout(1, 2)
        panel.add(JLabel(text))
        textField.text = text
        textField.inputVerifier = IntInputVerifier(min, max)
        textField.text = value.toString()
        textField.document.addDocumentListener(this)
        panel.add(textField)
        panel.isVisible = dependsOn.all { it.value == true }
        this.panel = panel
        return panel
    }

}


class IntInputVerifier(private val min: Int, private val max: Int) : InputVerifier() {
    companion object {
        val ERROR_BACKGROUND = Color(255, 215, 215)
    }

    override fun verify(component: JComponent?): Boolean {
        if (component is JFormattedTextField) {
            try {
                val value = component.text.toInt()
                if (value < min || value > max) {
                    JOptionPane.showMessageDialog(null, "The value must be between $min and $max", "Error", JOptionPane.ERROR_MESSAGE)
                    component.background = ERROR_BACKGROUND
                    return false
                }
                return true
            } catch (e: Exception) {
                component.background = ERROR_BACKGROUND
                return false
            }
        }
        return true
    }

}


class IntegerTextField : JFormattedTextField(NumberFormat.getInstance()) {
    companion object {
        val NORMAL_BACKGROUND = Color(255, 255, 255)
    }

    init {
        background = NORMAL_BACKGROUND
    }

    override fun processKeyBinding(ks: KeyStroke, e: KeyEvent, condition: Int, pressed: Boolean): Boolean {
        background = NORMAL_BACKGROUND
        return super.processKeyBinding(ks, e, condition, pressed)
    }
}