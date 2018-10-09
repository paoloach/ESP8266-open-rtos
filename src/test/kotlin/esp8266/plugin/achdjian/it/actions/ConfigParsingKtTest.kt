package esp8266.plugin.achdjian.it.actions

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.Constants
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configParsing
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsMapContaining
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ConfigParsingKtTest {
    @Mock
    lateinit var project: Project
    @Mock
    lateinit var baseDir: VirtualFile
    @Mock
    lateinit var includeDir: VirtualFile
    @Mock
    lateinit var configFile: VirtualFile

    @get:Rule
    var folder = TemporaryFolder()

    @Test
    fun configParser(){
        folder.newFolder(Constants.CONFIG_DIR)
        val tmpFile =  folder.newFile()
        tmpFile.appendText("#define CONFIG_TOOLPREFIX \"xtensa-lx106-elf-\"\n")
        tmpFile.appendText("#define CONFIG_TARGET_PLATFORM_ESP8266 1\n")
        tmpFile.appendText("#define CONFIG_APP_OFFSET  0x1000\n")
        tmpFile.appendText("#define CONFIG_MBEDTLS_RC4_DISABLED 1\n")
        tmpFile.appendText("#define CONFIG_ESPTOOLPY_COMPRESSED 1\n")
        `when`(project.baseDir).thenReturn(baseDir)
        Mockito.`when`(baseDir.findChild(Constants.CONFIG_DIR)).thenReturn(includeDir)
        Mockito.`when`(includeDir.findChild(Constants.CONFIG_FILE_NAME)).thenReturn(configFile)
        Mockito.`when`(configFile.exists()).thenReturn(true)
        Mockito.`when`(includeDir.exists()).thenReturn(true)
        Mockito.`when`(configFile.path).thenReturn(tmpFile.absolutePath)

        val configParsing = configParsing(project)

        assertThat(configParsing, IsMapContaining.hasEntry("TOOLPREFIX", "\"xtensa-lx106-elf-\""))
        assertThat(configParsing, IsMapContaining.hasEntry("TARGET_PLATFORM_ESP8266", "1"))
        assertThat(configParsing, IsMapContaining.hasEntry("APP_OFFSET", "0x1000"))
        assertThat(configParsing, IsMapContaining.hasEntry("MBEDTLS_RC4_DISABLED", "1"))
        assertThat(configParsing, IsMapContaining.hasEntry("ESPTOOLPY_COMPRESSED", "1"))


    }
}
