package plugin.achdjian.it.wizard

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import javax.swing.JComponent
import javax.swing.JLabel


class EPS2866ModuleBuilder: ModuleBuilder() {
    override fun getModuleType(): ModuleType<*> = ESP2866ModuleType.INSTANCE

    override fun setupRootModel(modifiableRootModel: ModifiableRootModel?) {
    }

    override fun createWizardSteps(wizardContext: WizardContext, modulesProvider: ModulesProvider): Array<ModuleWizardStep> {
        return arrayOf(object : ModuleWizardStep() {
            override fun getComponent(): JComponent {
                return JLabel("Put your content here")
            }

            override fun updateDataModel() {
                LOG.info("Update data model")
            }
        })
    }

    companion object {
        private val LOG = Logger.getInstance(EPS2866ModuleBuilder::class.java)
    }
}