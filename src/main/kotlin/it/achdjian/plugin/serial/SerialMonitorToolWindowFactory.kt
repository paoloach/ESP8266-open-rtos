package it.achdjian.plugin.serial

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import org.jetbrains.annotations.NotNull

class SerialMonitorToolWindowFactory : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val serialMonitorView = project.getComponent(SerialMonitorView::class.java)
        serialMonitorView.initToolWindow(toolWindow)
    }

    override fun shouldBeAvailable(project:Project):Boolean {
        return true
    }
}