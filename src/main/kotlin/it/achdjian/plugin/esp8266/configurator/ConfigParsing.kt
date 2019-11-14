package it.achdjian.plugin.esp8266.configurator

import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.MessageType
import esp8266.plugin.achdjian.it.ui.showMessage
import java.io.File

fun configParsing(project: Project): Map<String, String> {
    val result = mutableMapOf<String, String>()


    val modules = ModuleManager.getInstance(project).modules
    if (modules.isEmpty())
        return result
    val moduleFile = modules[0].moduleFile?:return result
    val parent = moduleFile.parent.parent


    val config = parent.findChild(CONFIG_FILE_NAME)

    config?.let {
        if (!it.exists()) {
            showMessage(project, MessageType.ERROR,
                    "<strong>Project Setting</strong>unable to find the file $CONFIG_FILE_NAME" )
            throw Exception()
        }

        File(it.path).
            forEachLine {
            if (it.startsWith("CONFIG_",false)) {
                val config = it.substring(7)
                val div = config.indexOfFirst { it == '=' }
                var key = config.substring(0, div).trim()
                var value = config.substring(div + 1).trim()
                if (value.startsWith("\"")) {
                    value = value.substring(1)
                }
                if (value.endsWith("\"")){
                    value = value.substring(IntRange(0, value.length-2))
                }
                result[key] = value
            }
        }
    }
    return result
}