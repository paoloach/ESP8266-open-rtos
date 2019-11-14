package esp8266.plugin.achdjian.it.wizard.free.rtos

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.vfs.VirtualFile
import org.apache.commons.codec.Charsets
import it.achdjian.plugin.esp8266.settings.ESP8266SDKSettings
import it.achdjian.plugin.esp8266.settings.ESP8266SettingsState
import esp8266.plugin.achdjian.it.wizard.WizardData
import esp8266.plugin.achdjian.it.wizard.getResourceAsString
import it.achdjian.plugin.esp8266.settings.getESP8266Setting

fun createOTACMakeFileTool(path: VirtualFile, requestor: Any, wizardData: WizardData, projectName: String): VirtualFile {
    val rbootFolder= path.createChildDirectory(requestor, "rboot")
    val file = rbootFolder.findOrCreateChildData(requestor, "CMakeLists.txt")
    var cmakeFile = getResourceAsString("templates/free/rboot/CMakeLists.txt")
    val setting = getESP8266Setting()

    cmakeFile =cmakeFile
            .replace("__{project_name}__", projectName)
            .replace("__{ESP_OPEN_RTOS_DIR}__", "set(ESP_OPEN_RTOS_DIR ${setting.freeRtosPath})")
            .replace("__{ESPTOOLS2_PATH}__", "set(ESPTOOL2 ${setting.esptool2})")

    file.setBinaryContent(cmakeFile.toByteArray(Charsets.UTF_8))
    createLinkerFiles(path, requestor, wizardData)
    return file
}

fun createLinkerFiles(path: VirtualFile, requestor: Any, wizardData: WizardData){
    var program0in = getResourceAsString("templates/free/program0.ld")
    val program0Out = path.findOrCreateChildData(requestor, "program0.ld")
    program0Out.setBinaryContent(program0in.toByteArray(Charsets.UTF_8))




    var program1in = getResourceAsString("templates/free/program1.ld")
    program1in = program1in
            .replace("__{ROM_START}__", wizardData.flashSize.startingRom2())
            .replace("__{ROM_SIZE}__", wizardData.flashSize.sizeRom2())
    val program1Out = path.findOrCreateChildData(requestor, "program1.ld")
    program1Out.setBinaryContent(program1in.toByteArray(Charsets.UTF_8))
}