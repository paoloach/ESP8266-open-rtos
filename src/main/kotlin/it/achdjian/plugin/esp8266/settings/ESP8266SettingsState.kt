package it.achdjian.plugin.esp8266.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import java.io.File


@State(name = "ESP8266.configuration", storages = [(Storage("ESP8266.xml"))])
data class ESP8266SettingsState(
    var freeRtosPath: String = "",
    var espressifRtosPath: String = "",
    var esptool2: String = "",
    var ccPath: String = GCC,
    var cxxPath: String = CXX,
    var serialPort: String = SERIAL_PORT,
    var baud: Int = BAUD
) : PersistentStateComponent<ESP8266SettingsState> {

    val crosscompilerPath: String
        get() {
            return File(ccPath).parentFile.toString()
        }

    override fun getState(): ESP8266SettingsState? {
        return this
    }

    override fun loadState(state: ESP8266SettingsState) {
        freeRtosPath = state.freeRtosPath
        espressifRtosPath = state.espressifRtosPath
        esptool2 = state.esptool2
        ccPath = state.ccPath
        cxxPath = state.cxxPath
        serialPort = state.serialPort
        baud = state.baud
    }
}

fun getESP8266Setting() =
    ApplicationManager.getApplication().getComponent(ESP8266SettingsState::class.java, DEFAULT) as ESP8266SettingsState