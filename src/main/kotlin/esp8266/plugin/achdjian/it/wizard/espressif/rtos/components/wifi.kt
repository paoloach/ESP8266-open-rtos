package esp8266.plugin.achdjian.it.wizard.espressif.rtos.components

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations.BoolConfigEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations.IntConfigEntry

val wifiTxRateSequenceFromHigh = BoolConfigEntry("Set wifi tx rate from 54M to 1M", "WIFI_TX_RATE_SEQUENCE_FROM_HIGH", defaultValue = false)


val wifiMenu = listOf<ConfigurationEntry>(
        IntConfigEntry("Max scan AP numbe", "SCAN_AP_MAX", 32, 1, 64),
        wifiTxRateSequenceFromHigh
)


