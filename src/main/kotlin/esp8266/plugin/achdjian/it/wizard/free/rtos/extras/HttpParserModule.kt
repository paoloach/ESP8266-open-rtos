package esp8266.plugin.achdjian.it.wizard.free.rtos.extras

class HttpParserModule : SimpleModule("http-parser") {
    override fun getCMakeFileLists() = "templates/free/extras/httpParserCMakeLists.txt"
}