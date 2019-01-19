package esp8266.plugin.achdjian.it.wizard.free.rtos.extras

class LibEspHttpdModule : SimpleModule("libesphttpd") {
    override fun getCMakeFileLists() = "templates/free/extras/libEspHttpdParserCMakeLists.txt"
}