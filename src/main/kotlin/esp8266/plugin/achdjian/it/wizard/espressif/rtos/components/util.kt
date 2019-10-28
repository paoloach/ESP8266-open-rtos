package esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry

val espSha = BoolConfigEntry("Enable Espressif SHA", "ESP_SHA", defaultValue = true)

val utilMenu = listOf<ConfigurationEntry>(
        espSha
)