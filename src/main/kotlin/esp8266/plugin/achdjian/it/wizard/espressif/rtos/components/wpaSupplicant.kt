package esp8266.plugin.achdjian.it.wizard.espressif.rtos.components

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry.BoolConfigEntry

val wpaSupplicantMenu = listOf<ConfigurationEntry>(
        BoolConfigEntry("Use faster div, esptmod, sqr, montgomery multiplication algorithm", "LTM_FAST",true)
)