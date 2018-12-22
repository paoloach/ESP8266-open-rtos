package esp8266.plugin.achdjian.it.ui

import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class HexTextField(name: String, var value: Int) : JPanel() {
    init {
        layout = GridLayout(1, 2)
        add(JLabel(name))
        val jTextField = JTextField()

        jTextField.text = value.toString(16)
        jTextField.addActionListener {
            value = jTextField.text.toInt()
        }

        add(jTextField)
    }

}

