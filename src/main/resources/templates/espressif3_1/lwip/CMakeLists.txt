cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ )

set(SOURCE_DIR ${RTOS_DIR}/components/lwip)

add_library(lwip STATIC
        ${SOURCE_DIR}/apps/dhcpserver/dhcpserver.c
        ${SOURCE_DIR}/apps/multi-threads/sockets_mt.c
        ${SOURCE_DIR}/apps/ping/esp_ping.c
        ${SOURCE_DIR}/apps/ping/ping.c

        ${SOURCE_DIR}/lwip/src/api/api_lib.c
        ${SOURCE_DIR}/lwip/src/api/api_msg.c
        ${SOURCE_DIR}/lwip/src/api/err.c
        ${SOURCE_DIR}/lwip/src/api/netbuf.c
        ${SOURCE_DIR}/lwip/src/api/netdb.c
        ${SOURCE_DIR}/lwip/src/api/netifapi.c
        ${SOURCE_DIR}/lwip/src/api/sockets.c
        ${SOURCE_DIR}/lwip/src/api/tcpip.c

        ${SOURCE_DIR}/lwip/src/apps/sntp/sntp.c

        ${SOURCE_DIR}/lwip/src/core/def.c
        ${SOURCE_DIR}/lwip/src/core/dns.c
        ${SOURCE_DIR}/lwip/src/core/inet_chksum.c
        ${SOURCE_DIR}/lwip/src/core/init.c
        ${SOURCE_DIR}/lwip/src/core/ip.c
        ${SOURCE_DIR}/lwip/src/core/ipv4
        ${SOURCE_DIR}/lwip/src/core/ipv6
        ${SOURCE_DIR}/lwip/src/core/mem.c
        ${SOURCE_DIR}/lwip/src/core/memp.c
        ${SOURCE_DIR}/lwip/src/core/netif.c
        ${SOURCE_DIR}/lwip/src/core/pbuf.c
        ${SOURCE_DIR}/lwip/src/core/raw.c
        ${SOURCE_DIR}/lwip/src/core/stats.c
        ${SOURCE_DIR}/lwip/src/core/sys.c
        ${SOURCE_DIR}/lwip/src/core/tcp.c
        ${SOURCE_DIR}/lwip/src/core/tcp_in.c
        ${SOURCE_DIR}/lwip/src/core/tcp_out.c
        ${SOURCE_DIR}/lwip/src/core/timeouts.c
        ${SOURCE_DIR}/lwip/src/core/udp.c

        ${SOURCE_DIR}/lwip/src/core/ipv4/autoip.c
        ${SOURCE_DIR}/lwip/src/core/ipv4/dhcp.c
        ${SOURCE_DIR}/lwip/src/core/ipv4/etharp.c
        ${SOURCE_DIR}/lwip/src/core/ipv4/icmp.c
        ${SOURCE_DIR}/lwip/src/core/ipv4/igmp.c
        ${SOURCE_DIR}/lwip/src/core/ipv4/ip4_addr.c
        ${SOURCE_DIR}/lwip/src/core/ipv4/ip4.c
        ${SOURCE_DIR}/lwip/src/core/ipv4/ip4_frag.c

        ${SOURCE_DIR}/lwip/src/core/ipv6/dhcp6.c
        ${SOURCE_DIR}/lwip/src/core/ipv6/ethip6.c
        ${SOURCE_DIR}/lwip/src/core/ipv6/icmp6.c
        ${SOURCE_DIR}/lwip/src/core/ipv6/inet6.c
        ${SOURCE_DIR}/lwip/src/core/ipv6/ip6_addr.c
        ${SOURCE_DIR}/lwip/src/core/ipv6/ip6.c
        ${SOURCE_DIR}/lwip/src/core/ipv6/ip6_frag.c
        ${SOURCE_DIR}/lwip/src/core/ipv6/mld6.c
        ${SOURCE_DIR}/lwip/src/core/ipv6/nd6.c

        ${SOURCE_DIR}/lwip/src/netif/ethernet.c
        ${SOURCE_DIR}/lwip/src/netif/ethernetif.c
        ${SOURCE_DIR}/lwip/src/netif/lowpan6.c
        ${SOURCE_DIR}/lwip/src/netif/slipif.c

        ${SOURCE_DIR}/port/esp8266/freertos/heap.c
        ${SOURCE_DIR}/port/esp8266/freertos/sys_arch.c
        ${SOURCE_DIR}/port/esp8266/freertos/udp_sync.c

        ${SOURCE_DIR}/port/esp8266/netif/ethernetif.c)

        
        
target_compile_options(lwip  PRIVATE  -DMBEDTLS_CONFIG_FILE="mbedtls/esp_config.h" ${COMMON_FLAGS} ${WARNING_FLAGS}  -Wno-address )
set_source_files_properties(${SOURCE_DIR}/lwip/src/apps/sntp/sntp.c PROPERTIES COMPILE_FLAGS  -Wno-implicit-function-declaration)
set_source_files_properties(${SOURCE_DIR}/lwip/src/core/ipv4/ip4.c PROPERTIES COMPILE_FLAGS  -Wno-implicit-function-declaration)


target_include_directories(lwip PUBLIC
        ${RTOS_DIR}/components/heap/include
        ${RTOS_DIR}/components/heap/port/esp8266/include
        ${RTOS_DIR}/components/lwip/include
        ${RTOS_DIR}/components/lwip/include/lwip/apps
        ${RTOS_DIR}/components/lwip/lwip/src/include
        ${RTOS_DIR}/components/lwip/port/esp8266/include
        ${RTOS_DIR}/components/lwip/lwip/src/include/posix
        ${RTOS_DIR}/components/bootloader_support/include
        ${RTOS_DIR}/components/cjson/include
        ${RTOS_DIR}/components/cjson/cJSON
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/espos/include
        ${RTOS_DIR}/components/freertos/include
        ${RTOS_DIR}/components/freertos/include/freertos
        ${RTOS_DIR}/components/freertos/include/freertos/private
        ${RTOS_DIR}/components/freertos/port/esp8266/include
        ${RTOS_DIR}/components/freertos/port/esp8266/include/freertos
        ${RTOS_DIR}/components/jsmn/include
        ${RTOS_DIR}/components/log/include
        ${RTOS_DIR}/components/mqtt/include
        ${RTOS_DIR}/components/mqtt/paho/MQTTClient-C/src
        ${RTOS_DIR}/components/mqtt/paho/MQTTClient-C/src/FreeRTOS
        ${RTOS_DIR}/components/mqtt/paho/MQTTPacket/src
        ${RTOS_DIR}/components/newlib/include
        ${RTOS_DIR}/components/newlib/newlib/include
        ${RTOS_DIR}/components/newlib/newlib/port/include
        ${RTOS_DIR}/components/nvs_flash/include
        ${RTOS_DIR}/components/smartconfig_ack/include
        ${RTOS_DIR}/components/spi_flash/include
        ${RTOS_DIR}/components/spiffs/include
        ${RTOS_DIR}/components/spiffs/include/spiffs
        ${RTOS_DIR}/components/ssl/include
        ${RTOS_DIR}/components/ssl/mbedtls/mbedtls/include
        ${RTOS_DIR}/components/ssl/mbedtls/port/esp8266/include
        ${RTOS_DIR}/components/ssl/mbedtls/port/openssl/include
        ${RTOS_DIR}/components/tcpip_adapter/include
        ${RTOS_DIR}/components/util/include
        ${SOURCE_DIR}/port/esp8266/netif
        ${CMAKE_SOURCE_DIR}/include
        )