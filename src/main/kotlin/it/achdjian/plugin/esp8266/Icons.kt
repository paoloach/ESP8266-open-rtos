package it.achdjian.plugin.esp8266

import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.util.NotNullLazyValue
import javax.swing.Icon

object ICON_FLASH : NotNullLazyValue<Icon>() {
    override fun compute() = IconLoader.findIcon("/images/icon.png") ?: AllIcons.Icon
}

val ICON_SERIAL = IconLoader.findIcon("/images/iconConnect.png")
val ICON_SERIAL_FLASH= IconLoader.findIcon("/images/iconflash.png")