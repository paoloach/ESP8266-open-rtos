package esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations

import esp8266.plugin.achdjian.it.ui.ButtonTitledBorder
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JComponent
import javax.swing.JPanel

class SubPanelConfigEntry(text: String, private val entries: List<ConfigurationEntry>, dependsOn: List<BoolConfigEntry>) : ConfigurationEntry(text, "", dependsOn = dependsOn) {
    constructor(text: String, entries: List<ConfigurationEntry>, dependsOn: BoolConfigEntry) :
            this(text, entries, dependsOn = listOf(dependsOn))

    constructor(text: String, entries: List<ConfigurationEntry>) :
            this(text, entries, listOf())

    override fun isConfig(configKey: String): Boolean {
        return entries.any { it.isConfig(configKey) }
    }

    override fun set(key: String, value: String) {
        entries.first { it.isConfig(key) }.set(key, value)
    }

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