package plugin.achdjian.it.wizard

import com.jetbrains.cidr.cpp.cmake.projectWizard.CMakeProjectStepAdapter
import com.jetbrains.cidr.cpp.cmake.projectWizard.CMakeProjectWizard
import java.io.File

class NewESP2866ProjectWizard: CMakeProjectWizard("New ESP2866 free rtos Project", "NewESP2866Project") {
    val adapter = NewESP2866ProjectForm("untitled-0",  File(lastDir).getPath());

    override fun tryFinish(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doRunWizard() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}