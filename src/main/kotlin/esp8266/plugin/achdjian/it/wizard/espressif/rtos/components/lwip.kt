package esp8266.plugin.achdjian.it.wizard.espressif.rtos.components

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry.*


private val enableIPV6 = BoolConfigEntry("Enable IPv6", "LWIP_IPV6", true)
private val lwipSocketMultithread = BoolConfigEntry("LWIP socket supports multithread", "LWIP_SOCKET_MULTITHREAD", true)
private val espUdpSyncSend = BoolConfigEntry("LWIP socket UDP sync send", "ESP_UDP_SYNC_SEND", true)
private val tcpHighSpeedRetransmission = BoolConfigEntry("TCP high speed retransmissions", "TCP_HIGH_SPEED_RETRANSMISSION", defaultValue = false)
private val tcpQueueOoseq = BoolConfigEntry("Queue incoming out-of-order segments", "TCP_QUEUE_OOSEQ")


val lwipHighThroughput = BoolConfigEntry("Enable lwip high throughput", "LWIP_HIGH_THROUGHPUT", false, associated = listOf(tcpQueueOoseq, tcpHighSpeedRetransmission, socFullICache, wifiTxRateSequenceFromHigh))

private val entriesARP = listOf(
        IntConfigEntry("Number of active MAC-IP address pairs cached", "LWIP_ARP_TABLE_SIZE", 10, 1, 16),
        IntConfigEntry("The time an ARP entry stays valid after its last update", "LWIP_ARP_MAXAGE", 300, 100, 65535)
)

private val soReuse = BoolConfigEntry("Enable SO_REUSEADDR option", "LWIP_SO_REUSE", true)
private val icmp = BoolConfigEntry("ICMP", "LWIP_ICMP", true)
private val lwipAutoip = BoolConfigEntry("Enable IPV4 Link-Local Addressing (AUTOIP)", "LWIP_AUTOIP", false)
private val perInterfaceLoopback = BoolConfigEntry("nable per-interface loopback", "LWIP_NETIF_LOOPBACK", false)

private val enableDebug = BoolConfigEntry("Enable lwip Debug", "LWIP_DEBUG", false)




private val entryICMP = listOf(
        BoolConfigEntry("Respond to multicast pings", "LWIP_MULTICAST_PING", false),
        BoolConfigEntry("Respond to broadcast pings", "LWIP_BROADCAST_PING", false),
        BoolConfigEntry("Enable application layer to hook into the IP layer itself", "LWIP_RAW", false)
)

private val entriesDHCP = listOf<ConfigurationEntry>(
        BoolConfigEntry("DHCP: Perform ARP check on any offered address", "LWIP_DHCP_DOES_ARP_CHECK", true),
        IntConfigEntry("Maximum number of NTP servers", "LWIP_DHCP_MAX_NTP_SERVERS", 1, 1, 8),
        IntConfigEntry("Multiplier for lease time, in seconds", "LWIP_DHCPS_LEASE_UNIT", 60, 1, 3600),
        IntConfigEntry("Maximum number of stations", "LWIP_DHCPS_MAX_STATION_NUM", 8, 1, 8)
)

private val entriesLwipAutoip = listOf(
        IntConfigEntry("DHCP Probes before self-assigning IPv4 LL address", "LWIP_DHCP_AUTOIP_COOP_TRIES", 2, 1, 100),
        IntConfigEntry("Max IP conflicts before rate limiting", "LWIP_AUTOIP_MAX_CONFLICTS", 9, 1, 100),
        IntConfigEntry("Rate limited interval (seconds)", "LWIP_AUTOIP_RATE_LIMIT_INTERVAL", 20, 1, 120)
)

private val entriesPerInterfaceLoopback = listOf(
        IntConfigEntry("Max queued loopback packets per interface", "LWIP_LOOPBACK_MAX_PBUFS", 0, 0, 16)
)

private val defaultTCPOversize = BoolConfigEntry("MSS", "TCP_OVERSIZE_MSS", true)
private val entriesTCP = listOf(
        tcpHighSpeedRetransmission,
        IntConfigEntry("Maximum active TCP Connections", "LWIP_MAX_ACTIVE_TCP", 5, 1, 32),
        IntConfigEntry("Maximum listening TCP Connections", "LWIP_MAX_LISTENING_TCP", 8, 1, 16),
        IntConfigEntry("Maximum number of retransmissions of data segments", "TCP_MAXRTX", 12, 3, 12),
        IntConfigEntry("Maximum number of retransmissions of SYN segments", "TCP_SYNMAXRTX", 6, 3, 12),
        IntConfigEntry("Maximum Segment Size (MSS)", "TCP_MSS", 1436, 536, 1460),
        IntConfigEntry("Default send buffer size", "TCP_SND_BUF_DEFAULT", 2920, 2920, 11680),
        IntConfigEntry("Default receive window size", "TCP_WND_DEFAULT", 5840, 2920, 11680),
        IntConfigEntry("Default TCP receive mail box size", "TCP_RECVMBOX_SIZE", 6, 6, 32),
        tcpQueueOoseq,
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
        BoolConfigEntry("Fragment outgoing IPv6 packets that are too big", "LWIP_IPV6_FRAG", false),
        BoolConfigEntry("The IPv6 ND6 RDNSS max DNS servers", "LWIP_ND6_RDNSS_MAX_DNS_SERVERS", false)
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



private val entriesSocket = listOf<ConfigurationEntry>(
        BoolConfigEntry("LWIP socket supports IPv6 multicast configuration", "LWIP_IPV6_MLD_SOCK", true, dependsOn = enableIPV6),
        lwipSocketMultithread,
        BoolConfigEntry("set socket SO_LINGER default", "SET_SOLINGER_DEFAULT", true, dependsOn = lwipSocketMultithread),
        espUdpSyncSend,
        IntConfigEntry("LWIP socket UDP sync send retry max count", "ESP_UDP_SYNC_RETRY_MAX", 5, 1, 5, dependsOn = espUdpSyncSend),
        IntConfigEntry("Max number of open sockets", "LWIP_MAX_SOCKETS", 10, 1, 16),
        soReuse,
        BoolConfigEntry("SO_REUSEADDR copies broadcast/multicast to all matches", "LWIP_SO_REUSE_RXTOALL", true, soReuse),
        BoolConfigEntry("Enable SO_RCVBUF option", "LWIP_SO_RCVBUF", false),
        IntConfigEntry("The default value for recv_bufsize", "LWIP_RECV_BUFSIZE_DEFAULT", 11680, 2920, 11680),
        IntConfigEntry("TCP socket/netconn close waits time to send the FIN", "LWIP_TCP_CLOSE_TIMEOUT_MS_DEFAULT", 10000, 10000, 20000)
)

val entriesLWIP = listOf(
        BoolConfigEntry("Enable lwip use iram option", "LWIP_USE_IRAM", false),
        lwipHighThroughput,
        BoolConfigEntry("Link LWIP global data to IRAM", "LWIP_GLOBAL_DATA_LINK_IRAM", true, And(Not(lwipHighThroughput), Not(socFullICache))),
        IntConfigEntry("TCPIP task receive mail box size", "TCPIP_RECVMBOX_SIZE", 32, 6, 64),
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