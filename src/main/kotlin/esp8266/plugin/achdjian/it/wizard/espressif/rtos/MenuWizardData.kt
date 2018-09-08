package esp8266.plugin.achdjian.it.wizard.espressif.rtos

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry.*

class MenuWizardData() {
    val flashMode: String get() = flashModeConfigEntry.choiceText
    val flashFreq: String get() = flashFreqConfigEntry.choiceText
    val flashSize: String get() = flashSizeConfigEntry.choiceText
    val espToolPort: String get() = esptoolPortEntry.value
    val espToolBefore: String get() = esptoolBeforeEntry.choiceText
    val espToolAfter: String get() = esptoolAfterEntry.choiceText
    val espToolBaudRate: String get() = espToolBaudRateEntry.choiceText
    val compressUpload: Boolean get() = compressUploadEntry.value


    private val defaultBaudRate = BoolConfigEntry("115200 baud", "ESPTOOLPY_BAUD_115200B", true)
    private val defaultFlashMode = BoolConfigEntry("QIO", "FLASHMODE_QIO", true)
    private val defaultFlashFreq = BoolConfigEntry("40 MHz", "ESPTOOLPY_FLASHFREQ_40M", true)
    private val defaultFlashSize = BoolConfigEntry("4 MB", "ESPTOOLPY_FLASHSIZE_4MB", true)
    private val flashModeConfigEntry = ChoiceConfigEntry("Flash SPI mode", "ESPTOOLPY_FLASHMODE", mapOf(
            defaultFlashMode to "\"qio\"",
            BoolConfigEntry("QOUT", "FLASHMODE_QOUT", true) to "\"qout\"",
            BoolConfigEntry("DIO", "FLASHMODE_DIO", true) to "\"dio\"",
            BoolConfigEntry("DOUT", "FLASHMODE_DOUT", true) to "\"dout\""
    ), defaultFlashMode)

    private val flashFreqConfigEntry = ChoiceConfigEntry("Flash SPI speed", "ESPTOOLPY_FLASHFREQ", mapOf(
            BoolConfigEntry("80 MHz", "ESPTOOLPY_FLASHFREQ_80M", true) to "\"80m\"",
            defaultFlashFreq to "\"40m\"",
            BoolConfigEntry("26 MHz", "ESPTOOLPY_FLASHFREQ_26M", true) to "\"26m\"",
            BoolConfigEntry("20 MHz", "ESPTOOLPY_FLASHFREQ_20M", true) to "\"20m\""
    ), defaultFlashFreq)
    private val flashSizeConfigEntry = ChoiceConfigEntry("Flash size", "ESPTOOLPY_FLASHSIZE", mapOf(
            BoolConfigEntry("2 MB", "ESPTOOLPY_FLASHSIZE_2MB", true) to "\"2MB\"",
            defaultFlashSize to "\"4MB\"",
            BoolConfigEntry("8 MB", "ESPTOOLPY_FLASHSIZE_8MB", true) to "\"8MB\"",
            BoolConfigEntry("16 MB", "ESPTOOLPY_FLASHSIZE_16MB", true) to "\"16MB\""
    ), defaultFlashSize)
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
    private val entriesPartitionTable = listOf(
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

    private val amazonIOT = BoolConfigEntry("Amazon Web Services IoT Platform", "AWS_IOT_SDK", false)
    private val entriesAmazon = listOf(
            StringConfigEntry("AWS IoT Endpoint Hostname", "AWS_IOT_MQTT_HOST", ""),
            IntConfigEntry("AWS IoT MQTT Port", "AWS_IOT_MQTT_PORT", 8883, 0, 65535),
            IntConfigEntry("MQTT TX Buffer Length", "AWS_IOT_MQTT_TX_BUF_LEN", 512, 32, 65535),
            IntConfigEntry("MQTT RX Buffer Length", "AWS_IOT_MQTT_RX_BUF_LEN", 512, 32, 65535),
            IntConfigEntry("Maximum MQTT Topic Filters", "AWS_IOT_MQTT_NUM_SUBSCRIBE_HANDLERS", 5, 1, 100),
            IntConfigEntry("Auto reconnect initial interval (ms)", "AWS_IOT_MQTT_MIN_RECONNECT_WAIT_INTERVAL", 1000, 10, 3600000),
            IntConfigEntry("Auto reconnect maximum interval (ms)", "AWS_IOT_MQTT_MAX_RECONNECT_WAIT_INTERVAL", 128000, 1, 100),
            SubPanelConfigEntry("Thing Shadow", entriesEditorThingShadow)
    )
    private val defaultLineEnding = BoolConfigEntry("CRLF", "NEWLIB_STDOUT_LINE_ENDING_CRLF", true)
    private val defaultLogLevel = BoolConfigEntry("Info", "LOG_DEFAULT_LEVEL_INFO", true)
    private val entriesComponentConfig = listOf(
            amazonIOT,
            SubPanelConfigEntry("Amazon Web Services IoT config", entriesAmazon, amazonIOT),
            ChoiceConfigEntry("Line ending for UART output", "NEWLIB_STDOUT_LINE_ENDING", listOf(
                    defaultLineEnding,
                    BoolConfigEntry("LF", "NEWLIB_STDOUT_LINE_ENDING_LF"),
                    BoolConfigEntry("CR", "NEWLIB_STDOUT_LINE_ENDING_CR")
            ), defaultLineEnding),
            BoolConfigEntry("Store phy calibration data in NVS", "ESP_PHY_CALIBRATION_AND_DATA_STORAGE", true),
            BoolConfigEntry("Use a partition to store PHY init data", "ESP_PHY_INIT_DATA_IN_PARTITION"),
            BoolConfigEntry("Enable \"reent\" function", "FREERTOS_ENABLE_REENT"),
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

    private val soReuse = BoolConfigEntry("Enable SO_REUSEADDR option", "LWIP_SO_REUSE", true)
    private val icmp = BoolConfigEntry("ICMP", "LWIP_ICMP", true)
    private val lwipAutoip = BoolConfigEntry("Enable IPV4 Link-Local Addressing (AUTOIP)", "LWIP_AUTOIP", false)
    private val perInterfaceLoopback = BoolConfigEntry("nable per-interface loopback", "LWIP_NETIF_LOOPBACK", false)
    private val enableIPV6 = BoolConfigEntry("Enable IPv6", "LWIP_IPV6", true)
    private val enableDebug = BoolConfigEntry("Enable lwip Debug", "LWIP_DEBUG", false)

    private val entriesARP = listOf(
            IntConfigEntry("Number of active MAC-IP address pairs cached", "LWIP_ARP_TABLE_SIZE", 10, 1, 16),
            IntConfigEntry("The time an ARP entry stays valid after its last update", "LWIP_ARP_MAXAGE", 300, 100, 65535)
    )
    private val entriesSocket = listOf<ConfigurationEntry>(
            BoolConfigEntry("LWIP socket supports multithread", "LWIP_SOCKET_MULTITHREAD", true),
            IntConfigEntry("Max number of open sockets", "LWIP_MAX_SOCKETS", 10, 1, 16),
            soReuse,
            BoolConfigEntry("SO_REUSEADDR copies broadcast/multicast to all matches", "LWIP_SO_REUSE_RXTOALL", true, soReuse),
            BoolConfigEntry("Enable SO_RCVBUF option", "LWIP_SO_RCVBUF", false),
            IntConfigEntry("The default value for recv_bufsize", "LWIP_RECV_BUFSIZE_DEFAULT", 11680, 2920, 11680),
            IntConfigEntry("TCP socket/netconn close waits time to send the FIN", "LWIP_TCP_CLOSE_TIMEOUT_MS_DEFAULT", 10000, 10000, 20000)
    )

    private val entryICMP = listOf(
            BoolConfigEntry("Respond to multicast pings", "LWIP_MULTICAST_PING", false),
            BoolConfigEntry("Respond to broadcast pings", "LWIP_BROADCAST_PING", false)
    )

    private val entriesDHCP = listOf<ConfigurationEntry>(
            BoolConfigEntry("DHCP: Perform ARP check on any offered address", "LWIP_DHCP_DOES_ARP_CHECK", true),
            IntConfigEntry("Maximum number of NTP servers", "LWIP_DHCP_MAX_NTP_SERVERS", 1, 1, 8),
            IntConfigEntry("Multiplier for lease time, in seconds", "LWIP_DHCPS_LEASE_UNIT", 60, 1, 3600),
            IntConfigEntry("Maximum number of stations", "LWIP_DHCPS_MAX_STATION_NUM", 8, 1, 8)
    )

    private val entriesLwipAutoip = listOf(
            IntConfigEntry("DHCP Probes before self-assigning IPv4 LL address", "LWIP_DHCP_AUTOIP_COOP_TRIES", 2, 1, 100)
    )

    private val entriesPerInterfaceLoopback = listOf(
            IntConfigEntry("Max queued loopback packets per interface", "LWIP_LOOPBACK_MAX_PBUFS", 0, 0, 16)
    )

    private val defaultTCPOversize = BoolConfigEntry("MSS", "TCP_OVERSIZE_MSS", true)
    private val entriesTCP = listOf(
            IntConfigEntry("Maximum active TCP Connections", "LWIP_MAX_ACTIVE_TCP", 5, 1, 32),
            IntConfigEntry("Maximum listening TCP Connections", "LWIP_MAX_LISTENING_TCP", 8, 1, 16),
            IntConfigEntry("Maximum number of retransmissions of data segments", "TCP_MAXRTX", 12, 3, 12),
            IntConfigEntry("Maximum number of retransmissions of SYN segments", "TCP_SYNMAXRTX", 6, 3, 12),
            IntConfigEntry("Maximum Segment Size (MSS)", "TCP_MSS", 1460, 536, 1460),
            IntConfigEntry("Default send buffer size", "TCP_SND_BUF_DEFAULT", 2920, 2920, 11680),
            IntConfigEntry("Default receive window size", "TCP_WND_DEFAULT", 5840, 2920, 11680),
            IntConfigEntry("Default TCP receive mail box size", "TCP_RECVMBOX_SIZE", 6, 6, 32),
            BoolConfigEntry("Queue incoming out-of-order segments", "TCP_QUEUE_OOSEQ"),
            ChoiceConfigEntry("Pre-allocate transmit PBUF size", "TCP_OVERSIZE", listOf(
                    defaultTCPOversize,
                    BoolConfigEntry("25% MSS", "TCP_OVERSIZE_QUARTER_MSS", true),
                    BoolConfigEntry("Disabled", "TCP_OVERSIZE_DISABLE", true)),
                    defaultTCPOversize),
            BoolConfigEntry("Support the TCP timestamp option", "LWIP_TCP_TIMESTAMPS", false)
    )

    private val entriesUDP = listOf(
            IntConfigEntry("Maximum active UDP control blocks", "LWIP_MAX_UDP_PCBS", 4, 1, 32),
            IntConfigEntry("Default UDP receive mail box size", "UDP_RECVMBOX_SIZE", 6, 6, 64)
    )

    private val entriesIPV6 = listOf<ConfigurationEntry>(
            IntConfigEntry("Number of IPv6 addresses per netif", "LWIP_IPV6_NUM_ADDRESSES", 3, 3, 5),
            BoolConfigEntry("Forward IPv6 packets across netifs", "LWIP_IPV6_FORWARD", false),
            BoolConfigEntry("Fragment outgoing IPv6 packets that are too big", "LWIP_IPV6_FRAG", false)
    )

    private val entriesDebug = listOf<ConfigurationEntry>(
            BoolConfigEntry("Enable debugging in etharp.c", "LWIP_ETHARP_DEBUG", false),
            BoolConfigEntry("Enable debugging in netif.c", "LWIP_NETIF_DEBUG", false),
            BoolConfigEntry("Enable debugging in pbuf.c", "LWIP_PBUF_DEBUG", false),
            BoolConfigEntry("Enable debugging in api_lib.c", "LWIP_API_LIB_DEBUG", false),
            BoolConfigEntry("Enable debugging in api_msg.c", "LWIP_API_MSG_DEBUG", false),
            BoolConfigEntry("Enable debugging in sockets.c", "LWIP_SOCKETS_DEBUG", false),
            BoolConfigEntry("Enable debugging in icmp.c", "LWIP_ICMP_DEBUG", false),
            BoolConfigEntry("Enable debugging in igmp.c", "LWIP_IGMP_DEBUG", false),
            BoolConfigEntry("Enable debugging in inet.c", "LWIP_INET_DEBUG", false),
            BoolConfigEntry("Enable debugging in ethernetif.c", "LWIP_ETHERNETIF_DEBUG", false),
            BoolConfigEntry("Enable debugging for IP", "LWIP_IP_DEBUG", false),
            BoolConfigEntry("Enable debugging in ip_frag.c for both frag & reass", "LWIP_IP_REASS_DEBUG", false),
            BoolConfigEntry("Enable debugging in raw.c", "LWIP_RAW_DEBUG", false),
            BoolConfigEntry("Enable debugging in mem.c", "LWIP_MEM_DEBUG", false),
            BoolConfigEntry("Enable debugging in memp.c", "LWIP_MEMP_DEBUG", false),
            BoolConfigEntry("Enable debugging in sys.c", "LWIP_SYS_DEBUG", false),
            BoolConfigEntry("Enable debugging in timers.c", "LWIP_TIMERS_DEBUG", false),
            BoolConfigEntry("Enable debugging for TCP", "LWIP_TCP_DEBUG", false),
            BoolConfigEntry("Enable debugging in tcp_in.c for incoming debug", "LWIP_TCP_INPUT_DEBUG", false),
            BoolConfigEntry("Enable debugging in tcp_in.c for fast retransmit", "LWIP_TCP_FR_DEBUG", false),
            BoolConfigEntry("Enable debugging in TCP for retransmit", "LWIP_TCP_RTO_DEBUG", false),
            BoolConfigEntry("Enable debugging for TCP congestion window", "LWIP_TCP_CWND_DEBUG", false),
            BoolConfigEntry("Enable debugging in tcp_in.c for window updating", "LWIP_TCP_WND_DEBUG", false),
            BoolConfigEntry("Enable debugging in tcp_out.c output functions", "LWIP_TCP_OUTPUT_DEBUG", false),
            BoolConfigEntry("Enable debugging for TCP with the RST message", "LWIP_TCP_RST_DEBUG", false),
            BoolConfigEntry("Enable debugging for TCP queue lengths", "LWIP_TCP_QLEN_DEBUG", false),
            BoolConfigEntry("Enable debugging in UDP", "LWIP_UDP_DEBUG", false),
            BoolConfigEntry("Enable debugging in tcpip.c", "LWIP_TCPIP_DEBUG", false),
            BoolConfigEntry("Enable debugging in slipif.c", "LWIP_SLIP_DEBUG", false),
            BoolConfigEntry("Enable debugging in dhcp.c", "LWIP_DHCP_DEBUG", false),
            BoolConfigEntry("Enable debugging in dhcpserver.c", "LWIP_DHCP_SERVER_DEBUG", false),
            BoolConfigEntry("Enable debugging in autoip.c", "LWIP_AUTOIP_DEBUG", false),
            BoolConfigEntry("Enable debugging for DNS", "LWIP_DNS_DEBUG", false),
            BoolConfigEntry("Enable debugging for IPv6", "LWIP_IP6_DEBUG", false),
            BoolConfigEntry("Enable debugging for SNTP", "LWIP_SNTP_DEBUG", false),
            BoolConfigEntry("Enable debugging for LWIP thread safety", "LWIP_THREAD_SAFE_DEBUG", false)
    )

    private val entriesLWIP = listOf(
            SubPanelConfigEntry("ARP", entriesARP),
            SubPanelConfigEntry("SOCKET", entriesSocket),
            BoolConfigEntry("Enable fragment outgoing IP packets", "LWIP_IP_FRAG", false),
            BoolConfigEntry("Enable reassembly incoming fragmented IP packets", "LWIP_IP_REASSEMBLY", false),
            IntConfigEntry("Total maximum amount of pbufs waiting to be reassembled", "LWIP_IP_REASS_MAX_PBUFS", 10, 1, 16),
            BoolConfigEntry("Enable broadcast filter per pcb on udp and raw send operation", "LWIP_IP_SOF_BROADCAST", false),
            BoolConfigEntry("Enable the broadcast filter on recv operations", "LWIP_IP_SOF_BROADCAST_RECV", false),
            icmp,
            SubPanelConfigEntry("ICMP", entryICMP, icmp),
            BoolConfigEntry("Enable application layer to hook into the IP layer itself", "LWIP_RAW", false),
            SubPanelConfigEntry("DHCP", entriesDHCP),
            lwipAutoip,
            SubPanelConfigEntry("AUTOIP", entriesLwipAutoip, lwipAutoip),
            BoolConfigEntry("Enable IGMP module", "LWIP_IGMP", true),
            IntConfigEntry("The maximum of DNS servers", "DNS_MAX_SERVERS", 2, 1, 5),
            perInterfaceLoopback,
            SubPanelConfigEntry("per-interface loopback", entriesPerInterfaceLoopback, perInterfaceLoopback),
            SubPanelConfigEntry("TCP", entriesTCP),
            SubPanelConfigEntry("UDP", entriesUDP),
            IntConfigEntry("TCP/IP Task Stack Size", "TCPIP_TASK_STACK_SIZE", 2048, 2048, 8192),
            IntConfigEntry("Maximum LWIP RAW PCBs", "LWIP_MAX_RAW_PCBS", 4, 1, 32),
            enableIPV6,
            SubPanelConfigEntry("IPV6", entriesIPV6, enableIPV6),
            BoolConfigEntry("Enable statistics collection in lwip_stats", "LWIP_STATS", false),
            enableDebug,
            SubPanelConfigEntry("Debug", entriesDebug, enableDebug)
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

    private val entriesMbedTLS = listOf(
            IntConfigEntry("TLS maximum message content length", "MBEDTLS_SSL_MAX_CONTENT_LEN", 4096, 512, 16384),
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

    private val defaultBootloaderLogLevel = BoolConfigEntry("Info", "LOG_BOOTLOADER_LEVEL_INFO", true)

    private val bootloaderLogLevel = ChoiceConfigEntry("Bootloader log verbosity", "LOG_BOOTLOADER_LEVEL", mapOf(
            BoolConfigEntry("No output", "LOG_BOOTLOADER_LEVEL_NONE") to "0",
            BoolConfigEntry("Error", "LOG_BOOTLOADER_LEVEL_ERROR") to "1",
            BoolConfigEntry("Warning", "LOG_BOOTLOADER_LEVEL_WARN") to "2",
            defaultBootloaderLogLevel to "3",
            BoolConfigEntry("Debug", "LOG_BOOTLOADER_LEVEL_DEBUG") to "4",
            BoolConfigEntry("Verbose", "LOG_BOOTLOADER_LEVEL_VERBOSE") to "5"
    ), defaultBootloaderLogLevel)

    private val sdkTooConfiguration = listOf(
            StringConfigEntry("Python 2 interpreter", "PYTHON", "python"),
            BoolConfigEntry("'make' warns on undefined variables", "MAKE_WARN_UNDEFINED_VARIABLES", true)
    )

    val entriesMenu: List<ConfigurationEntry> = listOf(
            SubPanelConfigEntry("Serial flasher config", entriesESPTool),
            bootloaderLogLevel,
            SubPanelConfigEntry("SDK tool configuration", sdkTooConfiguration),
            SubPanelConfigEntry("Wifi config", entriesWIFIConfig),
            SubPanelConfigEntry("Partition Table", entriesPartitionTable),
            SubPanelConfigEntry("Compiler options", entriesCompilerOptions),
            SubPanelConfigEntry("Component config", entriesComponentConfig),
            SubPanelConfigEntry("LWIP", entriesLWIP),
            SubPanelConfigEntry("Newlib", entriesNewLib),
            SubPanelConfigEntry("SSL", entriesSSL),
            IntConfigEntry("IP Address lost timer interval (seconds)", "IP_LOST_TIMER_INTERVAL", 120, 0, 65535)
    )
}