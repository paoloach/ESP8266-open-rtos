package plugin.achdjian.it.wizard

import com.intellij.ide.RecentProjectsManager
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.jetbrains.cidr.cpp.CPPLog
import com.jetbrains.cidr.cpp.cmake.CMakeProjectOpenProcessor
import com.jetbrains.cidr.cpp.cmake.projectWizard.CLionProjectWizardUtils.refreshProjectDir
import com.jetbrains.cidr.cpp.cmake.projectWizard.CMakeProjectStepAdapter
import com.jetbrains.cidr.cpp.cmake.projectWizard.CMakeProjectWizard
import org.jdom.JDOMException
import java.io.File
import java.io.FilenameFilter
import java.io.IOException
import java.util.*

class NewESP2866ProjectWizard: CMakeProjectWizard("New ESP2866 free rtos Project", "NewESP2866Project") {
    private val lastDir = Optional.ofNullable(RecentProjectsManager.getInstance().lastProjectCreationLocation)
            .orElse("")
    val adapter = NewESP2866ProjectForm("untitled-0",  File(lastDir).getPath());


    init {
        initWithStep(adapter)
    }

    override fun tryFinish(): Boolean {
        val projectRootPath = adapter.lastSelectedPath

        val projectRootDir = File(projectRootPath)
        if (projectRootDir.exists()) {
            val fileList = projectRootDir.list(FilenameFilter { dir, name -> !".DS_Store".equals(name, ignoreCase = true) && !"Thumbs.db".equals(name, ignoreCase = true) })
            if (fileList != null && fileList!!.size > 0) {
                val dialogAnswer = Messages.showYesNoDialog(String
                        .format("Directory \'%s\' already exists and not empty.\nWould you like to continue?", projectRootPath), "Project Directory Already Exists", Messages
                        .getQuestionIcon())
                if (dialogAnswer != 0) {
                    return false
                }
            }
        } else {
            try {
                VfsUtil.createDirectories(projectRootPath)
            } catch (e: IOException) {
                CPPLog.LOG.warn(e)
                return false
            } catch (e: RuntimeException) {
                CPPLog.LOG.warn(e)
                return false
            }

        }

        val projectRootDirParentPath = projectRootDir.getParent()
        if (projectRootDirParentPath != null) {
            RecentProjectsManager.getInstance().lastProjectCreationLocation = projectRootDirParentPath
        }

        try {
            createProject(adapter.projectName, projectRootPath)
            return true
        } catch (e: IOException) {
            CPPLog.LOG.warn(e)
            return false
        }

    }

    override fun doRunWizard() {
        val projectRoot = LocalFileSystem.getInstance().refreshAndFindFileByPath(this.adapter.lastSelectedPath) ?: return
        refreshProjectDir(projectRoot)
        val cMakeLists = projectRoot.findChild("CMakeLists.txt") ?: return
        val mainSketchFile = projectRoot.findChild(this.adapter.projectName + ".ino") ?: return

        val project: Project?
        try {
            project = ProjectManager.getInstance().loadAndOpenProject(cMakeLists.path)
        } catch (e: IOException) {
            CPPLog.LOG.warn(e)
            return
        } catch (e: JDOMException) {
            CPPLog.LOG.warn(e)
            return
        }


        if (project == null) {
            return
        }

        val projectSpec = CMakeProjectOpenProcessor.getHelper()
                .getAndClearFileToOpenData(project)

        deleteBuildOutputDir(projectSpec)
        OpenFileDescriptor(project, cMakeLists).navigate(false)
        OpenFileDescriptor(project, mainSketchFile).navigate(true)
    }

    @Throws(IOException::class)
    fun createProject(projectName: String, projectRootPath: String): String {
        var projectName = projectName
        val projectRoot = File(projectRootPath)
        val cMakeLists = File(projectRoot, "CMakeLists.txt")
        if (!cMakeLists.exists() && !cMakeLists.createNewFile()) {
            throw IOException("Cannot create file $cMakeLists")
        } else {
            projectName = FileUtil.sanitizeFileName(projectName)
            val mainSketchFile = File(projectRoot, "$projectName.ino")
            if (!mainSketchFile.exists() && !mainSketchFile.createNewFile()) {
                throw IOException("Cannot create file $mainSketchFile")
            } else {
                FileUtil.writeToFile(mainSketchFile, "test")

                // generate makefile

                return projectName
            }
        }
    }


}