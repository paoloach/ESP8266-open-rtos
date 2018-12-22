package esp8266.plugin.achdjian.it.ui

import java.awt.event.ItemEvent
import javax.swing.JCheckBox

class CheckBox(text: String, change: (status: Boolean) -> Unit) : JCheckBox(text) {
    init {
        addItemListener {
            change(it.stateChange == ItemEvent.SELECTED)
        }
    }
}