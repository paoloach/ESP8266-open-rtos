package esp8266.plugin.achdjian.it.wizard.free.rtos.extras

import com.intellij.BundleBase
import com.intellij.openapi.vfs.VirtualFile
import esp8266.plugin.achdjian.it.ui.CheckBox
import esp8266.plugin.achdjian.it.ui.HexTextField
import esp8266.plugin.achdjian.it.ui.TextAndBrowserButton
import esp8266.plugin.achdjian.it.wizard.getResourceAsString
import org.apache.commons.codec.Charsets
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JComponent
import javax.swing.JPanel

class SpiffsModule : ExtraModule {
    override var enabled = false
    override val name = "spiffs"
    var singleton = "0"
    var baseAddr = 0x200000
    val size = 0x10000
    var logPageSize = 256
    var logBlockSize = 8192
    var folderToInclude: VirtualFile? = null

    override fun imageDependecy() = folderToInclude?.let { "add_dependencies(createImage make_spiffs_image)\n" } ?: ""
    override fun flashImages() = folderToInclude?.let { "0x" + baseAddr.toString(16) + " \${CMAKE_CURRENT_BINARY_DIR}/spiffs/spiffs.bin\n" }
            ?: ""

    override val component: JComponent
        get() = createComponent()

    private fun createComponent(): JComponent {
        val panel = JPanel()
        panel.layout = BorderLayout()
        val internalPanel = JPanel()
        internalPanel.layout = BoxLayout(internalPanel, BoxLayout.Y_AXIS)
        internalPanel.isVisible = false
        val checkBox = CheckBox(BundleBase.replaceMnemonicAmpersand(name)) {
            enabled = it
            internalPanel.isVisible = enabled
        }

        panel.add(checkBox, BorderLayout.NORTH)
        panel.add(internalPanel, BorderLayout.CENTER)

        val singletonPanel = CheckBox("Singleton") {
            singleton = if (it) "1" else "0"
        }
        val folderToInclude = TextAndBrowserButton("Directory put in SPIFFS image.") {
            folderToInclude = it
        }

        internalPanel.add(singletonPanel)
        internalPanel.add(HexTextField("Base Addr", baseAddr))
        internalPanel.add(HexTextField("Size", size))
        internalPanel.add(HexTextField("Log Page Size", logPageSize))
        internalPanel.add(HexTextField("Log Block Size", logBlockSize))
        internalPanel.add(folderToInclude)
        return panel
    }

    override fun createCMakeList(path: VirtualFile, requestor: Any, projectName: String) {
        val rootFolder = path.createChildDirectory(requestor, "spiffs")
        val cMakeList = rootFolder.findOrCreateChildData(requestor, "CMakeLists.txt")
        val template = getResourceAsString("templates/free/extras/spiffsCMakeLists.txt")

        val makeSpiffsImageCmd = folderToInclude?.let { makeSpiffsImage(it) } ?: " "

        val spiffsCMakeFile = template
                .replace("__{project_name}__", projectName)
                .replace("__{singleton}__", singleton)
                .replace("__{baseAddr}__", "0x" + baseAddr.toString(16))
                .replace("__{size}__", "0x" + size.toString(16))
                .replace("__{logPageSize}__", "0x" + logPageSize.toString(16))
                .replace("__{logBlockSize}__", "0x" + logBlockSize.toString(16))
                .replace("__{make_spiffs_image}__", makeSpiffsImageCmd)


        cMakeList.setBinaryContent(spiffsCMakeFile.toByteArray(Charsets.UTF_8))
    }

    private fun makeSpiffsImage(folder: VirtualFile): String {
        return "add_custom_target(make_spiffs_image)\n" +
                "add_dependencies(make_spiffs_image mkspiffs)\n" +
                "add_custom_command(TARGET make_spiffs_image\n" +
                "\tPOST_BUILD\n" +
                "\tCOMMENT \"create spiffs image\"\n" +
                "\tWORKING_DIRECTORY  " + folder.parent.path + "\n" +
                "\tCOMMAND  \${CMAKE_CURRENT_BINARY_DIR}/mkspiffs " +
                "-D " + folder.name +
                " -f \${CMAKE_CURRENT_BINARY_DIR}/spiffs.bin " +
                "-s $size " +
                "-p $logPageSize " +
                "-b $logBlockSize \n)\n"

    }

}