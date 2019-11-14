package it.achdjian.plugin.esp8266.configuration.flash

import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.RunConfigurationProducer
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement
import it.achdjian.plugin.esp8266.configuration.flash.FlashRunConfiguration

class FlashConfigurationProducer : RunConfigurationProducer<FlashRunConfiguration>(true) {
    override fun isConfigurationFromContext(flashRunConf: FlashRunConfiguration, confContext: ConfigurationContext): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setupConfigurationFromContext(
        flashRunConf: FlashRunConfiguration,
        confContext: ConfigurationContext,
        ref: Ref<PsiElement>
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}