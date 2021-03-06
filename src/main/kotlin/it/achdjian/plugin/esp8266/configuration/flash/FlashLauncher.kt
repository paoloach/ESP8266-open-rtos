package it.achdjian.plugin.esp8266.configuration.flash

import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.KillableColoredProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
import com.jetbrains.cidr.cpp.cmake.CMakeSettings
import com.jetbrains.cidr.cpp.cmake.workspace.CMakeWorkspace

class FlashLauncher(
    executeEnvironment: ExecutionEnvironment,
    private val flashConfigurationState: FlashConfigurationState
) : CommandLineState(executeEnvironment){

    override fun startProcess(): ProcessHandler {
        val cmdLine = GeneralCommandLine("make")
        cmdLine.addParameter("flash")

        val cMakeWorkspace = CMakeWorkspace.getInstance(environment.project)
        cmdLine.withWorkDirectory(cMakeWorkspace.projectDir)

        CMakeWorkspace.getInstance(environment.project).modelTargets.first{it.name=="flash"}?.buildConfigurations?.first()?.let {
            val profileInfo = cMakeWorkspace.getProfileInfoFor(it)
            cmdLine.withWorkDirectory( profileInfo.generationDir )
        }

        CMakeSettings.getInstance(environment.project).profiles.firstOrNull()?.let {
            cmdLine.withEnvironment(it.additionalEnvironment)
        }

        cmdLine.withEnvironment("ESPPORT",  "/dev/${flashConfigurationState.port}")
        cmdLine.withEnvironment("ESPBAUD", flashConfigurationState.baud.toString())
        return KillableColoredProcessHandler(cmdLine)
    }

}