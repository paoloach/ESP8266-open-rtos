package esp8266.plugin.achdjian.it.wizard.espressif.rtos.components

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry.BoolConfigEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry.ChoiceConfigEntry


private val bootloaderInitSpiFlash = BoolConfigEntry("Bootloader init SPI flash", "BOOTLOADER_INIT_SPI_FLASH", true)
private val bootloaderCheckAppHash = BoolConfigEntry("Check APP binary data hash before loading", "BOOTLOADER_CHECK_APP_HASH", false)
private val defaultBootloaderLogLevel = BoolConfigEntry("Info", "LOG_BOOTLOADER_LEVEL_INFO", true)
private val bootloaderLogLevel = ChoiceConfigEntry("Bootloader log verbosity", "LOG_BOOTLOADER_LEVEL", mapOf(
        BoolConfigEntry("No output", "LOG_BOOTLOADER_LEVEL_NONE") to "0",
        BoolConfigEntry("Error", "LOG_BOOTLOADER_LEVEL_ERROR") to "1",
        BoolConfigEntry("Warning", "LOG_BOOTLOADER_LEVEL_WARN") to "2",
        defaultBootloaderLogLevel to "3",
        BoolConfigEntry("Debug", "LOG_BOOTLOADER_LEVEL_DEBUG") to "4",
        BoolConfigEntry("Verbose", "LOG_BOOTLOADER_LEVEL_VERBOSE") to "5"
), defaultBootloaderLogLevel)

val bootloaderCheckData = BoolConfigEntry("Check APP binary data sum before loading", "BOOTLOADER_CHECK_APP_SUM", true)

val bootloaderConfiguration = listOf(
        bootloaderInitSpiFlash,
        bootloaderLogLevel,
        bootloaderCheckData,
        bootloaderCheckAppHash
)