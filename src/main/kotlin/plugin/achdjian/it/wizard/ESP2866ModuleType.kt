package plugin.achdjian.it.wizard

import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.module.ModuleTypeManager
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

class ESP2866ModuleType : ModuleType<EPS2866ModuleBuilder>(ID) {
    override fun createModuleBuilder(): EPS2866ModuleBuilder = EPS2866ModuleBuilder()

    override fun getName(): String = "ESP2866 free rtos"

    override fun getDescription(): String = "ESP2866 free rtos module"

    override fun getNodeIcon(isOpened: Boolean): Icon  = IconLoader.getIcon("/icons/rust.svg")

    companion object {
        private val ID = "ESP2866_MODULE"
        val INSTANCE: ESP2866ModuleType by lazy { ModuleTypeManager.getInstance().findByID(ID) as ESP2866ModuleType }
    }
}