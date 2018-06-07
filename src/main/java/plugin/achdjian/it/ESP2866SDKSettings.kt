package plugin.achdjian.it

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.ui.layout.panel
import javax.swing.JComponent
import javax.swing.JTextField

class ESP2866SDKSettings(val project:Project) : ProjectComponent, Configurable {
    val rootPath = JTextField()
    override fun isModified(): Boolean {
        return true
    }

    override fun getDisplayName() = "ESP2866"


    override fun apply() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disposeComponent() {

    }

    override fun createComponent() = panel() {
        row("Root path: ") { rootPath }
    }

    override fun reset() {

    }
}