package plugin.achdjian.it.wizard

data class WizardData(val extras: MutableMap<String, Boolean>, var flashSize: String, var flashMode: String, var flashSpeed: String, var espPort: String, var floatSupport:Boolean) {
    constructor():this(HashMap(), "512KB","qio", "40", "/dev/ttyUSB0", false)

}