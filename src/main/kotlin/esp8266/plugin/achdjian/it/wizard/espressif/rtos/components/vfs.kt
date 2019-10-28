package esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry

val usingEspVFS = BoolConfigEntry("Using espressif VFS", "USING_ESP_VFS", defaultValue = true)

val vfsMenu = listOf<ConfigurationEntry>(
        usingEspVFS,
        BoolConfigEntry("Suppress select() related debug outputs", "SUPPRESS_SELECT_DEBUG_OUTPUT", defaultValue = true, dependsOn = usingEspVFS),
        BoolConfigEntry("Add support for termios.h", "SUPPORT_TERMIOS", defaultValue = true, dependsOn = usingEspVFS)
)