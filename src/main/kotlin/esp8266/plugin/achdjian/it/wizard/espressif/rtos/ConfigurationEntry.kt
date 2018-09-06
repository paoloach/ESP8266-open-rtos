package esp8266.plugin.achdjian.it.wizard.espressif.rtos

import com.intellij.openapi.ui.ComboBox
import esp8266.plugin.achdjian.it.ui.ButtonTitledBorder
import java.awt.BorderLayout
import java.awt.Color
import java.awt.GridLayout
import java.awt.event.*
import java.text.NumberFormat
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import kotlin.properties.Delegates

abstract class ConfigurationEntry(val text: String, val configEntry: List<String>, var dependsOn: List<BoolConfigEntry> = listOf()) {
    var panel: JPanel? = null
    abstract fun createRow(): JComponent
    abstract fun addConfigution(configurations: MutableMap<String, String>)
    fun dependsChangValue() {
        panel?.isVisible = dependsOn.all { it.value }
    }

    constructor(text: String, configEntry: String, dependsOn: List<BoolConfigEntry>) : this(text, listOf(configEntry), dependsOn = dependsOn)

    init {
        dependsOn.forEach {
            it.addListener(this)
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

    class IntConfigEntry(text: String, configEntry: String, private var value: Int, private val min: Int, private val max: Int, dependsOn: List<BoolConfigEntry>) : ConfigurationEntry(text, configEntry, dependsOn), DocumentListener {


        private val textField = IntegerTextField()

        constructor(text: String, configEntry: String, value: Int, min: Int, max: Int, dependsOn: BoolConfigEntry) :
                this(text, configEntry, value, min, max, listOf(dependsOn))

        constructor(text: String, configEntry: String, value: Int, min: Int, max: Int) :
                this(text, configEntry, value, min, max, listOf())

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

    class StringFocusListener(val stringConfigEntry: StringConfigEntry) : FocusListener{
        override fun focusLost(p0: FocusEvent?) {
            stringConfigEntry.updateValue()
        }

        override fun focusGained(p0: FocusEvent?) {
        }

    }

    open class StringConfigEntry(text: String, configEntry: String, var value: String, dependsOn: List<BoolConfigEntry>) : ConfigurationEntry(text, configEntry, dependsOn) {
        constructor(text: String, configEntry: String, value: String) : this(text, configEntry, value, listOf())
        constructor(text: String, configEntry: String, value: String, dependsOn: BoolConfigEntry) : this(text, configEntry, value, listOf(dependsOn))
        val jTextField = JTextField()

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
            jTextField.addFocusListener( StringFocusListener(this))
            jTextField.addActionListener {
                value = jTextField.text
            }
            panel.add(jTextField)
            panel.isVisible = dependsOn.all { it.value == true }
            this.panel = panel
            return panel
        }

        public fun updateValue() {
            value = jTextField.text;
        }
    }

    class HexConfigEntry(text: String, configEntry: String, value: Int, dependsOn: List<BoolConfigEntry>) : StringConfigEntry(text, configEntry, value.toString(16), dependsOn) {
        constructor(text: String, configEntry: String, value: Int) : this(text, configEntry, value, listOf())
        constructor(text: String, configEntry: String, value: Int, dependsOn: BoolConfigEntry) : this(text, configEntry, value, listOf(dependsOn))

        override fun addConfigution(configurations: MutableMap<String, String>) {
            configEntry.forEach { configurations[it] = "0x" + value.toInt(16) }
        }


    }

    class BoolConfigEntry(text: String, configEntry: String, defaultValue: Boolean = false, dependsOn: List<BoolConfigEntry>, private val associated: List<BoolConfigEntry> = listOf()) : ConfigurationEntry(text, configEntry, dependsOn), ItemListener {
        private val listener = ArrayList<ConfigurationEntry>()
        var value: Boolean by Delegates.observable(defaultValue) { _, _, _ ->
            listener.forEach { it.dependsChangValue() }
        }

        constructor(text: String, configEntry: String, defaultValue: Boolean = false, dependsOn: BoolConfigEntry, associated: List<BoolConfigEntry> = listOf()) : this(text, configEntry, defaultValue, listOf(dependsOn), associated)
        constructor(text: String, configEntry: String, value: Boolean, associated: List<BoolConfigEntry>) : this(text, configEntry, value, listOf(), associated)
        constructor(text: String, configEntry: String, defaultValue: Boolean = false) : this(text, configEntry, defaultValue, listOf(), listOf())

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


        fun addListener(configurationEntry: ConfigurationEntry) = listener.add(configurationEntry)
    }

    class ChoiceConfigEntry(text: String, configEntry: String, private var choices: Map<BoolConfigEntry, String>, private val default: BoolConfigEntry, dependsOn: List<BoolConfigEntry>) : ConfigurationEntry(text, configEntry, dependsOn) {
        constructor(text: String, configEntry: String, choices: List<BoolConfigEntry>, default: BoolConfigEntry) : this(text, configEntry, choices.associate { Pair(it, "") }, default, listOf<BoolConfigEntry>())
        constructor(text: String, configEntry: String, choices: List<BoolConfigEntry>, default: BoolConfigEntry, dependsOn: BoolConfigEntry) : this(text, configEntry, choices.associate { Pair(it, "") }, default, listOf(dependsOn))
        constructor(text: String, configEntry: String, choices: Map<BoolConfigEntry, String>, default: BoolConfigEntry) : this(text, configEntry, choices, default, listOf())

        private var choiced = default
        val choiceText: String get() = choices[choiced].orEmpty()

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
                choices.keys.firstOrNull { c -> c.text.equals(comboBox.selectedItem) }?.let { c ->
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

    class SubPanelConfigEntry(text: String, private val entries: List<ConfigurationEntry>, dependsOn: List<BoolConfigEntry>) : ConfigurationEntry(text, "", dependsOn = dependsOn) {
        constructor(text: String, entries: List<ConfigurationEntry>, dependsOn: BoolConfigEntry) :
                this(text, entries, dependsOn = listOf(dependsOn))

        constructor(text: String, entries: List<ConfigurationEntry>) :
                this(text, entries, listOf())

        override fun addConfigution(configurations: MutableMap<String, String>) {
            if (dependsOn.all { it.value })
                entries.forEach { it.addConfigution(configurations) }
        }

        override fun createRow(): JComponent {
            val panel = JPanel()
            panel.layout = BorderLayout()
            val internalPanel = JPanel()
            panel.add(internalPanel, BorderLayout.CENTER)
            internalPanel.layout = BoxLayout(internalPanel, BoxLayout.Y_AXIS)
            entries.forEach { internalPanel.add(it.createRow()) }
            internalPanel.isVisible = false
            panel.border = ButtonTitledBorder(text, panel) {
                internalPanel.setVisible(!it)
            }

            panel.isVisible = dependsOn.all { it.value == true }
            this.panel = panel
            return panel
        }

    }
}