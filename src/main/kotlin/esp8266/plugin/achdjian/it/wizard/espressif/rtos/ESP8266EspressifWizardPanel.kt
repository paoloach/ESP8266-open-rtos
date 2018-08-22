package esp8266.plugin.achdjian.it.wizard.espressif.rtos

import esp8266.plugin.achdjian.it.wizard.WizardData
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JPanel


class ESP8266EspressifWizardPanel(clionPanel: JPanel, entriesMenu: List<ConfigurationEntry>) : JPanel(BorderLayout()) {

    val internalPanel = JPanel()
    init {
        add(clionPanel, BorderLayout.PAGE_START)
        internalPanel.layout = BoxLayout(internalPanel, BoxLayout.Y_AXIS)

        entriesMenu.forEach {
            internalPanel.add(it.createRow())
        }
        add(internalPanel, BorderLayout.CENTER)

    }
}