package esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations

class HexConfigEntry(text: String, configEntry: String, value: Int, dependsOn: List<BoolConfigEntry>) : StringConfigEntry(text, configEntry, value.toString(16), dependsOn) {
    constructor(text: String, configEntry: String, value: Int) : this(text, configEntry, value, listOf())
    constructor(text: String, configEntry: String, value: Int, dependsOn: BoolConfigEntry) : this(text, configEntry, value, listOf(dependsOn))

    override fun addConfigution(configurations: MutableMap<String, String>) {
        configEntry.forEach { configurations[it] = "0x$value" }
    }

    override fun set(key: String, value: String) {
        if (isConfig(key))
            if (value.startsWith("0x"))
                this.value = value.substring(2)
            else
                this.value = value
    }


}