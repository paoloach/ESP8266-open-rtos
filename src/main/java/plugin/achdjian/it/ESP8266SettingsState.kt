package plugin.achdjian.it

import com.intellij.openapi.components.PersistentStateComponent

class ESP8266SettingsState: PersistentStateComponent<ESP8266SettingsState> {
    private var sdkHome:String? = null

    override fun getState(): ESP8266SettingsState? {
        return this
    }

    override fun loadState(state: ESP8266SettingsState) {
        sdkHome = state.sdkHome
    }
}