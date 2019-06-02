package it.achdjian.plugin.esp8266.ui

import com.intellij.openapi.util.io.FileUtil
import it.achdjian.plugin.esp8266.generator.CProjectGenerator

fun getResourceAsString(resourceName: String): String {

    val resource = CProjectGenerator::class.java.classLoader.getResourceAsStream(resourceName)
    return if (resource != null) {
        FileUtil.loadTextAndClose(resource)
    } else {
        ""
    }

}

fun getResourceAsBytes(resourceName: String): ByteArray {
    val resource = CProjectGenerator::class.java.classLoader.getResourceAsStream(resourceName)
    return if (resource != null) {
        FileUtil.loadBytes(resource)
    } else {
        ByteArray(0)
    }

}