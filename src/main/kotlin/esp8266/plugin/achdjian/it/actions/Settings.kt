package esp8266.plugin.achdjian.it.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogBuilder
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.DialogWrapper.OK_EXIT_CODE
import com.intellij.openapi.ui.Messages
import com.intellij.ui.ScrollPaneFactory
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.Constants.CONFIG_FILE_NAME
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.Creator3_1
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.MenuWizardData
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configParsing
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.createSdkConfigFile
import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.BoxLayout
import javax.swing.JPanel

class Settings : AnAction("ESP8266 RTOS setting...") {

    class SaveAction(val menuWizardData: MenuWizardData, val project: Project, private val dialogWrapper: DialogWrapper) : AbstractAction("Save") {
        override fun actionPerformed(p0: ActionEvent?) {
            createSdkConfigFile(menuWizardData, project.baseDir, creatorFactory(menuWizardData))
            dialogWrapper.close(OK_EXIT_CODE)
        }

    }

    companion object {
        private val LOG = Logger.getInstance(Settings::class.java)
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = AnAction.getEventProject(event)
        project?.let { loadSetting(project) }
    }

    private fun loadSetting(project: Project) {
        try {
            val menuWizard = MenuWizardData()
            val listEntry = menuWizard.entriesMenu
            val configEntries = configParsing(project)
            if (configEntries.isEmpty()) {
                Messages.showErrorDialog(project, "Unable to find $CONFIG_FILE_NAME file", "RTOS ESP 8266")
            } else {

                configEntries.forEach {
                    listEntry.filter { entry -> entry.isConfig(it.key) }.forEach { entry -> entry.set(it.key, it.value) }
                }

                val contentPanel = JPanel()
                contentPanel.layout = BoxLayout(contentPanel, BoxLayout.Y_AXIS)

                listEntry.forEach {
                    contentPanel.add(it.createRow())
                }

                val scrollPane = ScrollPaneFactory.createScrollPane(contentPanel, false)

                val dialog = DialogBuilder(project)
                        .centerPanel(scrollPane)
                        .title("Settings")

                dialog.addAction(SaveAction(menuWizard, project, dialog.dialogWrapper))
                dialog.addCancelAction()
                dialog.show()
            }
        } catch (e: Exception) {
            LOG.error("Unable to create setting: ", e)
        }
    }
}

private fun creatorFactory(wizardMenu: MenuWizardData) = Creator3_1()