package esp8266.plugin.achdjian.it.wizard.espressif.rtos.components

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry.BoolConfigEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry.ChoiceConfigEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry.IntConfigEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry.StringConfigEntry


val mqttV3_1 = BoolConfigEntry("V3.1", "MQTT_V3_1", true)
val mqttV3_1_1 = BoolConfigEntry("V3.1.1", "MQTT_V3_1_1", true)
val mqttVersion = ChoiceConfigEntry("MQTT version", "MQTT_VERSION", listOf(
        mqttV3_1,
        mqttV3_1_1
), mqttV3_1)


val cleanMqttSession = BoolConfigEntry("Clean Session", "CLEAN_SESSION", true)
val keepMqttSession = BoolConfigEntry("Keep Session", "KEEP_SESSION", false)

val mqttSession = ChoiceConfigEntry("MQTT Session", "MQTT_SESSION", listOf(
        cleanMqttSession,
        keepMqttSession
), cleanMqttSession)

val mqttMenu = listOf<ConfigurationEntry>(
        mqttVersion,
        StringConfigEntry("MQTT client ID", "MQTT_CLIENT_ID", "espressif_sample"),
        IntConfigEntry("MQTT keep-alive(seconds)", "MQTT_KEEP_ALIVE", 30, 1, 10000),
        StringConfigEntry("MQTT username", "MQTT_USERNAME", "espressif"),
        StringConfigEntry("MQTT password", "MQTT_PASSWORD", "admin"),
        mqttSession,
        IntConfigEntry("MQTT send buffer", "MQTT_SEND_BUFFER", 2048, 1, 10000),
        IntConfigEntry("MQTT recv buffer", "MQTT_RECV_BUFFER", 2048, 1, 10000),
        IntConfigEntry("MQTT send cycle(ms)", "MQTT_SEND_CYCLE", 30000, 1, 100000),
        IntConfigEntry("MQTT recv cycle(ms)", "MQTT_RECV_CYCLE", 0, 0, 100000),
        IntConfigEntry("MQTT ping timeout(ms)", "MQTT_PING_TIMEOUT", 3000, 0, 100000)
)