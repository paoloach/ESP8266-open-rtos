package esp8266.plugin.achdjian.it.wizard.espressif.rtos

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.components.*
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry.*

class MenuWizardData() {
    val consoleUartNum: String get() : String = if (consoleUartCustom1.value) "1" else "0"

    val socIRAMSize: String get() = when(socFullICache.value){
        true->"0x8000"
        false->"0xC000"
    }

    val mqttSessionType: Int get()  = when(mqttSession.choiceText){
        cleanMqttSession.text -> 1
        keepMqttSession.text-> 0
        else -> 1
    }
    val mqttVersionType: Int get() = when(mqttVersion.choiceText){
        mqttV3_1.text->3
        mqttV3_1_1.text->4
        else -> 3

    }
    val flashMode: String get() = flashModeConfigEntry.choiceText
    val flashFreq: String get() = flashFreqConfigEntry.choiceText
    val flashFreqHex: Int
        get() = when (flashFreqConfigEntry.choiceText) {
            "ESPTOOLPY_FLASHFREQ_80M" -> 0xf
            "ESPTOOLPY_FLASHFREQ_40M" -> 0x0
            "ESPTOOLPY_FLASHFREQ_26M" -> 0x1
            "ESPTOOLPY_FLASHFREQ_20M" -> 0x2
            else -> 0
        }
    val flashModeHex: Int
        get() = when (flashModeConfigEntry.choiceText) {
            "FLASHMODE_QIO" -> 0
            "FLASHMODE_QOUT" -> 1
            "FLASHMODE_DIO" -> 2
            "FLASHMODE_DOUT" -> 3
            else -> 0
        }
    val taskWdtTimeout: String
        get() = when (choiceWT.choiceText) {
            taskWdTimeout13N.text -> "13"
            taskWdTimeout14N.text -> "14"
            taskWdTimeout15N.text -> "15"
            else -> "15"

        }
    val flashSize: String get() = flashSizeConfigEntry.choiceText
    val espToolPort: String get() = esptoolPortEntry.value
    val espToolBefore: String get() = esptoolBeforeEntry.choiceText
    val espToolAfter: String get() = esptoolAfterEntry.choiceText
    val espToolBaudRate: String get() = espToolBaudRateEntry.choiceText
    val compressUpload: Boolean get() = compressUploadEntry.value
    val bootloaderCheckAppSum: Boolean get() = bootloaderCheckData.value

    val flashSizeHex: String
        get() = when (flashSizeConfigEntry.choiceText) {
            "\"2MB\"" -> "0x200000"
            "\"4MB\"" -> "0x400000"
            "\"8MB\"" -> "0x800000"
            "\"16MB\"" -> "0x1000000"
            else -> "0x200000"
        }
    val spiFlashModeHex: String
        get() = when (flashModeConfigEntry.choiceText) {
            "\"qio\"" -> "0"
            "\"qout\"" -> "1"
            "\"dio\"" -> "2"
            "\"dout\"" -> "3"
            else -> "0"
        }


    private val defaultBaudRate = BoolConfigEntry("115200 baud", "ESPTOOLPY_BAUD_115200B", true)
    private val flashModeQIO = BoolConfigEntry("QIO", "FLASHMODE_QIO", false)
    private val flashModeDIO = BoolConfigEntry("DIO", "FLASHMODE_DIO", true)
    private val defaultFlashFreq = BoolConfigEntry("40 MHz", "ESPTOOLPY_FLASHFREQ_40M", true)
    private val flashSize4Mb = BoolConfigEntry("4 MB", "ESPTOOLPY_FLASHSIZE_4MB", false)
    private val flashSize8Mb = BoolConfigEntry("8 MB", "ESPTOOLPY_FLASHSIZE_8MB", true)
    private val flashModeConfigEntry = ChoiceConfigEntry("Flash SPI mode", "ESPTOOLPY_FLASHMODE", mapOf(
            flashModeQIO to "\"qio\"",
            BoolConfigEntry("QOUT", "FLASHMODE_QOUT", true) to "\"qout\"",
            flashModeDIO to "\"dio\"",
            BoolConfigEntry("DOUT", "FLASHMODE_DOUT", true) to "\"dout\""
    ), flashModeDIO)

    private val flashFreqConfigEntry = ChoiceConfigEntry("Flash SPI speed", "ESPTOOLPY_FLASHFREQ", mapOf(
            BoolConfigEntry("80 MHz", "ESPTOOLPY_FLASHFREQ_80M", true) to "\"80m\"",
            defaultFlashFreq to "\"40m\"",
            BoolConfigEntry("26 MHz", "ESPTOOLPY_FLASHFREQ_26M", true) to "\"26m\"",
            BoolConfigEntry("20 MHz", "ESPTOOLPY_FLASHFREQ_20M", true) to "\"20m\""
    ), defaultFlashFreq)
    private val flashSizeConfigEntry = ChoiceConfigEntry("Flash size", "ESPTOOLPY_FLASHSIZE", mapOf(
            BoolConfigEntry("2 MB", "ESPTOOLPY_FLASHSIZE_2MB", true) to "\"2MB\"",
            flashSize4Mb to "\"4 MB\"",
            flashSize8Mb to "\"8MB\"",
            BoolConfigEntry("16 MB", "ESPTOOLPY_FLASHSIZE_16MB", true) to "\"16MB\""
    ), flashSize8Mb)
    private val defaultResetBeforeFlash = BoolConfigEntry("Reset to bootloader", "ESPTOOLPY_BEFORE_RESET", true)
    private val defaultResetAfterFlash = BoolConfigEntry("Reset after flashing", "ESPTOOLPY_AFTER_RESET", true)
    private val esptoolPortEntry = StringConfigEntry("Default serial port", "ESPTOOLPY_PORT", "/dev/ttyUSB0")

    private val esptoolBeforeEntry = ChoiceConfigEntry("Before flashing", "ESPTOOLPY_BEFORE", mapOf(
            defaultResetBeforeFlash to "\"default_reset\"",
            BoolConfigEntry("No reset", "ESPTOOLPY_BEFORE_NORESET") to "\"no_reset\""
    ), defaultResetBeforeFlash)

    private val esptoolAfterEntry = ChoiceConfigEntry("After flashing", "ESPTOOLPY_AFTER", mapOf(
            defaultResetAfterFlash to "\"hard_reset\"",
            BoolConfigEntry("Stay in bootloader", "ESPTOOLPY_AFTER_NORESET") to "\"no_reset\""
    ), defaultResetAfterFlash)

    private val espToolBaudRateEntry = ChoiceConfigEntry("Default baud rate", "ESPTOOLPY_BAUD", mapOf(
            defaultBaudRate to "\"115200\"",
            BoolConfigEntry("230400 baud", "ESPTOOLPY_BAUD_230400B") to "\"230400\"",
            BoolConfigEntry("921600 baud", "ESPTOOLPY_BAUD_921600B") to "\"921600\"",
            BoolConfigEntry("2M baud", "ESPTOOLPY_BAUD_2MB") to "\"2000000\""
    ), defaultBaudRate)
    private val compressUploadEntry = BoolConfigEntry("Use compressed upload", "ESPTOOLPY_COMPRESSED", true)
    private val entriesESPTool = listOf(
            esptoolPortEntry,
            espToolBaudRateEntry,
            compressUploadEntry,
            flashModeConfigEntry,
            flashFreqConfigEntry,
            flashSizeConfigEntry,
            esptoolBeforeEntry,
            esptoolAfterEntry
    )

    private val entriesWIFIConfig = listOf(
            StringConfigEntry("WiFi SSID", "WIFI_SSID", "myssid"),
            StringConfigEntry("WiFi Password", "WIFI_PASSWORD", "mypassword")
    )

    private val defaultPartitionTable = BoolConfigEntry("Single factory app, no OTA", "PARTITION_TABLE_SINGLE_APP", true)
    private val otaPartitionTable = BoolConfigEntry("Factory app, two OTA definitions", "PARTITION_TABLE_TWO_OTA")
    private val customPartitionTable = BoolConfigEntry("Custom partition table CSV", "PARTITION_TABLE_CUSTOM")
    private val entriesPartitionTable = listOf<ConfigurationEntry>(
            ChoiceConfigEntry("Partition Table", "PARTITION_TABLE_FILENAME", mapOf(
                    defaultPartitionTable to "\"partitions_singleapp.csv\"",
                    otaPartitionTable to "\"partitions_two_ota.csv\"",
                    customPartitionTable to "\"partitions.csv\""
            ), defaultPartitionTable),
            HexConfigEntry("Partition table offset address at flash", "PARTITION_TABLE_OFFSET", 0x8000),
            HexConfigEntry("Factory app partition offset", "PARTITION_TABLE_CUSTOM_APP_BIN_OFFSET", 0x10000, customPartitionTable),
            StringConfigEntry("Custom partition CSV file", "PARTITION_TABLE_CUSTOM_FILENAME", "partitions.csv", customPartitionTable)
    )

    private val cxxException = BoolConfigEntry("Enable C++ exceptions", "CXX_EXCEPTIONS")
    private val defaultOptimizationLevel = BoolConfigEntry("Debug (-Og)", "OPTIMIZATION_LEVEL_DEBUG", true)
    private val defaultAssertionLevel = BoolConfigEntry("Enabled", "OPTIMIZATION_ASSERTIONS_ENABLED", true)
    private val defaultStackCheckMode = BoolConfigEntry("None", "STACK_CHECK_NONE", true)
    private val entriesCompilerOptions = listOf(
            ChoiceConfigEntry("Optimization Level", "OPTIMIZATION_COMPILER", listOf(
                    defaultOptimizationLevel,
                    BoolConfigEntry("Release (-Os)", "OPTIMIZATION_LEVEL_RELEASE")
            ), defaultOptimizationLevel),
            ChoiceConfigEntry("Assertion level", "OPTIMIZATION_ASSERTION_LEVEL", listOf(
                    defaultAssertionLevel,
                    BoolConfigEntry("Silent (saves code size)", "OPTIMIZATION_ASSERTIONS_SILENT"),
                    BoolConfigEntry("Disabled (sets -DNDEBUG)", "OPTIMIZATION_ASSERTIONS_DISABLED")
            ), defaultAssertionLevel),
            cxxException,
            IntConfigEntry("Emergency Pool Size", "CXX_EXCEPTIONS_EMG_POOL_SIZE", 0, 0, 65535, cxxException),
            ChoiceConfigEntry("Stack smashing protection mode", "STACK_CHECK_MODE", listOf(
                    defaultStackCheckMode,
                    BoolConfigEntry("Normal", "STACK_CHECK_NORM"),
                    BoolConfigEntry("Strong", "STACK_CHECK_STRONG"),
                    BoolConfigEntry("Overall", "STACK_CHECK_ALL")
            ), defaultStackCheckMode)
    )

    private val overrideRxBuffer = BoolConfigEntry("Override Shadow RX buffer size", "AWS_IOT_OVERRIDE_THING_SHADOW_RX_BUFFER", false)
    private val entriesEditorThingShadow = listOf<ConfigurationEntry>(
            overrideRxBuffer,
            IntConfigEntry("Maximum RX Buffer (bytes)", "AWS_IOT_SHADOW_MAX_SIZE_OF_RX_BUFFER", 513, 32, 65535, overrideRxBuffer),
            IntConfigEntry("Maximum unique client ID size (bytes)", "AWS_IOT_SHADOW_MAX_SIZE_OF_UNIQUE_CLIENT_ID_BYTES", 80, 4, 1000),
            IntConfigEntry("Maximum simultaneous responses", "AWS_IOT_SHADOW_MAX_SIMULTANEOUS_ACKS", 10, 1, 100),
            IntConfigEntry("Maximum simultaneous Thing Name operations", "AWS_IOT_SHADOW_MAX_SIMULTANEOUS_THINGNAMES", 10, 1, 100),
            IntConfigEntry("Maximum expected JSON tokens", "AWS_IOT_SHADOW_MAX_JSON_TOKEN_EXPECTED", 120, 1, 65535),
            IntConfigEntry("Maximum topic length (not including Thing Name)", "AWS_IOT_SHADOW_MAX_SHADOW_TOPIC_LENGTH_WITHOUT_THINGNAME", 60, 10, 1000),
            IntConfigEntry("Maximum Thing Name length", "AWS_IOT_SHADOW_MAX_SIZE_OF_THING_NAME", 20, 4, 1000)
    )

    private val defaultLogLevel = BoolConfigEntry("Info", "LOG_DEFAULT_LEVEL_INFO", true)
    private val logMenu = listOf(
            ChoiceConfigEntry("Default log verbosity", "LOG_DEFAULT_LEVEL", mapOf(
                    BoolConfigEntry("No output\"", "LOG_DEFAULT_LEVEL_NONE", true) to "0",
                    BoolConfigEntry("Error", "LOG_DEFAULT_LEVEL_ERROR", true) to "1",
                    BoolConfigEntry("Warning", "LOG_DEFAULT_LEVEL_WARN", true) to "2",
                    defaultLogLevel to "3",
                    BoolConfigEntry("Debug", "LOG_DEFAULT_LEVEL_DEBUG", true) to "4",
                    BoolConfigEntry("Verbose", "LOG_DEFAULT_LEVEL_VERBOSE", true) to "5"
            ), defaultLogLevel),
            BoolConfigEntry("Use ANSI terminal colors in log output", "LOG_COLORS", true)
    )




    private val newLibEnable = BoolConfigEntry("Enable newlib", "NEWLIB_ENABLE", true)
    private val defaultNewLibLevel = BoolConfigEntry("nano", "NEWLIB_LIBRARY_LEVEL_NANO", true)
    private val entriesNewLib = listOf(
            newLibEnable,
            ChoiceConfigEntry("newlib level", "NEWLIB_LIBRARY_LEVEL", listOf(
                    defaultNewLibLevel,
                    BoolConfigEntry("normal", "NEWLIB_LIBRARY_LEVEL_NORMAL")
            ), defaultNewLibLevel)
    )


    private val mbedTLS = BoolConfigEntry("mbedTLS", "SSL_USING_MBEDTLS", true)
    private val mbedTLSDebug = BoolConfigEntry("Enable mbedTLS debugging", "MBEDTLS_DEBUG")
    private val enableMbedTime = BoolConfigEntry("Enable mbedtls time", "MBEDTLS_HAVE_TIME", true)
    private val mbedTlsServer = BoolConfigEntry("mbed server", "MBEDTLS_TLS_SERVER", true)
    private val mbedTlsClient = BoolConfigEntry("mbed server", "MBEDTLS_TLS_CLIENT", true)
    private val mbedTlsEnable = BoolConfigEntry("mbed server", "MBEDTLS_TLS_ENABLED", true)
    private val mbedTlsPskMode = BoolConfigEntry("Enable pre-shared-key ciphersuites", "MBEDTLS_PSK_MODES")
    private val mbedTlsEcpC = BoolConfigEntry("Elliptic Curve Ciphers", "MBEDTLS_ECP_C")
    private val mbedTlsSupportEcpC = BoolConfigEntry("Support Elliptic Curve based ciphersuites", "MBEDTLS_KEY_EXCHANGE_ELLIPTIC_CURVE", false, mbedTlsEcpC)
    private val mbedTlsEcdhC = BoolConfigEntry("Elliptic Curve Diffie-Hellman (ECDH)", "MBEDTLS_ECDH_C", true, mbedTlsEcpC)
    private val mbedTlsEcdsaC = BoolConfigEntry("Elliptic Curve DSA", "MBEDTLS_ECDSA_C", false, mbedTlsEcdhC)
    private val mbedTlsV1_2 = BoolConfigEntry("Support TLS 1.2 protocol", "MBEDTLS_SSL_PROTO_TLS1_2", true, mbedTlsEnable)
    private val mbedTlsV1_1 = BoolConfigEntry("Support TLS 1.1 protocol", "MBEDTLS_SSL_PROTO_TLS1_1", true, mbedTlsEnable)
    private val defaultMbedTlsMode = BoolConfigEntry("Server & Client", "MBEDTLS_TLS_SERVER_AND_CLIENT", true, listOf(mbedTlsClient, mbedTlsServer, mbedTlsEnable))
    private val defaultMbedTlsRC4Mode = BoolConfigEntry("Disabled", "MBEDTLS_RC4_DISABLED", true)

    private val entriesTlsKeyExchange = listOf(
            mbedTlsPskMode,
            BoolConfigEntry("Enable PSK based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_PSK", false, mbedTlsPskMode),
            BoolConfigEntry("Enable DHE-PSK based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_DHE_PSK", false, mbedTlsPskMode),
            BoolConfigEntry("Enable ECDHE-PSK based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_ECDHE_PSK", false, mbedTlsPskMode),
            BoolConfigEntry("Enable RSA-PSK based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_RSA_PSK", true, mbedTlsPskMode),
            BoolConfigEntry("Enable RSA-only based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_RSA", true),
            BoolConfigEntry("Enable DHE-RSA based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_DHE_RSA"),
            mbedTlsSupportEcpC,
            BoolConfigEntry("Enable ECDHE-RSA based ciphersuite mode", "MBEDTLS_KEY_EXCHANGE_ECDHE_RSA", false, listOf(mbedTlsSupportEcpC, mbedTlsEcdhC)),
            BoolConfigEntry("Enable ECDHE-ECDSA based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_ECDHE_ECDSA", false, listOf(mbedTlsSupportEcpC, mbedTlsEcdhC, mbedTlsEcdsaC)),
            BoolConfigEntry("Enable ECDH-ECDSA based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_ECDH_ECDSA", false, listOf(mbedTlsSupportEcpC, mbedTlsEcdhC, mbedTlsEcdsaC)),
            BoolConfigEntry("Enable ECDH-RSA based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_ECDH_RSA", false, listOf(mbedTlsSupportEcpC, mbedTlsEcdhC))
    )

    private val entriesMbedTLSSymmetricCipher = listOf(
            BoolConfigEntry("AES block cipher", "MBEDTLS_AES_C", true),
            BoolConfigEntry("Camellia block cipher", "MBEDTLS_CAMELLIA_C"),
            BoolConfigEntry("DES block cipher (legacy, insecure)", "MBEDTLS_DES_C"),
            ChoiceConfigEntry("RC4 Stream Cipher (legacy, insecure)", "MBEDTLS_RC4_MODE", listOf(
                    defaultMbedTlsRC4Mode,
                    BoolConfigEntry("Enabled, not in default ciphersuites", "MBEDTLS_RC4_ENABLED_NO_DEFAULT"),
                    BoolConfigEntry("Enabled", "MBEDTLS_RC4_ENABLED")
            ), defaultMbedTlsRC4Mode),
            BoolConfigEntry("Blowfish block cipher", "MBEDTLS_BLOWFISH_C"),
            BoolConfigEntry("XTEA block cipher", "MBEDTLS_XTEA_C", true),
            BoolConfigEntry("CCM (Counter with CBC-MAC) block cipher modes", "MBEDTLS_CCM_C"),
            BoolConfigEntry("GCM (Galois/Counter) block cipher modes", "MBEDTLS_GCM_C")
    )
    private val entriesCertificates = listOf(
            BoolConfigEntry("Read & Parse PEM formatted certificates", "MBEDTLS_PEM_PARSE_C", true),
            BoolConfigEntry("Write PEM formatted certificates", "MBEDTLS_PEM_WRITE_C", true),
            BoolConfigEntry("X.509 CRL parsing", "MBEDTLS_X509_CRL_PARSE_C", true),
            BoolConfigEntry("X.509 CSR parsing", "MBEDTLS_X509_CSR_PARSE_C", true)

    )
    private val entriesEllipticCurveCipher = listOf(
            mbedTlsEcdhC,
            mbedTlsEcdsaC,
            BoolConfigEntry("Enable SECP192R1 curve", "MBEDTLS_ECP_DP_SECP192R1_ENABLED", true),
            BoolConfigEntry("Enable SECP224R1 curve", "MBEDTLS_ECP_DP_SECP224R1_ENABLED", true),
            BoolConfigEntry("Enable SECP256R1 curve", "MBEDTLS_ECP_DP_SECP256R1_ENABLED", true),
            BoolConfigEntry("Enable SECP384R1 curve", "MBEDTLS_ECP_DP_SECP384R1_ENABLED", true),
            BoolConfigEntry("Enable SECP521R1 curve", "MBEDTLS_ECP_DP_SECP521R1_ENABLED", true),
            BoolConfigEntry("Enable SECP192K1 curve", "MBEDTLS_ECP_DP_SECP192K1_ENABLED", true),
            BoolConfigEntry("Enable SECP224K1 curve", "MBEDTLS_ECP_DP_SECP224K1_ENABLED", true),
            BoolConfigEntry("Enable SECP256K1 curve", "MBEDTLS_ECP_DP_SECP256K1_ENABLED", true),
            BoolConfigEntry("Enable BP256R1 curve", "MBEDTLS_ECP_DP_BP256R1_ENABLED", true),
            BoolConfigEntry("Enable BP384R1 curve", "MBEDTLS_ECP_DP_BP384R1_ENABLED", true),
            BoolConfigEntry("Enable BP512R1 curve", "MBEDTLS_ECP_DP_BP512R1_ENABLED", true),
            BoolConfigEntry("Enable CURVE25519 curve", "MBEDTLS_ECP_DP_CURVE25519_ENABLED", true),
            BoolConfigEntry("NIST 'modulo p' optimisations", "MBEDTLS_ECP_NIST_OPTIM", true)
    )

    private val openSSLDebug = BoolConfigEntry("Enable OpenSSL debugging", "OPENSSL_DEBUG")
    private val defaultOpensslAssertFunction = BoolConfigEntry("Check and exit", "OPENSSL_ASSERT_EXIT")
    private val entriesOpenSSL = listOf(
            openSSLDebug,
            IntConfigEntry("TOpenSSL debugging level", "OPENSSL_DEBUG_LEVEL", 0, 0, 255, openSSLDebug),
            BoolConfigEntry("Enable OpenSSL low-level module debugging", "OPENSSL_LOWLEVEL_DEBUG", false, openSSLDebug, listOf(mbedTLSDebug)),
            ChoiceConfigEntry("Select OpenSSL assert function", "OPENSSL_ASSERT", listOf(
                    BoolConfigEntry("Do nothing", "OPENSSL_ASSERT_DO_NOTHING"),
                    defaultOpensslAssertFunction,
                    BoolConfigEntry("Show debugging message", "OPENSSL_ASSERT_DEBUG", false, openSSLDebug),
                    BoolConfigEntry("Show debugging message and exit", "OPENSSL_ASSERT_DEBUG_EXIT", false, openSSLDebug),
                    BoolConfigEntry("Show debugging message and block", "OPENSSL_ASSERT_DEBUG_BLOCK", false, openSSLDebug)
            ), default = defaultOpensslAssertFunction, dependsOn = mbedTLS)
    )
    val mbedTLS_RSA_bitlen_2048 = BoolConfigEntry("2048", "MBEDTLS_RSA_BITLEN_2048", true)
    val mbedTLS_RSA_bitlen_1024 = BoolConfigEntry("1024(not safe)", "MBEDTLS_RSA_BITLEN_1024", false)

    private val entriesMbedTLS = listOf(
            IntConfigEntry("TLS maximum OUTPUT message content length", "MBEDTLS_SSL_OUT_CONTENT_LEN", 4096, 512, 16384),
            IntConfigEntry("TLS maximum INPUT message content length", "MBEDTLS_SSL_IN_CONTENT_LEN", 4096, 512, 16384),
            ChoiceConfigEntry("RSA minimum bit length", "MBEDTLS_RSA_BITLEN_MIN", listOf(
                    mbedTLS_RSA_bitlen_2048,
                    mbedTLS_RSA_bitlen_1024), mbedTLS_RSA_bitlen_2048),
            mbedTLSDebug,
            IntConfigEntry("Mbedtls debugging level", "MBEDTLS_DEBUG_LEVEL", 4, 0, 4, mbedTLSDebug),
            enableMbedTime,
            BoolConfigEntry("Enable mbedtls time data", "MBEDTLS_HAVE_TIME_DATE", false, enableMbedTime),
            ChoiceConfigEntry("TLS Protocol Role", "MBEDTLS_TLS_MODE", listOf(
                    defaultMbedTlsMode,
                    BoolConfigEntry("server only", "MBEDTLS_TLS_SERVER_ONLY", false, associated = listOf(mbedTlsServer, mbedTlsEnable)),
                    BoolConfigEntry("client only", "MBEDTLS_TLS_CLIENT_ONLY", false, associated = listOf(mbedTlsClient, mbedTlsEnable)),
                    BoolConfigEntry("disable", "MBEDTLS_TLS_DISABLED")
            ), defaultMbedTlsMode),
            SubPanelConfigEntry("TLS Key Exchange Methods", entriesTlsKeyExchange, mbedTlsEnable),
            BoolConfigEntry("Support TLS renegotiation", "MBEDTLS_SSL_RENEGOTIATION", false, dependsOn = mbedTlsEnable),
            BoolConfigEntry("Legacy SSL 3.0 support", "MBEDTLS_SSL_PROTO_SSL3", false, dependsOn = mbedTlsEnable),
            BoolConfigEntry("Support TLS 1.0 protocol", "MBEDTLS_SSL_PROTO_TLS1", true, dependsOn = mbedTlsEnable),
            mbedTlsV1_1,
            mbedTlsV1_2,
            BoolConfigEntry("Support DTLS protocol (all versions)", "MBEDTLS_SSL_PROTO_DTLS", false, listOf(mbedTlsV1_1, mbedTlsV1_2)),
            BoolConfigEntry("Support ALPN (Application Layer Protocol Negotiation)", "MBEDTLS_SSL_ALPN", false, mbedTlsEnable),
            BoolConfigEntry("TLS: Support RFC 5077 SSL session tickets", "MBEDTLS_SSL_SESSION_TICKETS", false, mbedTlsEnable),
            SubPanelConfigEntry("Symmetric Ciphers", entriesMbedTLSSymmetricCipher),
            BoolConfigEntry("Enable RIPEMD-160 hash algorithm", "MBEDTLS_RIPEMD160_C"),
            SubPanelConfigEntry("Certificates", entriesCertificates),
            mbedTlsEcpC,
            SubPanelConfigEntry("Elliptic Curve Ciphers config", entriesEllipticCurveCipher, mbedTlsEcpC),
            SubPanelConfigEntry("OpenSSL", entriesOpenSSL)
    )

    private val entriesSSL = listOf(
            ChoiceConfigEntry("Choose SSL/TLS library", "SSL_LIBRARY_CHOOSE", listOf(
                    mbedTLS,
                    BoolConfigEntry("axTLS", "SSL_USING_AXTLS"),
                    BoolConfigEntry("wolfSSL", "SSL_USING_WOLFSSL")
            ), mbedTLS),
            SubPanelConfigEntry(text = "SSL", entries = entriesMbedTLS, dependsOn = mbedTLS)
    )


    private val sdkTooConfiguration = listOf(
            StringConfigEntry("Python 2 interpreter", "PYTHON", "python"),
            BoolConfigEntry("'make' warns on undefined variables", "MAKE_WARN_UNDEFINED_VARIABLES", true)
    )



    private val enableAwsIOT = BoolConfigEntry("Enable Amazon Web Services IoT Platform", "AWS_IOT_SDK", false)
    private val enablemDns = BoolConfigEntry("Enable mDNS", "ENABLE_MDNS", false)
    private val enablePThread = BoolConfigEntry("Enable pthread", "ENABLE_PTHREAD", false)


    val components: List<ConfigurationEntry> = listOf(
            SubPanelConfigEntry("App update",appUpdateMenu),
            enableAwsIOT,
            SubPanelConfigEntry("Amazon Web Services IoT Platform",awsIOTMenu, enableAwsIOT),
            SubPanelConfigEntry("Wifi config", entriesWIFIConfig),
            SubPanelConfigEntry("Compiler options", entriesCompilerOptions),
            SubPanelConfigEntry("ESP8266 specific ", esp8266ConfigurationMenu),
            SubPanelConfigEntry("Wifi ", wifiMenu),
            BoolConfigEntry("Enable https", "ESP_HTTP_CLIENT_ENABLE_HTTPS", true),
            SubPanelConfigEntry("HTTP Server", httpServerMenu),
            SubPanelConfigEntry("FreeRTOS", freeRTOSConfig),
            BoolConfigEntry("Use mbedTLS SHA256 & SHA512 implementations", "LIBSODIUM_USE_MBEDTLS_SHA" ,   true, dependsOn = Not(espSha)),
            SubPanelConfigEntry("Log output", logMenu),
            SubPanelConfigEntry("LWIP", entriesLWIP),
            enablemDns,
            enablePThread,
            SubPanelConfigEntry("Enable mDNS", mDNSMenu,enablemDns),
            SubPanelConfigEntry("MQTT(Paho)", mqttMenu),
            SubPanelConfigEntry("Newlib", entriesNewLib),
            SubPanelConfigEntry("SSL", entriesSSL),
            SubPanelConfigEntry("Virtual file system", vfsMenu),
            SubPanelConfigEntry("Util", utilMenu),
            SubPanelConfigEntry("wpa supplicant", wpaSupplicantMenu),
            IntConfigEntry("IP Address lost timer interval (seconds)", "IP_LOST_TIMER_INTERVAL", 120, 0, 65535)
    )

    val entriesMenu: List<ConfigurationEntry> = listOf(
            SubPanelConfigEntry("Serial flasher config", entriesESPTool),
            SubPanelConfigEntry("SDK tool configuration", sdkTooConfiguration),
            SubPanelConfigEntry("Boot configuration", bootloaderConfiguration),
            SubPanelConfigEntry("Partition Table", entriesPartitionTable),
            SubPanelConfigEntry("Components", components)

    )
}