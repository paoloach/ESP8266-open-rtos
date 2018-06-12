package plugin.achdjian.it.wizard

import com.intellij.ide.util.projectWizard.AbstractNewProjectStep
import com.intellij.ide.util.projectWizard.CustomStepProjectGenerator
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.impl.welcomeScreen.AbstractActionWithPanel
import com.intellij.platform.DirectoryProjectGenerator
import com.intellij.platform.DirectoryProjectGeneratorBase
import javax.swing.Icon

class ESP2866ProjectGenerator : DirectoryProjectGeneratorBase<ESP2866Data>(),
        CustomStepProjectGenerator<ESP2866Data> {
    override fun generateProject(p0: Project, p1: VirtualFile, p2: ESP2866Data, p3: Module) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLogo(): Icon? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createStep(p0: DirectoryProjectGenerator<ESP2866Data>?, p1: AbstractNewProjectStep.AbstractCallback<ESP2866Data>?): AbstractActionWithPanel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}