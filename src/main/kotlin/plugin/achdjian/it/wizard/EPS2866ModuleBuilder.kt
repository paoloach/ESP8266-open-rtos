package plugin.achdjian.it.wizard

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ModifiableRootModel

class EPS2866ModuleBuilder: ModuleBuilder() {
    override fun getModuleType(): ModuleType<*> {
        return ModuleType.EMPTY
    }

    override fun setupRootModel(modifiableRootModel: ModifiableRootModel?) {
    }
}