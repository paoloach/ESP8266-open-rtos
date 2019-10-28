package esp8266.plugin.achdjian.it.wizard.espressif.configuration.flash

import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.util.NotNullLazyValue
import javax.swing.Icon

object ICON_FLASH : NotNullLazyValue<Icon>() {
    override fun compute() = IconLoader.findIcon("/images/icon.png") ?: AllIcons.Icon
}