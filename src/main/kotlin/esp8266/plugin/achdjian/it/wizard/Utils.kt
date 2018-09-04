package esp8266.plugin.achdjian.it.wizard

import com.intellij.openapi.util.io.FileUtil

fun getResourceAsString(resourceName: String): String {
    val resource = WizardData::class.java.classLoader.getResourceAsStream(resourceName)
    return if (resource != null) {
        FileUtil.loadTextAndClose(resource)
    } else {
        ""
    }

}

fun getResourceAsBytes(resourceName: String): ByteArray {
    val resource = WizardData::class.java.classLoader.getResourceAsStream(resourceName)
    return if (resource != null) {
        FileUtil.loadBytes(resource)
    } else {
        ByteArray(0)
    }

}