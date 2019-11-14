package it.achdjian.plugin.esp8266.generator

import com.intellij.execution.RunManagerEx
import com.intellij.execution.impl.RunManagerImpl
import com.intellij.execution.impl.RunnerAndConfigurationSettingsImpl
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.jetbrains.cidr.cpp.cmake.CMakeSettings
import com.jetbrains.cidr.cpp.cmake.projectWizard.generators.CMakeAbstractCProjectGenerator
import com.jetbrains.cidr.cpp.cmake.projectWizard.generators.settings.CMakeProjectSettings
import com.jetbrains.cidr.cpp.cmake.workspace.CMakeWorkspace
import com.jetbrains.cidr.cpp.execution.CMakeAppRunConfigurationType
import it.achdjian.plugin.esp8266.configuration.flash.FlashConfigurationType
import it.achdjian.plugin.esp8266.configuration.flash.FlashRunConfiguration
import it.achdjian.plugin.esp8266.configurationName
import it.achdjian.plugin.esp8266.configurator.CONFIG_FILE_NAME
import it.achdjian.plugin.esp8266.configurator.ESP8266WizardPanel
import it.achdjian.plugin.esp8266.configurator.MAIN_DIR
import it.achdjian.plugin.esp8266.configurator.WizardData
import it.achdjian.plugin.esp8266.entry_type.ConfigurationEntry
import it.achdjian.plugin.esp8266.settings.getESP8266Setting
import it.achdjian.plugin.esp8266.ui.getResourceAsString
import java.util.concurrent.TimeoutException
import javax.swing.JComponent

class CProjectGenerator : CMakeAbstractCProjectGenerator() {
    companion object {
        val esp32Project: Key<Boolean> = Key.create("ESP8266")
        private val LOG = Logger.getInstance(CProjectGenerator::class.java)
        private val allowedCmakeTarget = listOf("flash", "app","apidoc","partition_table")
    }

    val wizardData = WizardData()
//    var settingPanel = ESP8266WizardPanel(createSettingsPanel(), wizardData.entries)

    override fun getName(): String = "ESP8266 C project"

    override fun createSourceFiles(projectName: String, path: VirtualFile): Array<VirtualFile> {
        createSdkConfigFile(wizardData.entries, path)
        val files = mutableListOf<VirtualFile>()
        createMainFile(path, files)
        return files.toTypedArray()
    }

    override fun getSettingsPanel(): JComponent? {
        val settingPanel = ESP8266WizardPanel(createSettingsPanel(), wizardData.entries)
        return settingPanel
    }

    override fun getCMakeFileContent(projectName: String): String {
        val state =getESP8266Setting()

        var cmakelists = getResourceAsString("templates/CMakeLists.txt")
        cmakelists = cmakelists
                .replace("__{project_name}__", projectName)
                .replace("__{SDK_PATH}__", state.espressifRtosPath)
        return cmakelists
    }

    override fun generateProject(project: Project, path: VirtualFile, cmakeSetting: CMakeProjectSettings, module: Module) {
        super.generateProject(project, path, cmakeSetting, module)
        generateCrossCompilerConfiguration(project)
        generateFlashConfiguration(project)
        project.putUserData(esp32Project, true)
        removeCmakeConfiguration(project)
        project.save()
    }

    private fun removeCmakeConfiguration(project: Project){
        val cMakeWorkspace = CMakeWorkspace.getInstance(project)
        ApplicationManager.getApplication().executeOnPooledThread {
            try {
                cMakeWorkspace.waitForReloadsToFinish(1000000)
                val runManager = RunManagerEx.getInstanceEx(project) as RunManagerImpl
                val toRemove = runManager
                    .allSettings
                    .filter { it.type is CMakeAppRunConfigurationType }
                    .filter {  !allowedCmakeTarget.contains(it.name)}

                runManager.removeConfigurations(toRemove)

            } catch (e: TimeoutException) {
                LOG.warn("workspace building too long")
            }
        }
    }

    private fun generateFlashConfiguration(project: Project) {
        val runManager = RunManagerEx.getInstanceEx(project) as RunManagerImpl
        FlashConfigurationType.factory?.let {
            val newConfig = RunnerAndConfigurationSettingsImpl(
                runManager,
                FlashRunConfiguration(project, it, configurationName),
                false
            )

            runManager.addConfiguration(newConfig)
            runManager.selectedConfiguration = newConfig

            project.save()
        }

    }

    private fun generateCrossCompilerConfiguration(project: Project) {
        val cMakeWorkspace = CMakeWorkspace.getInstance(project)
        val settings = cMakeWorkspace.settings

        val state =getESP8266Setting()
        val env = mapOf("PATH" to "${state.crosscompilerPath}:/usr/bin:/sbin:/bin:/opt/bin")
        val releaseProfile = CMakeSettings.Profile("Release", "Release", "", "", true, env, null, null)

        settings.profiles = listOf(releaseProfile)

    }
}

fun createMainFile(path: VirtualFile, files: MutableList<VirtualFile>) {
    ApplicationManager.getApplication().runWriteAction {
        val mainDir = path.findChild(MAIN_DIR) ?: path.createChildDirectory(null, MAIN_DIR)
        val cMakeListsFile = mainDir.findOrCreateChildData(null, "CMakeLists.txt")
        val cMakeListsData = getResourceAsString("templates/main/CMakeLists.txt")

        val helloWordFile = mainDir.findOrCreateChildData(null, "hello_world_main.c")

        val helloWordData = getResourceAsString("templates/main/hello_world_main.c")
        cMakeListsFile.setBinaryContent(cMakeListsData.toByteArray())
        helloWordFile.setBinaryContent(helloWordData.toByteArray())
        files.add(helloWordFile)
    }
}


fun createSdkConfigFile(entries: List<ConfigurationEntry>, path: VirtualFile) {
    ApplicationManager.getApplication().runWriteAction {
        val sdkConfig = path.findOrCreateChildData(null, CONFIG_FILE_NAME)
        val configurations = mutableListOf<Pair<String, String>>()
        entries.forEach { it.addConfiguration(configurations) }

        val data = configurations.joinToString(separator = "\n") { "CONFIG_${it.first}=${it.second}" }


        sdkConfig.setBinaryContent(data.toByteArray())
    }
}