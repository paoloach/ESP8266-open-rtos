package esp8266.plugin.achdjian.it.wizard.espressif.rtos

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.MessageType
import esp8266.plugin.achdjian.it.ui.showMessage
import java.io.File

fun configParsing(project: Project): Map<String, String> {
    val result = HashMap<String, String>()
    val configDir = project.baseDir.findChild(Constants.CONFIG_DIR)
    if (configDir == null || !configDir.exists()) {
        return result;
    }

    val config = configDir.findChild(Constants.CONFIG_FILE_NAME)

    config?.let {
        if (!it.exists()) {
            showMessage(project, MessageType.ERROR,
                    "<strong>Project Setting</strong>unable to find the file" + Constants.CONFIG_PATH)
            throw Exception()
        }

        File(it.path).forEachLine {
            if (it.indexOf("#define", 0, true) == 0) {
                val piece = it.substring(8)
                val div = piece.indexOfFirst { it == ' ' }
                var key = piece.substring(0, div).trim()
                if (key.startsWith("CONFIG_")) {
                    key = key.substring(7)
                }
                var value = piece.substring(div + 1).trim()
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