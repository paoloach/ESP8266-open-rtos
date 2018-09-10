package esp8266.plugin.achdjian.it.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComponentWithBrowseButton
import com.intellij.openapi.ui.TextComponentAccessor
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.TextFieldWithHistoryWithBrowseButton
import com.intellij.ui.components.installFileCompletionAndBrowseDialog
import esp8266.plugin.achdjian.it.ui.panel
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class ESP8266SDKSettings(private val project: Project) : ProjectComponent, Configurable {


    private var ccPath: VirtualFile? = null
    private var cxxPath: VirtualFile? = null
    private var freeRtosPath: VirtualFile? = null
    private var espressifRtosPath: VirtualFile? = null
    private var esptool2Path: VirtualFile? = null
    private var p: JPanel? = null

    companion object {
        const val GCC = "xtensa-lx106-elf-gcc"
        const val CXX = "xtensa-lx106-elf-g++"
        val DEFAULT = ESP8266SettingsState(GCC, CXX)
    }

    override fun isModified(): Boolean {
        val state = ApplicationManager.getApplication().getComponent(ESP8266SettingsState::class.java, DEFAULT) as ESP8266SettingsState

        val modified = (state.ccPath != ccPath?.canonicalPath)
                || (state.cxxPath != cxxPath?.canonicalPath)
                || (state.freeRtosPath != freeRtosPath?.canonicalPath)
                || (state.espressifRtosPath != espressifRtosPath?.canonicalPath)
                || (state.esptool2 != esptool2Path?.canonicalPath)
        return modified
    }

    override fun getDisplayName() = "ESP8266"


    override fun apply() {
        val state = ApplicationManager.getApplication().getComponent(ESP8266SettingsState::class.java, DEFAULT) as ESP8266SettingsState
        ccPath?.canonicalPath?.let { state.ccPath = it }
        cxxPath?.canonicalPath?.let { state.cxxPath = it }
        freeRtosPath?.canonicalPath?.let { state.freeRtosPath = it }
        esptool2Path?.canonicalPath?.let { state.esptool2 = it }
        espressifRtosPath?.canonicalPath?.let { state.espressifRtosPath = it }
    }

    override fun disposeComponent() {

    }


    override fun createComponent(): JComponent {
        val p = panel("Tools path") {
            val state = project.getComponent(ESP8266SettingsState::class.java, DEFAULT) as ESP8266SettingsState
            row("FREE ESP8266 RTOS path: ") {
                val component = TextFieldWithHistoryWithBrowseButton()
                val editor = component.childComponent.textEditor
                editor.text = state.freeRtosPath
                installFileCompletionAndBrowseDialog(
                        project,
                        component,
                        editor,
                        "ESP8266 free rtos path",
                        FileChooserDescriptorFactory.createSingleFolderDescriptor(),
                        TextComponentAccessor.TEXT_FIELD_WITH_HISTORY_WHOLE_TEXT) {
                    freeRtosPath = it
                    it.path
                }
                this.component = component
            }
            row("ESPRESSIF ESP8266 RTOS path: ") {
                val component = TextFieldWithHistoryWithBrowseButton()
                val editor = component.childComponent.textEditor
                editor.text = state.espressifRtosPath
                installFileCompletionAndBrowseDialog(
                        project,
                        component,
                        editor,
                        "ESPRESSIF rtos path",
                        FileChooserDescriptorFactory.createSingleFolderDescriptor(),
                        TextComponentAccessor.TEXT_FIELD_WITH_HISTORY_WHOLE_TEXT) {
                    espressifRtosPath = it
                    it.path
                }
                this.component = component
            }
            row("esptool2 path: ") {
                val component = TextFieldWithHistoryWithBrowseButton()
                val editor = component.childComponent.textEditor
                editor.text = state.esptool2
                installFileCompletionAndBrowseDialog(
                        project,
                        component,
                        editor,
                        "ESP8266 esptool2 path",
                        FileChooserDescriptorFactory.createSingleFileDescriptor(),
                        TextComponentAccessor.TEXT_FIELD_WITH_HISTORY_WHOLE_TEXT) {
                    esptool2Path = it
                    it.path
                }
                this.component = component
            }
            row("GCC path: ") {
                val component = TextFieldWithHistoryWithBrowseButton()
                val editor = component.childComponent.textEditor
                editor.text = state.ccPath
                installFileCompletionAndBrowseDialog(
                        project,
                        component,
                        editor,
                        "ESP8266 gcc path",
                        FileChooserDescriptorFactory.createSingleFileDescriptor(),
                        TextComponentAccessor.TEXT_FIELD_WITH_HISTORY_WHOLE_TEXT) {
                    ccPath = it
                    it.path
                }
                this.component = component
            }
            row("G++ path: ") {
                val component = TextFieldWithHistoryWithBrowseButton()
                val editor = component.childComponent.textEditor
                editor.text = state.cxxPath
                installFileCompletionAndBrowseDialog(
                        project,
                        component,
                        editor,
                        "ESP8266 g++ path",
                        FileChooserDescriptorFactory.createSingleFileDescriptor(),
                        TextComponentAccessor.TEXT_FIELD_WITH_HISTORY_WHOLE_TEXT) {
                    cxxPath = it
                    it.path
                }
                this.component = component
            }
        }
        this.p = p
        return p
    }

    override fun reset() {

    }
}