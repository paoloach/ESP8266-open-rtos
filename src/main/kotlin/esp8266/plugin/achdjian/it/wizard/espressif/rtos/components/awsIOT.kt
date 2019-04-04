package esp8266.plugin.achdjian.it.wizard.espressif.rtos.components

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations.BoolConfigEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations.IntConfigEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations.StringConfigEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations.SubPanelConfigEntry


val awsIotOverrideThingShadowRxBuffer = BoolConfigEntry("Override Shadow RX buffer size", "AWS_IOT_OVERRIDE_THING_SHADOW_RX_BUFFER", false)
val thingShadowMenu = listOf<ConfigurationEntry>(
        awsIotOverrideThingShadowRxBuffer,
        IntConfigEntry("Maximum RX Buffer (bytes)", "AWS_IOT_SHADOW_MAX_SIZE_OF_RX_BUFFER",513, 32, 65536,awsIotOverrideThingShadowRxBuffer),
        IntConfigEntry("Maximum unique client ID size (bytes)", "AWS_IOT_SHADOW_MAX_SIZE_OF_UNIQUE_CLIENT_ID_BYTES",80, 4, 1000),
        IntConfigEntry("Maximum simultaneous responses", "AWS_IOT_SHADOW_MAX_SIMULTANEOUS_ACKS",10, 1, 100),
        IntConfigEntry("Maximum simultaneous Thing Name operations", "AWS_IOT_SHADOW_MAX_SIMULTANEOUS_THINGNAMES",10, 1, 100),
        IntConfigEntry("Maximum expected JSON tokens", "AWS_IOT_SHADOW_MAX_JSON_TOKEN_EXPECTED",120, 1, 10000),
        IntConfigEntry("Maximum topic length (not including Thing Name)", "AWS_IOT_SHADOW_MAX_SHADOW_TOPIC_LENGTH_WITHOUT_THINGNAME",60, 10, 1000),
        IntConfigEntry("Maximum Thing Name length", "AWS_IOT_SHADOW_MAX_SIZE_OF_THING_NAME",20, 4, 1000)
)

val awsIOTMenu = listOf<ConfigurationEntry>(
        StringConfigEntry("AWS IoT Endpoint Hostname", "AWS_IOT_MQTT_HOST",""),
        IntConfigEntry("AWS IoT MQTT Port", "AWS_IOT_MQTT_PORT",8883, 0, 6535),
        IntConfigEntry("MQTT TX Buffer Length", "AWS_IOT_MQTT_TX_BUF_LEN",512, 32, 65536),
        IntConfigEntry("MQTT RX Buffer Length", "AWS_IOT_MQTT_RX_BUF_LEN",512, 32, 65536),
        IntConfigEntry("Maximum MQTT Topic Filters", "AWS_IOT_MQTT_NUM_SUBSCRIBE_HANDLERS",5, 1, 100),
        IntConfigEntry("Auto reconnect initial interval (ms)", "AWS_IOT_MQTT_MIN_RECONNECT_WAIT_INTERVAL",1000, 10, 3600000),
        IntConfigEntry("Auto reconnect maximum interval (ms)", "AWS_IOT_MQTT_MAX_RECONNECT_WAIT_INTERVAL",128000, 32, 3600000),
        SubPanelConfigEntry("Thing Shadow",thingShadowMenu)
)