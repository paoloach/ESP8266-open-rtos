package plugin.achdjian.it.wizard

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.util.io.FileUtil
import plugin.achdjian.it.ESP2866SDKSettings
import plugin.achdjian.it.ESP8266SettingsState

fun createCMake(wizardData: WizardData, projectName: String): String {

    var cmakelists = selectCMakeListFile(wizardData.otaSupport)
    val setting = ApplicationManager.getApplication().getComponent(ESP8266SettingsState::class.java, ESP2866SDKSettings.DEFAULT) as ESP8266SettingsState
    cmakelists = cmakelists
            .replace("__{project_name}__", projectName)
            .replace("__{ESP_OPEN_RTOS_DIR}__", "set(ESP_OPEN_RTOS_DIR ${setting.rtosPath})")
            .replace("__{FLASH_SIZE}__", wizardData.flashSize.strSize())
            .replace("__{FLASH_MODE}__", wizardData.flashMode)
            .replace("__{FLASH_SPEED}__", wizardData.flashSpeed)
            .replace("__{ESP_PORT}__", wizardData.espPort)
            .replace("__{ROM2START}__", wizardData.flashSize.startingFlashRom2())
            .replace("__{EXTRAS_SOURCE_FILES}__", createExtrasSourceFilesSet(wizardData))
            .replace("__{EXTRAS_LIBRARIES}__", createExtrasLibraries(wizardData))
            .replace("__{EXTRAS_STATIC_LIBRARIES}__", createExtrasStaticLibraries(wizardData))
            .replace("__{EXTRA_INCLUDE_DIRECTORIES}__", createExtraIncludeDirectories(wizardData))



    val floatSupport = if (wizardData.floatSupport) {
        "1"
    } else {
        "0"
    }
    cmakelists = cmakelists.replace(" __{FLOAT_SUPPORT}__", floatSupport)
    return cmakelists
}

fun selectCMakeListFile(ota:Boolean): String {
    if (ota)
        return getResourceAsString("templates/CMakeLists_ota.txt")
    else
        return getResourceAsString("templates/CMakeLists.txt")
}

fun createExtrasSourceFilesSet(wizardData: WizardData): String {
    val builder = StringBuilder()

    wizardData.extras.filter { it.value }.forEach {
        builder
                .append("file(GLOB LIB_")
                .append(it.key.toUpperCase())
                .append("_SRC \${ESP_OPEN_RTOS_DIR}/extras/")
                .append(it.key)
                .appendln("/*.c)")
    }
    return builder.toString()
}

fun createExtrasLibraries(wizardData: WizardData): String {
    val builder = StringBuilder()

    wizardData.extras.filter { it.value }.forEach {
        builder
                .append("add_library(")
                .append(it.key)
                .append(" STATIC \${LIB_")
                .append(it.key.toUpperCase())
                .appendln("_SRC})")
    }
    return builder.toString()
}

fun createExtraIncludeDirectories(wizardData: WizardData): String {
    val builder = StringBuilder()

    wizardData.extras.filter { it.value }.forEach {
        builder.append("target_compile_options(")
                .append(it.key)
                .appendln(" PUBLIC \${COMMON_FLAGS})")
                .append("target_include_directories(")
                .append(it.key)
                .appendln(" PUBLIC")
                .appendln("\t\t\${ESP_OPEN_RTOS_DIR}/extras/${it.key}")
                .appendln("\t\t\${ESP_OPEN_RTOS_DIR}/extras/${it.key}/include")
                .appendln("\t\t\${ESP_OPEN_RTOS_DIR}/include")
                .appendln("\t\t\${ESP_OPEN_RTOS_DIR}/libc/xtensa-lx106-elf/include")
                .appendln("\t\t\${ESP_OPEN_RTOS_DIR}/extras/dhcpserver/include")
                .appendln("\t\t\${ESP_OPEN_RTOS_DIR}/extras/wificfg/..")
                .appendln("\t\t\${FREE_RTOS}/include")
                .appendln("\t\t\${FREE_RTOS}/portable/esp8266")
                .appendln("\t\t\${LWIP_DIR}/include")
                .appendln("\t\t\${ESP_OPEN_RTOS_DIR}/lwip/include")
                .appendln("\t\t\${LWIP_DIR}/include/compat/posix")
                .appendln("\t\t\${LWIP_DIR}/include/ipv4")
                .appendln("\t\t\${LWIP_DIR}/include/ipv4/lwip")
                .appendln("\t\t\${LWIP_DIR}/include/lwip")
                .appendln("\t\t\${ESP_OPEN_RTOS_DIR}/core/include")
                .appendln("\t\t\${ESP_OPEN_RTOS_DIR}/open_esplibs/include")
                .appendln("\t\t)")
    }

    return builder.toString()
}

fun createExtrasStaticLibraries(wizardData: WizardData): String {
    val builder = StringBuilder()

    wizardData.extras.filter { it.value }.forEach { builder.appendln(it.key) }

    return builder.toString()
}

fun getResourceAsString(resourceName: String): String {
    val resource = WizardData::class.java.classLoader.getResourceAsStream(resourceName)
    return if (resource != null) {
        FileUtil.loadTextAndClose(resource)
    } else {
        ""
    }

}

fun getResourceAsBytes(resourceName: String): ByteArray {
    val resource = WizardData::class.java.classLoader.getResourceAsStream(resourceName)
    return if (resource != null) {
        FileUtil.loadBytes(resource)
    } else {
        ByteArray(0)
    }

}