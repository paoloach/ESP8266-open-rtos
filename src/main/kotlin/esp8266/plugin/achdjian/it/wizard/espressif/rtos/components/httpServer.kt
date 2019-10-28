package esp8266.plugin.achdjian.it.wizard.espressif.rtos.components

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry.IntConfigEntry

val httpServerMenu = listOf<ConfigurationEntry>(
        IntConfigEntry("Max HTTP Request Header Length", "HTTPD_MAX_REQ_HDR_LEN", 512, 1, 10000),
        IntConfigEntry("Max HTTP URI Length", "HTTPD_MAX_URI_LEN",512, 1, 10000)
)