package plugin.achdjian.it.wizard

data class WizardData(val extras: MutableMap<String, Boolean>,
                      var flashSize: FlashSize,
                      var flashMode: String,
                      var flashSpeed: String,
                      var espPort: String,
                      var floatSupport: Boolean,
                      var otaSupport: Boolean) {
    constructor() : this(HashMap(), FlashSize.KB_512, "qio", "40", "/dev/ttyUSB0", false, false)

}