package esp8266.plugin.achdjian.it.wizard.espressif.rtos

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry.ChoiceConfigEntry

class MenuWizardData() {
    private val defaultBaudRate = ConfigurationEntry.BoolConfigEntry("115200 baud", "ESPTOOLPY_BAUD_115200B", true)
    private val defaultFlashMode = ConfigurationEntry.BoolConfigEntry("QIO", "FLASHMODE_QIO", true)
    private val defaultFlashSize = ConfigurationEntry.BoolConfigEntry("4 MB", "ESPTOOLPY_FLASHSIZE_4MB", true)
    private val entriesESPTool = listOf(
            ConfigurationEntry.StringConfigEntry("Default serial port", "ESPTOOLPY_PORT", "/dev/ttyUSB0"),
            ChoiceConfigEntry("Default baud rate", listOf(
                    defaultBaudRate,
                    ConfigurationEntry.BoolConfigEntry("230400 baud", "ESPTOOLPY_BAUD_230400B"),
                    ConfigurationEntry.BoolConfigEntry("921600 baud", "ESPTOOLPY_BAUD_921600B"),
                    ConfigurationEntry.BoolConfigEntry("2M baud", "ESPTOOLPY_BAUD_2MB")
            ), defaultBaudRate),
            ConfigurationEntry.BoolConfigEntry("Use compressed upload", "ESPTOOLPY_COMPRESSED", true),
            ChoiceConfigEntry("Flash SPI mode", listOf(
                    defaultFlashMode,
                    ConfigurationEntry.BoolConfigEntry("QOUT", "FLASHMODE_QOUT", true),
                    ConfigurationEntry.BoolConfigEntry("DIO", "FLASHMODE_DIO", true),
                    ConfigurationEntry.BoolConfigEntry("DOUT", "FLASHMODE_DOUT", true)
            ), defaultFlashMode),
            ChoiceConfigEntry("Flash size", listOf(
                    ConfigurationEntry.BoolConfigEntry("2 MB", "ESPTOOLPY_FLASHSIZE_2MB", true),
                    defaultFlashSize,
                    ConfigurationEntry.BoolConfigEntry("8 MB", "ESPTOOLPY_FLASHSIZE_8MB", true),
                    ConfigurationEntry.BoolConfigEntry("16 MB", "ESPTOOLPY_FLASHSIZE_16MB", true)
            ), defaultFlashSize),
            ConfigurationEntry.BoolConfigEntry("Reset to bootloader", "ESPTOOLPY_BEFORE_RESET", true),
            ConfigurationEntry.BoolConfigEntry("Reset after flashing", "ESPTOOLPY_BEFORE", true)
    )

    private val entriesWIFIConfig = listOf(
            ConfigurationEntry.StringConfigEntry("WiFi SSID", "WIFI_SSID", "myssid"),
            ConfigurationEntry.StringConfigEntry("WiFi Password", "WIFI_PASSWORD", "mypassword")
    )

    private val defaultPartitionTable = ConfigurationEntry.BoolConfigEntry("Single factory app, no OTA", "PARTITION_TABLE_SINGLE_APP", true)
    private val entriesPartitionTable = listOf(
            ChoiceConfigEntry("Partition Table", listOf(
                    defaultPartitionTable,
                    ConfigurationEntry.BoolConfigEntry("Factory app, two OTA definitions", "PARTITION_TABLE_TWO_OTA"),
                    ConfigurationEntry.BoolConfigEntry("Custom partition table CSV", "PARTITION_TABLE_CUSTOM")
            ), defaultPartitionTable),
            ConfigurationEntry.StringConfigEntry("Partition table offset address at flash", "PARTITION_TABLE_OFFSET", "0x8000")
    )

    private val cxxException = ConfigurationEntry.BoolConfigEntry("Enable C++ exceptions", "CXX_EXCEPTIONS")
    private val defaultOptimizationLevel = ConfigurationEntry.BoolConfigEntry("Debug (-Og)", "OPTIMIZATION_LEVEL_DEBUG", true)
    private val defaultAssertionLevel = ConfigurationEntry.BoolConfigEntry("Enabled", "OPTIMIZATION_ASSERTIONS_ENABLED", true)
    private val defaultStackCheckMode = ConfigurationEntry.BoolConfigEntry("None", "STACK_CHECK_NONE", true)
    private val entriesCompilerOptions = listOf(
            ChoiceConfigEntry("Optimization Level", listOf(
                    defaultOptimizationLevel,
                    ConfigurationEntry.BoolConfigEntry("Release (-Os)", "OPTIMIZATION_LEVEL_RELEASE")
            ), defaultOptimizationLevel),
            ChoiceConfigEntry("Assertion level", listOf(
                    defaultAssertionLevel,
                    ConfigurationEntry.BoolConfigEntry("Silent (saves code size)", "OPTIMIZATION_ASSERTIONS_SILENT"),
                    ConfigurationEntry.BoolConfigEntry("Disabled (sets -DNDEBUG)", "OPTIMIZATION_ASSERTIONS_DISABLED")
            ), defaultAssertionLevel),
            cxxException,
            ConfigurationEntry.IntConfigEntry("Emergency Pool Size", "CXX_EXCEPTIONS_EMG_POOL_SIZE", 0, 0, 65535, cxxException),
            ChoiceConfigEntry("Stack smashing protection mode", listOf(
                    defaultStackCheckMode,
                    ConfigurationEntry.BoolConfigEntry("Normal", "STACK_CHECK_NORM"),
                    ConfigurationEntry.BoolConfigEntry("Strong", "STACK_CHECK_STRONG"),
                    ConfigurationEntry.BoolConfigEntry("Overall", "STACK_CHECK_ALL")
            ), defaultStackCheckMode)
    )

    private val overrideRxBuffer = ConfigurationEntry.BoolConfigEntry("Override Shadow RX buffer size", "AWS_IOT_OVERRIDE_THING_SHADOW_RX_BUFFER", false)
    private val entriesEditorThingShadow = listOf<ConfigurationEntry>(
            overrideRxBuffer,
            ConfigurationEntry.IntConfigEntry("Maximum RX Buffer (bytes)", "AWS_IOT_SHADOW_MAX_SIZE_OF_RX_BUFFER", 513, 32, 65535, overrideRxBuffer),
            ConfigurationEntry.IntConfigEntry("Maximum unique client ID size (bytes)", "AWS_IOT_SHADOW_MAX_SIZE_OF_UNIQUE_CLIENT_ID_BYTES", 80, 4, 1000),
            ConfigurationEntry.IntConfigEntry("Maximum simultaneous responses", "AWS_IOT_SHADOW_MAX_SIMULTANEOUS_ACKS", 10, 1, 100),
            ConfigurationEntry.IntConfigEntry("Maximum simultaneous Thing Name operations", "AWS_IOT_SHADOW_MAX_SIMULTANEOUS_THINGNAMES", 10, 1, 100),
            ConfigurationEntry.IntConfigEntry("Maximum expected JSON tokens", "AWS_IOT_SHADOW_MAX_JSON_TOKEN_EXPECTED", 120, 1, 65535),
            ConfigurationEntry.IntConfigEntry("Maximum topic length (not including Thing Name)", "AWS_IOT_SHADOW_MAX_SHADOW_TOPIC_LENGTH_WITHOUT_THINGNAME", 60, 10, 1000),
            ConfigurationEntry.IntConfigEntry("Maximum Thing Name length", "AWS_IOT_SHADOW_MAX_SIZE_OF_THING_NAME", 20, 4, 1000)
    )

    private val amazonIOT = ConfigurationEntry.BoolConfigEntry("Amazon Web Services IoT Platform", "AWS_IOT_SDK", false)
    private val entriesAmazon = listOf(
            ConfigurationEntry.StringConfigEntry("AWS IoT Endpoint Hostname", "AWS_IOT_MQTT_HOST", ""),
            ConfigurationEntry.IntConfigEntry("AWS IoT MQTT Port", "AWS_IOT_MQTT_PORT", 8883, 0, 65535),
            ConfigurationEntry.IntConfigEntry("MQTT TX Buffer Length", "AWS_IOT_MQTT_TX_BUF_LEN", 512, 32, 65535),
            ConfigurationEntry.IntConfigEntry("MQTT RX Buffer Length", "AWS_IOT_MQTT_RX_BUF_LEN", 512, 32, 65535),
            ConfigurationEntry.IntConfigEntry("Maximum MQTT Topic Filters", "AWS_IOT_MQTT_NUM_SUBSCRIBE_HANDLERS", 5, 1, 100),
            ConfigurationEntry.IntConfigEntry("Auto reconnect initial interval (ms)", "AWS_IOT_MQTT_MIN_RECONNECT_WAIT_INTERVAL", 1000, 10, 3600000),
            ConfigurationEntry.IntConfigEntry("Auto reconnect maximum interval (ms)", "AWS_IOT_MQTT_MAX_RECONNECT_WAIT_INTERVAL", 128000, 1, 100),
            ConfigurationEntry.SubPanelConfigEntry("Thing Shadow", entriesEditorThingShadow)
    )
    private val defaultLineEnding = ConfigurationEntry.BoolConfigEntry("CRLF", "NEWLIB_STDOUT_LINE_ENDING_CRLF", true)
    private val defaultLogLevel = ConfigurationEntry.BoolConfigEntry("Info", "LOG_DEFAULT_LEVEL 3", true)
    private val entriesComponentConfig = listOf(
            amazonIOT,
            ConfigurationEntry.SubPanelConfigEntry("Amazon Web Services IoT config", entriesAmazon, amazonIOT),
            ChoiceConfigEntry("Line ending for UART output", listOf(
                    defaultLineEnding,
                    ConfigurationEntry.BoolConfigEntry("LF", "NEWLIB_STDOUT_LINE_ENDING_LF"),
                    ConfigurationEntry.BoolConfigEntry("CR", "NEWLIB_STDOUT_LINE_ENDING_CR")
            ), defaultLineEnding),
            ConfigurationEntry.BoolConfigEntry("Store phy calibration data in NVS", "ESP_PHY_CALIBRATION_AND_DATA_STORAGE", true),
            ConfigurationEntry.BoolConfigEntry("Use a partition to store PHY init data", "ESP_PHY_INIT_DATA_IN_PARTITION"),
            ConfigurationEntry.BoolConfigEntry("Enable \"reent\" function", "FREERTOS_ENABLE_REENT"),
            ChoiceConfigEntry("Default log verbosity", listOf(
                    ConfigurationEntry.BoolConfigEntry("No output\"", "LOG_DEFAULT_LEVEL 0", true),
                    ConfigurationEntry.BoolConfigEntry("Error", "LOG_DEFAULT_LEVEL 1", true),
                    ConfigurationEntry.BoolConfigEntry("Warning", "LOG_DEFAULT_LEVEL 2", true),
                    defaultLogLevel,
                    ConfigurationEntry.BoolConfigEntry("Debug", "LOG_DEFAULT_LEVEL 4", true),
                    ConfigurationEntry.BoolConfigEntry("Verbose", "LOG_DEFAULT_LEVEL 5", true)
            ), defaultLogLevel),
            ConfigurationEntry.BoolConfigEntry("Use ANSI terminal colors in log output", "LOG_COLORS", true)

    )

    private val soReuse = ConfigurationEntry.BoolConfigEntry("Enable SO_REUSEADDR option", "LWIP_SO_REUSE", true)
    private val icmp = ConfigurationEntry.BoolConfigEntry("ICMP", "LWIP_ICMP", true)
    private val lwipAutoip = ConfigurationEntry.BoolConfigEntry("Enable IPV4 Link-Local Addressing (AUTOIP)", "LWIP_AUTOIP", false)
    private val perInterfaceLoopback = ConfigurationEntry.BoolConfigEntry("nable per-interface loopback", "LWIP_NETIF_LOOPBACK", false)
    private val enableIPV6 = ConfigurationEntry.BoolConfigEntry("Enable IPv6", "LWIP_IPV6", true)
    private val enableDebug = ConfigurationEntry.BoolConfigEntry("Enable lwip Debug", "LWIP_DEBUG", false)

    private val entriesARP = listOf(
            ConfigurationEntry.IntConfigEntry("Number of active MAC-IP address pairs cached", "LWIP_ARP_TABLE_SIZE", 10, 1, 16),
            ConfigurationEntry.IntConfigEntry("The time an ARP entry stays valid after its last update", "LWIP_ARP_MAXAGE", 300, 100, 65535)
    )
    private val entriesSocket = listOf<ConfigurationEntry>(
            ConfigurationEntry.BoolConfigEntry("LWIP socket supports multithread", "LWIP_SOCKET_MULTITHREAD", true),
            ConfigurationEntry.IntConfigEntry("Max number of open sockets", "LWIP_MAX_SOCKETS", 1, 16, 10),
            soReuse,
            ConfigurationEntry.BoolConfigEntry("SO_REUSEADDR copies broadcast/multicast to all matches", "LWIP_SO_REUSE_RXTOALL", true, soReuse),
            ConfigurationEntry.BoolConfigEntry("Enable SO_RCVBUF option", "LWIP_SO_RCVBUF", false),
            ConfigurationEntry.IntConfigEntry("The default value for recv_bufsize", "LWIP_RECV_BUFSIZE_DEFAULT", 11680, 2920, 11680),
            ConfigurationEntry.IntConfigEntry("TCP socket/netconn close waits time to send the FIN", "LWIP_TCP_CLOSE_TIMEOUT_MS_DEFAULT", 10000, 10000, 20000)
    )

    private val entryICMP = listOf(
            ConfigurationEntry.BoolConfigEntry("Respond to multicast pings", "LWIP_MULTICAST_PING", false),
            ConfigurationEntry.BoolConfigEntry("Respond to broadcast pings", "LWIP_BROADCAST_PING", false)
    )

    private val entriesDHCP = listOf<ConfigurationEntry>(
            ConfigurationEntry.BoolConfigEntry("DHCP: Perform ARP check on any offered address", "LWIP_DHCP_DOES_ARP_CHECK", true),
            ConfigurationEntry.IntConfigEntry("Maximum number of NTP servers", "LWIP_DHCP_MAX_NTP_SERVERS", 1, 1, 8),
            ConfigurationEntry.IntConfigEntry("Multiplier for lease time, in seconds", "LWIP_DHCPS_LEASE_UNIT", 60, 1, 3600),
            ConfigurationEntry.IntConfigEntry("Maximum number of stations", "LWIP_DHCPS_MAX_STATION_NUM", 8, 1, 8)
    )

    private val entriesLwipAutoip = listOf(
            ConfigurationEntry.IntConfigEntry("DHCP Probes before self-assigning IPv4 LL address", "LWIP_DHCP_AUTOIP_COOP_TRIES", 2, 1, 100)
    )

    private val entriesPerInterfaceLoopback = listOf(
            ConfigurationEntry.IntConfigEntry("Max queued loopback packets per interface", "LWIP_LOOPBACK_MAX_PBUFS", 0, 0, 16)
    )

    private val defaultTCPOversize = ConfigurationEntry.BoolConfigEntry("MSS", "TCP_OVERSIZE_MSS", true)
    private val entriesTCP = listOf(
            ConfigurationEntry.IntConfigEntry("Maximum active TCP Connections", "LWIP_MAX_ACTIVE_TCP", 5, 1, 32),
            ConfigurationEntry.IntConfigEntry("Maximum listening TCP Connections", "LWIP_MAX_LISTENING_TCP", 8, 1, 16),
            ConfigurationEntry.IntConfigEntry("Maximum number of retransmissions of data segments", "TCP_MAXRTX", 12, 3, 12),
            ConfigurationEntry.IntConfigEntry("Maximum number of retransmissions of SYN segments", "TCP_SYNMAXRTX", 6, 3, 12),
            ConfigurationEntry.IntConfigEntry("Maximum Segment Size (MSS)", "TCP_MSS", 1460, 536, 1460),
            ConfigurationEntry.IntConfigEntry("Default send buffer size", "TCP_SND_BUF_DEFAULT", 2920, 2920, 11680),
            ConfigurationEntry.IntConfigEntry("Default receive window size", "TCP_WND_DEFAULT", 5840, 2920, 11680),
            ConfigurationEntry.IntConfigEntry("Default TCP receive mail box size", "TCP_RECVMBOX_SIZE", 6, 6, 32),
            ConfigurationEntry.BoolConfigEntry("Queue incoming out-of-order segments", "TCP_QUEUE_OOSEQ"),
            ChoiceConfigEntry("Pre-allocate transmit PBUF size", listOf(
                    defaultTCPOversize,
                    ConfigurationEntry.BoolConfigEntry("25% MSS", "TCP_OVERSIZE_QUARTER_MSS", true),
                    ConfigurationEntry.BoolConfigEntry("Disabled", "TCP_OVERSIZE_DISABLE", true)),
                    defaultTCPOversize),
            ConfigurationEntry.BoolConfigEntry("Support the TCP timestamp option", "LWIP_TCP_TIMESTAMPS", false)
    )

    private val entriesUDP = listOf(
            ConfigurationEntry.IntConfigEntry("Maximum active UDP control blocks", "LWIP_MAX_UDP_PCBS", 4, 1, 32),
            ConfigurationEntry.IntConfigEntry("Default UDP receive mail box size", "UDP_RECVMBOX_SIZE", 6, 6, 64)
    )

    private val entriesIPV6 = listOf<ConfigurationEntry>(
            ConfigurationEntry.IntConfigEntry("Number of IPv6 addresses per netif", "LWIP_IPV6_NUM_ADDRESSES", 3, 3, 5),
            ConfigurationEntry.BoolConfigEntry("Forward IPv6 packets across netifs", "LWIP_IPV6_FORWARD", false),
            ConfigurationEntry.BoolConfigEntry("Fragment outgoing IPv6 packets that are too big", "LWIP_IPV6_FRAG", false)
    )

    private val entriesDebug = listOf<ConfigurationEntry>(
            ConfigurationEntry.BoolConfigEntry("Enable debugging in etharp.c", "LWIP_ETHARP_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in netif.c", "LWIP_NETIF_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in pbuf.c", "LWIP_PBUF_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in api_lib.c", "LWIP_API_LIB_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in api_msg.c", "LWIP_API_MSG_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in sockets.c", "LWIP_SOCKETS_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in icmp.c", "LWIP_ICMP_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in igmp.c", "LWIP_IGMP_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in inet.c", "LWIP_INET_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in ethernetif.c", "LWIP_ETHERNETIF_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging for IP", "LWIP_IP_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in ip_frag.c for both frag & reass", "LWIP_IP_REASS_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in raw.c", "LWIP_RAW_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in mem.c", "LWIP_MEM_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in memp.c", "LWIP_MEMP_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in sys.c", "LWIP_SYS_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in timers.c", "LWIP_TIMERS_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging for TCP", "LWIP_TCP_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in tcp_in.c for incoming debug", "LWIP_TCP_INPUT_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in tcp_in.c for fast retransmit", "LWIP_TCP_FR_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in TCP for retransmit", "LWIP_TCP_RTO_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging for TCP congestion window", "LWIP_TCP_CWND_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in tcp_in.c for window updating", "LWIP_TCP_WND_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in tcp_out.c output functions", "LWIP_TCP_OUTPUT_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging for TCP with the RST message", "LWIP_TCP_RST_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging for TCP queue lengths", "LWIP_TCP_QLEN_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in UDP", "LWIP_UDP_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in tcpip.c", "LWIP_TCPIP_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in slipif.c", "LWIP_SLIP_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in dhcp.c", "LWIP_DHCP_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in dhcpserver.c", "LWIP_DHCP_SERVER_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging in autoip.c", "LWIP_AUTOIP_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging for DNS", "LWIP_DNS_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging for IPv6", "LWIP_IP6_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging for SNTP", "LWIP_SNTP_DEBUG", false),
            ConfigurationEntry.BoolConfigEntry("Enable debugging for LWIP thread safety", "LWIP_THREAD_SAFE_DEBUG", false)
    )

    private val entriesLWIP = listOf(
            ConfigurationEntry.SubPanelConfigEntry("ARP", entriesARP),
            ConfigurationEntry.SubPanelConfigEntry("SOCKET", entriesSocket),
            ConfigurationEntry.BoolConfigEntry("Enable fragment outgoing IP packets", "LWIP_IP_FRAG", false),
            ConfigurationEntry.BoolConfigEntry("Enable reassembly incoming fragmented IP packets", "LWIP_IP_REASSEMBLY", false),
            ConfigurationEntry.IntConfigEntry("Total maximum amount of pbufs waiting to be reassembled", "LWIP_IP_REASS_MAX_PBUFS", 10, 1, 16),
            ConfigurationEntry.BoolConfigEntry("Enable broadcast filter per pcb on udp and raw send operation", "LWIP_IP_SOF_BROADCAST", false),
            ConfigurationEntry.BoolConfigEntry("Enable the broadcast filter on recv operations", "LWIP_IP_SOF_BROADCAST_RECV", false),
            icmp,
            ConfigurationEntry.SubPanelConfigEntry("ICMP", entryICMP, icmp),
            ConfigurationEntry.BoolConfigEntry("Enable application layer to hook into the IP layer itself", "LWIP_RAW", false),
            ConfigurationEntry.SubPanelConfigEntry("DHCP", entriesDHCP),
            lwipAutoip,
            ConfigurationEntry.SubPanelConfigEntry("AUTOIP", entriesLwipAutoip, lwipAutoip),
            ConfigurationEntry.BoolConfigEntry("Enable IGMP module", "LWIP_IGMP", true),
            ConfigurationEntry.IntConfigEntry("The maximum of DNS servers", "DNS_MAX_SERVERS", 2, 1, 5),
            perInterfaceLoopback,
            ConfigurationEntry.SubPanelConfigEntry("per-interface loopback", entriesPerInterfaceLoopback, perInterfaceLoopback),
            ConfigurationEntry.SubPanelConfigEntry("TCP", entriesTCP),
            ConfigurationEntry.SubPanelConfigEntry("UDP", entriesUDP),
            ConfigurationEntry.IntConfigEntry("TCP/IP Task Stack Size", "TCPIP_TASK_STACK_SIZE", 2048, 2048, 8192),
            ConfigurationEntry.IntConfigEntry("Maximum LWIP RAW PCBs", "LWIP_MAX_RAW_PCBS", 4, 1, 32),
            enableIPV6,
            ConfigurationEntry.SubPanelConfigEntry("IPV6", entriesIPV6, enableIPV6),
            ConfigurationEntry.BoolConfigEntry("Enable statistics collection in lwip_stats", "LWIP_STATS", false),
            enableDebug,
            ConfigurationEntry.SubPanelConfigEntry("Debug", entriesDebug, enableDebug)
    )

    private val newLibEnable = ConfigurationEntry.BoolConfigEntry("Enable newlib", "NEWLIB_ENABLE", true)
    private val defaultNewLibLevel = ConfigurationEntry.BoolConfigEntry("normal", "NEWLIB_LIBRARY_LEVEL_NORMAL", true)
    private val entriesNewLib = listOf(
            newLibEnable,
            ChoiceConfigEntry("newlib level", listOf(
                    defaultNewLibLevel,
                    ConfigurationEntry.BoolConfigEntry("nano", "NEWLIB_LIBRARY_LEVEL_NANO")
            ), defaultNewLibLevel)
    )

    private val mbedTLS = ConfigurationEntry.BoolConfigEntry("mbedTLS", "SSL_USING_MBEDTLS", true)
    private val mbedTLSDebug = ConfigurationEntry.BoolConfigEntry("Enable mbedTLS debugging", "MBEDTLS_DEBUG")
    private val enableMbedTime = ConfigurationEntry.BoolConfigEntry("Enable mbedtls time", "MBEDTLS_HAVE_TIME", true)
    private val mbedTlsServer = ConfigurationEntry.BoolConfigEntry("mbed server", "MBEDTLS_TLS_SERVER", true)
    private val mbedTlsClient = ConfigurationEntry.BoolConfigEntry("mbed server", "MBEDTLS_TLS_CLIENT", true)
    private val mbedTlsEnable = ConfigurationEntry.BoolConfigEntry("mbed server", "MBEDTLS_TLS_ENABLED", true)
    private val mbedTlsPskMode = ConfigurationEntry.BoolConfigEntry("Enable pre-shared-key ciphersuites", "MBEDTLS_PSK_MODES")
    private val mbedTlsEcpC = ConfigurationEntry.BoolConfigEntry("Elliptic Curve Ciphers", "MBEDTLS_ECP_C")
    private val mbedTlsSupportEcpC = ConfigurationEntry.BoolConfigEntry("Support Elliptic Curve based ciphersuites", "MBEDTLS_KEY_EXCHANGE_ELLIPTIC_CURVE", false, mbedTlsEcpC)
    private val mbedTlsEcdhC = ConfigurationEntry.BoolConfigEntry("Elliptic Curve Diffie-Hellman (ECDH)", "MBEDTLS_ECP_C", true, mbedTlsEcpC)
    private val mbedTlsEcdsaC = ConfigurationEntry.BoolConfigEntry("Elliptic Curve DSA", "MBEDTLS_ECDSA_C", true, mbedTlsEcdhC)
    private val mbedTlsV1_2 = ConfigurationEntry.BoolConfigEntry("Support TLS 1.2 protocol", "MBEDTLS_SSL_PROTO_TLS1_2", true, mbedTlsEnable)
    private val mbedTlsV1_1 = ConfigurationEntry.BoolConfigEntry("Support TLS 1.1 protocol", "MBEDTLS_SSL_PROTO_TLS1_1", true, mbedTlsEnable)
    private val defaultMbedTlsMode = ConfigurationEntry.BoolConfigEntry("Server & Client", "MBEDTLS_TLS_SERVER_AND_CLIENT", true, listOf(mbedTlsClient, mbedTlsServer, mbedTlsEnable))
    private val defaultMbedTlsRC4Mode = ConfigurationEntry.BoolConfigEntry("Disabled", "MBEDTLS_RC4_DISABLED", true)

    private val entriesTlsKeyExchange = listOf(
            mbedTlsPskMode,
            ConfigurationEntry.BoolConfigEntry("Enable PSK based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_PSK", false, mbedTlsPskMode),
            ConfigurationEntry.BoolConfigEntry("Enable DHE-PSK based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_DHE_PSK", false, mbedTlsPskMode),
            ConfigurationEntry.BoolConfigEntry("Enable ECDHE-PSK based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_ECDHE_PSK", false, mbedTlsPskMode),
            ConfigurationEntry.BoolConfigEntry("Enable RSA-PSK based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_RSA_PSK", true, mbedTlsPskMode),
            ConfigurationEntry.BoolConfigEntry("Enable RSA-only based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_RSA", true),
            ConfigurationEntry.BoolConfigEntry("Enable DHE-RSA based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_DHE_RSA"),
            mbedTlsSupportEcpC,
            ConfigurationEntry.BoolConfigEntry("Enable ECDHE-RSA based ciphersuite mode", "MBEDTLS_KEY_EXCHANGE_ECDHE_RSA", false, listOf(mbedTlsSupportEcpC, mbedTlsEcdhC)),
            ConfigurationEntry.BoolConfigEntry("Enable ECDHE-ECDSA based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_ECDHE_ECDSA", false, listOf(mbedTlsSupportEcpC, mbedTlsEcdhC, mbedTlsEcdsaC)),
            ConfigurationEntry.BoolConfigEntry("Enable ECDH-ECDSA based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_ECDH_ECDSA", false, listOf(mbedTlsSupportEcpC, mbedTlsEcdhC, mbedTlsEcdsaC)),
            ConfigurationEntry.BoolConfigEntry("Enable ECDH-RSA based ciphersuite modes", "MBEDTLS_KEY_EXCHANGE_ECDH_RSA", false, listOf(mbedTlsSupportEcpC, mbedTlsEcdhC))
    )
    private val entriesMbedTLS = listOf(
            ConfigurationEntry.IntConfigEntry("TLS maximum message content length", "MBEDTLS_SSL_MAX_CONTENT_LEN", 4096, 512, 16384),
            mbedTLSDebug,
            ConfigurationEntry.IntConfigEntry("Mbedtls debugging level", "MBEDTLS_DEBUG_LEVEL", 4, 0, 4, mbedTLSDebug),
            enableMbedTime,
            ConfigurationEntry.BoolConfigEntry("Enable mbedtls time data", "MBEDTLS_HAVE_TIME_DATE", false, enableMbedTime),
            ChoiceConfigEntry("newlib level", listOf(
                    defaultMbedTlsMode,
                    ConfigurationEntry.BoolConfigEntry("server only", "MBEDTLS_TLS_SERVER_ONLY", false, listOf(mbedTlsServer, mbedTlsEnable)),
                    ConfigurationEntry.BoolConfigEntry("client only", "MBEDTLS_TLS_CLIENT_ONLY", false, listOf(mbedTlsClient, mbedTlsEnable)),
                    ConfigurationEntry.BoolConfigEntry("disable", "MBEDTLS_TLS_DISABLED")
            ), defaultMbedTlsMode),
            ConfigurationEntry.SubPanelConfigEntry("TLS Key Exchange Methods", entriesTlsKeyExchange, mbedTlsEnable)
    )
    private val entriesMbedTLSSymmetricCipher = listOf(
            ConfigurationEntry.BoolConfigEntry("AES block cipher", "MBEDTLS_AES_C", true),
            ConfigurationEntry.BoolConfigEntry("Camellia block cipher", "MBEDTLS_CAMELLIA_C"),
            ConfigurationEntry.BoolConfigEntry("DES block cipher (legacy, insecure)", "MBEDTLS_DES_C"),
            ChoiceConfigEntry("RC4 Stream Cipher (legacy, insecure)", listOf(
                    defaultMbedTlsRC4Mode,
                    ConfigurationEntry.BoolConfigEntry("Enabled, not in default ciphersuites", "MBEDTLS_RC4_ENABLED_NO_DEFAULT"),
                    ConfigurationEntry.BoolConfigEntry("Enabled", "MBEDTLS_RC4_ENABLED")
            ), defaultMbedTlsRC4Mode),
            ConfigurationEntry.BoolConfigEntry("Blowfish block cipher", "MBEDTLS_BLOWFISH_C"),
            ConfigurationEntry.BoolConfigEntry("XTEA block cipher", "MBEDTLS_XTEA_C"),
            ConfigurationEntry.BoolConfigEntry("CCM (Counter with CBC-MAC) block cipher modes", "MBEDTLS_CCM_C"),
            ConfigurationEntry.BoolConfigEntry("GCM (Galois/Counter) block cipher modes", "MBEDTLS_GCM_C")
    )
    private val entriesCertificates = listOf(
            ConfigurationEntry.BoolConfigEntry("Read & Parse PEM formatted certificates", "MBEDTLS_PEM_PARSE_C", true),
            ConfigurationEntry.BoolConfigEntry("Write PEM formatted certificates", "MBEDTLS_PEM_WRITE_C", true),
            ConfigurationEntry.BoolConfigEntry("X.509 CRL parsing", "MBEDTLS_X509_CRL_PARSE_C", true),
            ConfigurationEntry.BoolConfigEntry("X.509 CSR parsing", "MBEDTLS_X509_CSR_PARSE_C", true)

    )
    private val entriesEllipticCurveCipher = listOf(
            mbedTlsEcdhC,
            mbedTlsEcdsaC,
            ConfigurationEntry.BoolConfigEntry("Enable SECP192R1 curve", "MBEDTLS_ECP_DP_SECP192R1_ENABLED", true),
            ConfigurationEntry.BoolConfigEntry("Enable SECP224R1 curve", "MBEDTLS_ECP_DP_SECP224R1_ENABLED", true),
            ConfigurationEntry.BoolConfigEntry("Enable SECP256R1 curve", "MBEDTLS_ECP_DP_SECP256R1_ENABLED", true),
            ConfigurationEntry.BoolConfigEntry("Enable SECP384R1 curve", "MBEDTLS_ECP_DP_SECP384R1_ENABLED", true),
            ConfigurationEntry.BoolConfigEntry("Enable SECP521R1 curve", "MBEDTLS_ECP_DP_SECP521R1_ENABLED", true),
            ConfigurationEntry.BoolConfigEntry("Enable SECP192K1 curve", "MBEDTLS_ECP_DP_SECP192K1_ENABLED", true),
            ConfigurationEntry.BoolConfigEntry("Enable SECP224K1 curve", "MBEDTLS_ECP_DP_SECP224K1_ENABLED", true),
            ConfigurationEntry.BoolConfigEntry("Enable SECP256K1 curve", "MBEDTLS_ECP_DP_SECP256K1_ENABLED", true),
            ConfigurationEntry.BoolConfigEntry("Enable BP256R1 curve", "MBEDTLS_ECP_DP_BP256R1_ENABLED", true),
            ConfigurationEntry.BoolConfigEntry("Enable BP384R1 curve", "MBEDTLS_ECP_DP_BP384R1_ENABLED", true),
            ConfigurationEntry.BoolConfigEntry("Enable BP512R1 curve", "MBEDTLS_ECP_DP_BP512R1_ENABLED", true),
            ConfigurationEntry.BoolConfigEntry("Enable CURVE25519 curve", "MBEDTLS_ECP_DP_CURVE25519_ENABLED", true),
            ConfigurationEntry.BoolConfigEntry("NIST 'modulo p' optimisations", "MBEDTLS_ECP_NIST_OPTIM", true)
    )

    private val openSSLDebug = ConfigurationEntry.BoolConfigEntry("Enable OpenSSL debugging", "OPENSSL_DEBUG")
    private val defaultOpensslAssertFunction = ConfigurationEntry.BoolConfigEntry("Check and exit", "OPENSSL_ASSERT_EXIT")
    private val entriesOpenSSL = listOf(
            openSSLDebug,
            ConfigurationEntry.IntConfigEntry("TOpenSSL debugging level", "OPENSSL_DEBUG_LEVEL", 0, 0, 255, openSSLDebug),
            ConfigurationEntry.BoolConfigEntry("Enable OpenSSL low-level module debugging", "OPENSSL_LOWLEVEL_DEBUG", false, openSSLDebug, listOf(mbedTLSDebug)),
            ChoiceConfigEntry("Select OpenSSL assert function", listOf(
                    ConfigurationEntry.BoolConfigEntry("Do nothing", "OPENSSL_ASSERT_DO_NOTHING"),
                    defaultOpensslAssertFunction,
                    ConfigurationEntry.BoolConfigEntry("Show debugging message", "OPENSSL_ASSERT_DEBUG", false, openSSLDebug),
                    ConfigurationEntry.BoolConfigEntry("Show debugging message and exit", "OPENSSL_ASSERT_DEBUG_EXIT", false, openSSLDebug),
                    ConfigurationEntry.BoolConfigEntry("Show debugging message and block", "OPENSSL_ASSERT_DEBUG_BLOCK", false, openSSLDebug)
            ), mbedTLS)
    )
    private val entriesSSL = listOf(
            ChoiceConfigEntry("Choose SSL/TLS library", listOf(
                    mbedTLS,
                    ConfigurationEntry.BoolConfigEntry("axTLS", "SSL_USING_AXTLS"),
                    ConfigurationEntry.BoolConfigEntry("wolfSSL", "SSL_USING_WOLFSSL")
            ), mbedTLS),
            ConfigurationEntry.SubPanelConfigEntry("SSL", entriesMbedTLS, mbedTLS),
            ConfigurationEntry.BoolConfigEntry("Support TLS renegotiation", "MBEDTLS_SSL_RENEGOTIATION", false, mbedTlsEnable),
            ConfigurationEntry.BoolConfigEntry("Legacy SSL 3.0 support", "MBEDTLS_SSL_PROTO_SSL3", false, mbedTlsEnable),
            ConfigurationEntry.BoolConfigEntry("Support TLS 1.0 protocol", "MBEDTLS_SSL_PROTO_TLS1", true, mbedTlsEnable),
            mbedTlsV1_1,
            mbedTlsV1_2,
            ConfigurationEntry.BoolConfigEntry("Support DTLS protocol (all versions)", "MBEDTLS_SSL_PROTO_DTLS", false, listOf(mbedTlsV1_1, mbedTlsV1_2)),
            ConfigurationEntry.BoolConfigEntry("Support ALPN (Application Layer Protocol Negotiation)", "MBEDTLS_SSL_ALPN", false, mbedTlsEnable),
            ConfigurationEntry.BoolConfigEntry("TLS: Support RFC 5077 SSL session tickets", "MBEDTLS_SSL_SESSION_TICKETS", false, mbedTlsEnable),
            ConfigurationEntry.SubPanelConfigEntry("Symmetric Ciphers", entriesMbedTLSSymmetricCipher),
            ConfigurationEntry.BoolConfigEntry("Enable RIPEMD-160 hash algorithm", "MBEDTLS_RIPEMD160_C"),
            ConfigurationEntry.SubPanelConfigEntry("Certificates", entriesCertificates),
            mbedTlsEcpC,
            ConfigurationEntry.SubPanelConfigEntry("Elliptic Curve Ciphers config", entriesEllipticCurveCipher, mbedTlsEcpC),
            ConfigurationEntry.SubPanelConfigEntry("OpenSSL", entriesOpenSSL)

    )

    val entriesMenu = listOf(
            ConfigurationEntry.SubPanelConfigEntry("Serial flasher config", entriesESPTool),
            ConfigurationEntry.SubPanelConfigEntry("Wifi config", entriesWIFIConfig),
            ConfigurationEntry.SubPanelConfigEntry("Partition Table", entriesPartitionTable),
            ConfigurationEntry.SubPanelConfigEntry("Compiler options", entriesCompilerOptions),
            ConfigurationEntry.SubPanelConfigEntry("Component config", entriesComponentConfig),
            ConfigurationEntry.SubPanelConfigEntry("LWIP", entriesLWIP),
            ConfigurationEntry.SubPanelConfigEntry("Newlib", entriesNewLib),
            ConfigurationEntry.SubPanelConfigEntry("SSL", entriesSSL),
            ConfigurationEntry.IntConfigEntry("IP Address lost timer interval (seconds)", "IP_LOST_TIMER_INTERVAL", 120, 0, 65535)
    )
}