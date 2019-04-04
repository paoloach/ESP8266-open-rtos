package esp8266.plugin.achdjian.it.wizard.espressif.rtos.components

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations.BoolConfigEntry

val appUpdateMenu = listOf<ConfigurationEntry>(
        BoolConfigEntry("Check APP binary data sum after downloading", "APP_UPDATE_CHECK_APP_SUM", true),
        BoolConfigEntry("Check APP binary data hash after downloading", "APP_UPDATE_CHECK_APP_HASH",false)
)