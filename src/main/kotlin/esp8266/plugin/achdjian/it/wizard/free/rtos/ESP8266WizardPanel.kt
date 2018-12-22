package esp8266.plugin.achdjian.it.wizard.free.rtos

import esp8266.plugin.achdjian.it.ui.panel
import esp8266.plugin.achdjian.it.wizard.FlashSize
import esp8266.plugin.achdjian.it.wizard.WizardData
import java.awt.BorderLayout
import java.awt.event.ItemEvent
import javax.swing.JPanel


class ESP8266WizardPanel(clionPanel: JPanel, val wizardData: WizardData) : JPanel(BorderLayout()) {
    companion object {
        val availableSize = FlashSize.values().map { it.strSize() }.toTypedArray()
        val availableMode = arrayOf("qio", "qout", "dio")
        val availableSpeed = arrayOf("80", "40", "26", "20")
    }

    init {
        add(clionPanel, BorderLayout.PAGE_START)
        val p = panel("ESP 8266 Free RTOS configuration") {
            row("flash Size") { comboBox(availableSize, "512KB", { wizardData.flashSize = FlashSize.getElement(it.item.toString()) }) }
            row("Flash Mode") { comboBox(availableMode, "qio", { wizardData.flashMode = it.item.toString() }) }
            row("Flash Speed") { comboBox(availableSpeed, "40", { wizardData.flashSpeed = it.item.toString() }) }
            row("ESP port") { textArea("/dev/ttyUSB0", { wizardData.espPort = it?.document.toString() }) }
            row() { checkBox("Float Support", { wizardData.floatSupport = it.stateChange == ItemEvent.SELECTED }) }
            row() { checkBox("OTA Support", { wizardData.otaSupport = it.stateChange == ItemEvent.SELECTED }) }
        }

        add(p)

        add(panel(title = "Extras") { wizardData.extras.forEach { row(it) } }, BorderLayout.PAGE_END)
    }
}