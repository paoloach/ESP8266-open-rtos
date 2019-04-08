package esp8266.plugin.achdjian.it.wizard.espressif.rtos

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.vfs.VirtualFile
import esp8266.plugin.achdjian.it.settings.ESP8266SDKSettings
import esp8266.plugin.achdjian.it.settings.ESP8266SettingsState
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.Constants.CONFIG_DIR
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.Constants.CONFIG_FILE_NAME
import esp8266.plugin.achdjian.it.wizard.getResourceAsString
import org.apache.commons.codec.Charsets



fun createEspressifRTOSCMake(projectName: String, wizardData: MenuWizardData, creator: Creator): String {
    val templatePath = creator.createTemplatePath()
    var cmakelists = getResourceAsString("$templatePath/CMakeLists.txt")
    val setting = ApplicationManager.getApplication().getComponent(ESP8266SettingsState::class.java, ESP8266SDKSettings.DEFAULT) as ESP8266SettingsState
    val configFlags = creator.createConfigFlags(wizardData)
    cmakelists = cmakelists
            .replace("__{project_name}__", projectName)
            .replace("__{ESPRESSIF_RTOS_DIR}__", "set(RTOS_DIR ${setting.espressifRtosPath})")
            .replace("__{flash_mode}__", wizardData.flashMode)
            .replace("__{flash_freq}__", wizardData.flashFreq)
            .replace("__{flash_size}__", wizardData.flashSize)
            .replace("__{flash_size_hex}__", wizardData.flashSizeHex)
            .replace("__{esptool_port}__", wizardData.espToolPort)
            .replace("__{esptool_before}__", wizardData.espToolBefore)
            .replace("__{esptool_after}__", wizardData.espToolAfter)
            .replace("__{esptool_baudRate}__", wizardData.espToolBaudRate)
            .replace("__{flash_SPI_MODE}__", wizardData.spiFlashModeHex)
            .replace("__{CONFIG_FLAS}__", configFlags)
    if (wizardData.compressUpload)
        cmakelists = cmakelists.replace("__{esptool_compression}__", "-z")
    else
        cmakelists = cmakelists.replace("__{esptool_compression}__", "-u")
    return cmakelists
}

fun createEspressifRTORSubCMake(projectName: String, wizardMenu: MenuWizardData, path: VirtualFile, creator: Creator) {
    makeSubCMake("appUpdate", projectName, wizardMenu, path, creator)
    makeSubCMake("awsIOT", projectName, wizardMenu, path, creator)
    makeSubCMake("bootloader", projectName, wizardMenu, path, creator)
    makeSubCMake("bootloaderSupport", projectName, wizardMenu, path, creator)
    makeSubCMake("cjson", projectName, wizardMenu, path, creator)
    makeSubCMake("coap", projectName, wizardMenu, path, creator)
    makeSubCMake("esp8266", projectName, wizardMenu, path, creator)
    makeSubCMake("espTls", projectName, wizardMenu, path, creator)
    makeSubCMake("heap", projectName, wizardMenu, path, creator)
    makeSubCMake("freeRTOS", projectName, wizardMenu, path, creator)
    makeSubCMake("jsmn", projectName, wizardMenu, path, creator)
    makeSubCMake("log", projectName, wizardMenu, path, creator)
    makeSubCMake("lwip", projectName, wizardMenu, path, creator)
    makeSubCMake("mqtt", projectName, wizardMenu, path, creator)
    makeSubCMake("mdns", projectName, wizardMenu, path, creator)
    makeSubCMake("newlib", projectName, wizardMenu, path, creator)
    makeSubCMake("nvs_flash", projectName, wizardMenu, path, creator)
    makeSubCMake("pthread", projectName, wizardMenu, path, creator)
    makeSubCMake("ringbuffer", projectName, wizardMenu, path, creator)
    makeSubCMake("smartConfig", projectName, wizardMenu, path, creator)
    makeSubCMake("sodium", projectName, wizardMenu, path, creator)
    makeSubCMake("spiffs", projectName, wizardMenu, path, creator)
    makeSubCMake("spiFlash", projectName, wizardMenu, path, creator)
    makeSubCMake("ssl", projectName, wizardMenu, path, creator)
    makeSubCMake("tcpipAdapter", projectName, wizardMenu, path, creator)
    makeSubCMake("util", projectName, wizardMenu, path, creator)
    makeSubCMake("wpaSupplicant", projectName, wizardMenu, path, creator)

}


fun createSdkConfigFile(wizardData: MenuWizardData, path: VirtualFile, creator: Creator) {
    val entries = wizardData.entriesMenu

    var subFolder = path.findChild(CONFIG_DIR)
    if (subFolder == null)
        subFolder = path.createChildDirectory(null, CONFIG_DIR)

    val sdkConfig = subFolder.findOrCreateChildData(null, CONFIG_FILE_NAME)
    val configurations = HashMap<String, String>()
    entries.forEach { it.addConfigution(configurations) }
    val builder = StringBuilder()
    builder.appendln("#define CONFIG_TOOLPREFIX \"xtensa-lx106-elf-\"")
    builder.appendln("#define CONFIG_TARGET_PLATFORM_ESP8266 1")
    builder.appendln("#define CONFIG_APP1_OFFSET  0x10000")
    builder.appendln("#define CONFIG_APP1_SIZE  0xF0000")
    builder.appendln(creator.config(wizardData))

    configurations.forEach {
        builder.append("#define CONFIG_").append(it.key).append(" ").appendln(it.value)
    }
    ApplicationManager.getApplication().runWriteAction {
        sdkConfig.setBinaryContent(builder.toString().toByteArray())
    }
}


private fun makeSubCMake(subdir: String, projectName: String, wizardMenu: MenuWizardData, path: VirtualFile, creator: Creator) {

    val templatePath = creator.createTemplatePath()

    var cmakelists = getResourceAsString("$templatePath/$subdir/CMakeLists.txt")
    val setting = ApplicationManager.getApplication().getComponent(ESP8266SettingsState::class.java, ESP8266SDKSettings.DEFAULT) as ESP8266SettingsState
    cmakelists = cmakelists
            .replace("__{project_name}__", projectName)
            .replace("__{ESPRESSIF_RTOS_DIR}__", "set(RTOS_DIR ${setting.espressifRtosPath})")
            .replace("__{flash_mode}__", wizardMenu.flashMode)
            .replace("__{flash_freq}__", wizardMenu.flashFreq)
            .replace("__{flash_size}__", wizardMenu.flashSize)

    val subFolder = path.createChildDirectory(null, subdir)
    val cmakeFile = subFolder.findOrCreateChildData(null, "CMakeLists.txt")
    ApplicationManager.getApplication().runWriteAction {
        cmakeFile.setBinaryContent(cmakelists.toByteArray(Charsets.UTF_8))
    }
}

fun copyLinkScript(path: VirtualFile, creator: Creator) {

    val templatePath = creator.createTemplatePath()

    var sourceLinkScript = getResourceAsString("$templatePath/ld/esp8266.common.ld")
    val subFolder = path.createChildDirectory(null, "ld")
    val destLinkScript = subFolder.findOrCreateChildData(null, "esp8266.common.ld")
    ApplicationManager.getApplication().runWriteAction {
        destLinkScript.setBinaryContent(sourceLinkScript.toByteArray(Charsets.UTF_8))
    }
}
