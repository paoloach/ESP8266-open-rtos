package esp8266.plugin.achdjian.it.wizard.espressif.rtos.components

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry.IntConfigEntry

val mDNSMenu = listOf<ConfigurationEntry>(
        IntConfigEntry("Max number of services", "MDNS_MAX_SERVICES", 10, 1, 64),
        IntConfigEntry("Max stack size of MDNS", "MDNS_STACKSIZE",4096, 2048, 4096)
)