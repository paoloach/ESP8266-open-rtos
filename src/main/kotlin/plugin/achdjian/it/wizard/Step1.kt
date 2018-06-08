package plugin.achdjian.it.wizard

import com.intellij.BundleBase
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.openapi.diagnostic.Logger
import com.intellij.ui.layout.Row
import com.intellij.ui.layout.panel
import java.awt.event.ActionEvent
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JComponent

fun Row.checkBox(text: String, actionListener: (event: ActionEvent) -> Unit) {
    val button = JCheckBox(BundleBase.replaceMnemonicAmpersand(text))
    button.addActionListener(actionListener)
    button()
}

class Step1 : ModuleWizardStep() {
    companion object {
        private val LOG = Logger.getInstance(Step1::class.java)
    }

    override fun getComponent(): JComponent = panel(title="extenal") {
        row("ad7709x"){ checkBox("ad7709x",{})}
    }

    override fun updateDataModel() {
        LOG.info("Update data model")
    }


}