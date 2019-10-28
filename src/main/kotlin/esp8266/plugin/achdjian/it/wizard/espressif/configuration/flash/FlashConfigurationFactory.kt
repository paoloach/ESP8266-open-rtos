package esp8266.plugin.achdjian.it.wizard.espressif.configuration.flash

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.project.Project
import esp8266.plugin.achdjian.it.wizard.espressif.configuration.flash.FlashConfigurationType
import esp8266.plugin.achdjian.it.wizard.espressif.configuration.flash.FlashRunConfiguration

class FlashConfigurationFactory(configurationType: FlashConfigurationType): ConfigurationFactory(configurationType)  {
    override fun createTemplateConfiguration(project: Project): RunConfiguration =
        FlashRunConfiguration(project, this, "ESP32 flash rom")
    override fun getName() = "ESP32 flash rom"
}

