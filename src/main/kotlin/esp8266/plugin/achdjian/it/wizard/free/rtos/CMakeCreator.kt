package esp8266.plugin.achdjian.it.wizard.free.rtos

import com.intellij.openapi.application.ApplicationManager
import it.achdjian.plugin.esp8266.settings.ESP8266SDKSettings
import it.achdjian.plugin.esp8266.settings.ESP8266SettingsState
import esp8266.plugin.achdjian.it.wizard.WizardData
import esp8266.plugin.achdjian.it.wizard.getResourceAsString
import it.achdjian.plugin.esp8266.settings.getESP8266Setting

fun createFreeRTOSCMake(wizardData: WizardData, projectName: String): String {

    var cmakelists = selectCMakeListFile(wizardData.otaSupport)
    val setting = getESP8266Setting()
    cmakelists = cmakelists
            .replace("__{project_name}__", projectName)
            .replace("__{ESP_OPEN_RTOS_DIR}__", "set(ESP_OPEN_RTOS_DIR ${setting.freeRtosPath})")
            .replace("__{FLASH_SIZE}__", wizardData.flashSize.strSize())
            .replace("__{FLASH_MODE}__", wizardData.flashMode)
            .replace("__{FLASH_SPEED}__", wizardData.flashSpeed)
            .replace("__{ESP_PORT}__", wizardData.espPort)
            .replace("__{ROM2START}__", wizardData.flashSize.startingFlashRom2())
            .replace("__{EXTRAS_STATIC_LIBRARIES}__", createExtrasStaticLibraries(wizardData))
            .replace("__{ADD_EXTRAS_MODULES}__", createExtraModules(wizardData))
            .replace("__{extra_image_dependencies}__", createExtraImageDependencies(wizardData))
            .replace("__{extra_flash_images}__", createExtraFlashImages(wizardData))


    val floatSupport = if (wizardData.floatSupport) {
        "1"
    } else {
        "0"
    }
    cmakelists = cmakelists.replace(" __{FLOAT_SUPPORT}__", floatSupport)
    return cmakelists
}


fun selectCMakeListFile(ota: Boolean): String {
    return if (ota)
        getResourceAsString("templates/free/CMakeLists_ota.txt")
    else
        getResourceAsString("templates/free/CMakeLists.txt")
}


fun createExtrasStaticLibraries(wizardData: WizardData) = wizardData.extras.filter { it.enabled }.joinToString(separator = "") { it.name + " "}
fun createExtraModules(wizardData: WizardData) = wizardData.extras.filter { it.enabled }.joinToString(separator = "") { "add_subdirectory(${it.name})" }
fun createExtraImageDependencies(wizardData: WizardData) = wizardData.extras.filter { it.enabled }.joinToString(separator = "") { it.imageDependecy() }
fun createExtraFlashImages(wizardData: WizardData) = wizardData.extras.filter { it.enabled }.joinToString(separator = "") { it.flashImages() }

