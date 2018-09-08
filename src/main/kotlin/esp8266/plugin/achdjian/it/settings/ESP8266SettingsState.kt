package esp8266.plugin.achdjian.it.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage


@State(name = "ESP2866.configuration", storages = [(Storage("ESP2866.xml"))])
data class ESP8266SettingsState(
        var freeRtosPath: String = "",
        var espressifRtosPath: String = "",
        var esptool2: String = "",
        var ccPath: String= ESP2866SDKSettings.GCC,
        var cxxPath: String= ESP2866SDKSettings.CXX) : PersistentStateComponent<ESP8266SettingsState> {

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