package esp8266.plugin.achdjian.it.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage


@State(name = "ESP8266.configuration", storages = [(Storage("ESP8266.xml"))])
data class ESP8266SettingsState(
        var freeRtosPath: String = "",
        var espressifRtosPath: String = "",
        var esptool2: String = "",
        var ccPath: String= ESP8266SDKSettings.GCC,
        var cxxPath: String= ESP8266SDKSettings.CXX) : PersistentStateComponent<ESP8266SettingsState> {

    override fun getState(): ESP8266SettingsState? {
        return this
    }

    override fun loadState(state: ESP8266SettingsState) {
        freeRtosPath = state.freeRtosPath
        espressifRtosPath = state.espressifRtosPath
        esptool2 = state.esptool2
        ccPath = state.ccPath
        cxxPath =state.cxxPath
    }
}