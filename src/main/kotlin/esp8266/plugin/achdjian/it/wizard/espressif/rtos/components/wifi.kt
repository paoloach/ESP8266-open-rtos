package esp8266.plugin.achdjian.it.wizard.espressif.rtos.components

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations.BoolConfigEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations.ChoiceConfigEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations.IntConfigEntry

val wifiTxRateSequenceFromHigh = BoolConfigEntry("Set wifi tx rate from 54M to 1M", "WIFI_TX_RATE_SEQUENCE_FROM_HIGH", defaultValue = true)
val esp8266WifiDebugLogEnable = BoolConfigEntry("Enable WiFi debug log", "ESP8266_WIFI_DEBUG_LOG_ENABLE", defaultValue = false)
val esp8266WifiDebugLogError = BoolConfigEntry("Error", "ESP8266_WIFI_DEBUG_LOG_ERROR", true)

val wifiMenu = listOf<ConfigurationEntry>(
        IntConfigEntry("Max scan AP numbe", "SCAN_AP_MAX", 32, 1, 64),
        wifiTxRateSequenceFromHigh,
        esp8266WifiDebugLogEnable,
        ChoiceConfigEntry("The DEBUG level is enabled", "ESP8266_WIFI_DEBUG_LOG_LEVEL", listOf(
                esp8266WifiDebugLogError,
                BoolConfigEntry("Warning", "ESP8266_WIFI_DEBUG_LOG_WARNING", false),
                BoolConfigEntry("Info", "ESP8266_WIFI_DEBUG_LOG_INFO", false),
                BoolConfigEntry("Debug", "ESP8266_WIFI_DEBUG_LOG_DEBUG", false),
                BoolConfigEntry("Verbose", "ESP8266_WIFI_DEBUG_LOG_VERBOSE", false)
        ), esp8266WifiDebugLogError,dependsOn =  esp8266WifiDebugLogEnable),
        BoolConfigEntry("core", "ESP8266_WIFI_DEBUG_LOG_SUBMODULE_CORE", false, dependsOn = esp8266WifiDebugLogEnable),
        BoolConfigEntry("scan", "ESP8266_WIFI_DEBUG_LOG_SUBMODULE_SCAN", false, dependsOn = esp8266WifiDebugLogEnable),
        BoolConfigEntry("power management", "ESP8266_WIFI_DEBUG_LOG_SUBMODULE_PM", false, dependsOn = esp8266WifiDebugLogEnable),
        BoolConfigEntry("NVS", "ESP8266_WIFI_DEBUG_LOG_SUBMODULE_NVS", false, dependsOn = esp8266WifiDebugLogEnable),
        BoolConfigEntry("TRC", "ESP8266_WIFI_DEBUG_LOG_SUBMODULE_TRC", false, dependsOn = esp8266WifiDebugLogEnable),
        BoolConfigEntry("EBUF", "ESP8266_WIFI_DEBUG_LOG_SUBMODULE_EBUF", false, dependsOn = esp8266WifiDebugLogEnable),
        BoolConfigEntry("NET80211", "ESP8266_WIFI_DEBUG_LOG_SUBMODULE_NET80211", false, dependsOn = esp8266WifiDebugLogEnable),
        BoolConfigEntry("TIMER", "ESP8266_WIFI_DEBUG_LOG_SUBMODULE_TIMER", false, dependsOn = esp8266WifiDebugLogEnable),

        BoolConfigEntry("ESPNOW", "ESP8266_WIFI_DEBUG_LOG_SUBMODULE_ESPNOW", false, dependsOn = esp8266WifiDebugLogEnable),
        BoolConfigEntry("MAC", "ESP8266_WIFI_DEBUG_LOG_SUBMODULE_MAC", false, dependsOn = esp8266WifiDebugLogEnable),
        BoolConfigEntry("wpa", "ESP8266_WIFI_DEBUG_LOG_SUBMODULE_WPA", false, dependsOn = esp8266WifiDebugLogEnable),
        BoolConfigEntry("wps", "ESP8266_WIFI_DEBUG_LOG_SUBMODULE_WPS", false, dependsOn = esp8266WifiDebugLogEnable)
        )


