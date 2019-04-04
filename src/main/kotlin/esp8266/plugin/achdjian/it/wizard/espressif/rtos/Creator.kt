package esp8266.plugin.achdjian.it.wizard.espressif.rtos

interface Creator {
    fun createTemplatePath():String
    fun createConfigFlags(wizardMenu: MenuWizardData): String
    fun config(wizardMenu: MenuWizardData): String
    fun sourceMain_C(): String
}