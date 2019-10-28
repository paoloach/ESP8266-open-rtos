package esp8266.plugin.achdjian.it.wizard.espressif.configuration.flash

import com.intellij.execution.configurations.ConfigurationTypeBase

class FlashConfigurationType : ConfigurationTypeBase(
    FLASH_CONFIGURATION_ID,
    FLASH_CONFIGURATION_NAME,
    FLASH_CONFIGURATION_DESCRIPTION,
    ICON_FLASH
) {
    companion object {
        var factory: FlashConfigurationFactory?=null
    }

    init {
        if (factory == null)
            factory = FlashConfigurationFactory(this)
        factory?.let { addFactory(it)}
    }
}