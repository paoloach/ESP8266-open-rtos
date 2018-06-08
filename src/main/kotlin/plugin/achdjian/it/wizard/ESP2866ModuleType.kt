package plugin.achdjian.it.wizard

import com.intellij.openapi.module.ModuleType
import javax.swing.Icon

class ESP2866ModuleType : ModuleType<EPS2866ModuleBuilder>(ID) {
    override fun createModuleBuilder(): EPS2866ModuleBuilder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDescription(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getNodeIcon(isOpened: Boolean): Icon {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val ID = "ESP2866_MODULE"
    }
}