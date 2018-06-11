package plugin.achdjian.it

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage


@State(name = "ESP2866.configuration", storages = arrayOf(Storage("ESP2866.xml")))
data class ESP8266SettingsState(var sdkHome: String? = "", var extras: MutableMap<String,Boolean> = HashMap()) : PersistentStateComponent<ESP8266SettingsState> {

    override fun getState(): ESP8266SettingsState? {
        println("Get State: $this")
        return this;
    }

    override fun loadState(state: ESP8266SettingsState) {
        System.out.println("State: $state")
        sdkHome = state.sdkHome
        extras = HashMap(state.extras)
    }
}