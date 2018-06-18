package plugin.achdjian.it.wizard

import com.intellij.openapi.vfs.VirtualFile
import org.apache.commons.codec.Charsets

fun createFreeRTOSConfig(path: VirtualFile, requestor: Any): VirtualFile {
    val file = path.findOrCreateChildData(requestor, "FreeRTOSConfig.h")
    val content = getResourceAsString("templates/FreeRTOSConfig.h")
    file.setBinaryContent(content.toByteArray(Charsets.UTF_8))
    return file;
}
