package esp8266.plugin.achdjian.it.wizard.espressif.rtos

class Creator2_0 : Creator {
    override fun sourceMain_C() = "templates/espressif/main.c"

    override fun config(wizardMenu: MenuWizardData): String  = ""

    override fun createConfigFlags(wizardMenu: MenuWizardData): String  = ""

    override fun createTemplatePath(): String = "templates/espressif"
}