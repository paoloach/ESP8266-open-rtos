package esp8266.plugin.achdjian.it.wizard.free.rtos.extras

import com.intellij.BundleBase
import com.intellij.openapi.vfs.VirtualFile
import esp8266.plugin.achdjian.it.wizard.getResourceAsString
import org.apache.commons.codec.Charsets
import java.awt.event.ItemEvent
import javax.swing.JCheckBox
import javax.swing.JComponent

class SimpleModule(override val name: String) : ExtraModule {
    override var enabled = false
    override fun imageDependecy() = ""
    override fun flashImages() = ""

    override val component: JComponent
        get() = createComponent()

    private fun createComponent(): JComponent {
        val checkBox = JCheckBox(BundleBase.replaceMnemonicAmpersand(name))
        checkBox.addItemListener { enabled = it.stateChange == ItemEvent.SELECTED }
        return checkBox
    }


    override fun createCMakeList(path: VirtualFile, requestor: Any, projectName: String) {
        val rootFolder= path.createChildDirectory(requestor, name)
        val cMakeList = rootFolder.findOrCreateChildData(requestor, "CMakeLists.txt")
        val template = getResourceAsString("templates/free/extras/simpleCMakeLists.txt")

        val spiffsCMakeFile =template
                .replace("__{project_name}__", projectName)
                .replace("__{name}__", name)

        cMakeList.setBinaryContent(spiffsCMakeFile.toByteArray(Charsets.UTF_8))

    }
}