package esp8266.plugin.achdjian.it.wizard.free.rtos.extras

import com.intellij.openapi.vfs.VirtualFile
import javax.swing.JComponent

interface ExtraModule {
    var enabled: Boolean
    val name: String
    val component: JComponent

    fun imageDependecy(): String
    fun flashImages(): String
    fun createCMakeList(path: VirtualFile, requestor: Any, projectName: String)
}