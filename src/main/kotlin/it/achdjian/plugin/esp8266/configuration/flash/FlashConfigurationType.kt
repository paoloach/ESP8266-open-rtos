package it.achdjian.plugin.esp8266.configuration.flash

import com.intellij.execution.configurations.ConfigurationTypeBase
import it.achdjian.plugin.esp8266.ICON_FLASH

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
            factory =
                FlashConfigurationFactory(this)
        factory?.let { addFactory(it)}
    }
}