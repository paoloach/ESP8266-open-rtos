package esp8266.plugin.achdjian.it.wizard.espressif.rtos.components

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations.And
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations.BoolConfigEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations.IntConfigEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations.Not


val freeRTOSHz = IntConfigEntry("Tick rate (Hz)", "FREERTOS_HZ", 100, 1, 1000)

val freeRTOSConfig = listOf<ConfigurationEntry>(
        IntConfigEntry("Timer stack size", "FREERTOS_TIMER_STACKSIZE", 2048, 1, 10000),
        IntConfigEntry("ISR stack size", "FREERTOS_ISR_STACKSIZE", 1536, 1, 10000),
        IntConfigEntry("FreeRTOS hook max number", "FREERTOS_MAX_HOOK", 2, 1, 16),
        freeRTOSHz,
        BoolConfigEntry("Enable \"reent\" function", "FREERTOS_ENABLE_REENT", false),
        BoolConfigEntry("Link FreeRTOS global data to IRAM", "FREERTOS_GLOBAL_DATA_LINK_IRAM", true, And( Not(lwipHighThroughput), Not(socFullICache) ))
)