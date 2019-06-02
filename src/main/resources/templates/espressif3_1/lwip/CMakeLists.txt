cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ )

set(SOURCE_DIR ${RTOS_DIR}/components/lwip)

file(GLOB SOURCE
        ${SOURCE_DIR}/apps/dhcpserver/*.c
        ${SOURCE_DIR}/apps/multi-threads/*.c
        ${SOURCE_DIR}/apps/ping/*.c
        ${SOURCE_DIR}/lwip/src/api/*.c
        ${SOURCE_DIR}/lwip/src/apps/sntp/*.c
        ${SOURCE_DIR}/lwip/src/core/*.c
        ${SOURCE_DIR}/lwip/src/core/ipv4/*.c
        ${SOURCE_DIR}/lwip/src/core/ipv6/*.c
        ${SOURCE_DIR}/lwip/src/netif/*.c
        ${SOURCE_DIR}/port/esp8266/freertos/*.c
        ${SOURCE_DIR}/port/esp8266/netif/*.c)


string(FIND ${SDKCONFIG} "CONFIG_USING_ESP_VFS" FOUND_USING_ESP_VFS)
if (FOUND_USING_ESP_VFS GREATER 1)
    file(GLOB SOURCE_PORT ${SOURCE_DIR}/port/*.c)
endif()


string(FIND ${SDKCONFIG} "CONFIG_LWIP_SOCKET_MULTITHREAD" FOUND_LWIP_SOCKET_MULTITHREAD)
if (FOUND_LWIP_SOCKET_MULTITHREAD GREATER 1)
    set(LWIP_SOCKET_MT_SRC ${SOURCE_DIR}/lwip/src/api/sockets.c)
endif()


add_library(lwip STATIC ${SOURCE} ${SOURCE_PORT} ${LWIP_SOCKET_MT_SRC})


target_compile_options(lwip  PRIVATE   ${COMMON_FLAGS} ${WARNING_FLAGS}  -Wno-address )

set_source_files_properties(${SOURCE_DIR}/lwip/src/apps/sntp/sntp.c PROPERTIES COMPILE_FLAGS  -Wno-implicit-function-declaration)
set_source_files_properties(${SOURCE_DIR}/lwip/src/core/ipv4/ip4.c PROPERTIES COMPILE_FLAGS  -Wno-implicit-function-declaration)


target_include_directories(lwip PUBLIC
        ${COMMON_INCLUDE}
        ${SOURCE_DIR}/port/esp8266/netif
        )