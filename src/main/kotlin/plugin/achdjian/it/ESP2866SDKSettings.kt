package plugin.achdjian.it

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class ESP2866SDKSettings : ProjectComponent, Configurable {
    override fun isModified(): Boolean {
        return true
    }

    override fun getDisplayName() = "ESP2866/"

    override fun apply() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createComponent(): JComponent? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}