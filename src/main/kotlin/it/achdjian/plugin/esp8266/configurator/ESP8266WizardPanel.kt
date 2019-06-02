package it.achdjian.plugin.esp8266.configurator

import it.achdjian.plugin.esp8266.entry_type.ConfigurationEntry
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JPanel


class ESP8266WizardPanel(clionPanel: JPanel, entriesMenu: List<ConfigurationEntry>) : JPanel(BorderLayout()) {

    val internalPanel = JPanel()
    init {
        add(clionPanel, BorderLayout.PAGE_START)
        internalPanel.layout = BoxLayout(internalPanel, BoxLayout.Y_AXIS)

        entriesMenu.forEach {
            configuratorViewFactory(it)?.let { view ->
                internalPanel.add(view)
            }
        }
        add(internalPanel, BorderLayout.CENTER)

    }
}