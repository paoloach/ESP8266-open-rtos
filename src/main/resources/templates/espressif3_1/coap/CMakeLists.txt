cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ )

set(SOURCE_DIR ${RTOS_DIR}/components/coap)

add_library(coap STATIC
    ${SOURCE_DIR}/libcoap/src/address.c
    ${SOURCE_DIR}/libcoap/src/async.c
    ${SOURCE_DIR}/libcoap/src/block.c
    ${SOURCE_DIR}/libcoap/src/coap_time.c
    ${SOURCE_DIR}/libcoap/src/debug.c
    ${SOURCE_DIR}/libcoap/src/encode.c
    ${SOURCE_DIR}/libcoap/src/hashkey.c
    ${SOURCE_DIR}/libcoap/src/mem.c
    ${SOURCE_DIR}/libcoap/src/net.c
    ${SOURCE_DIR}/libcoap/src/option.c
    ${SOURCE_DIR}/libcoap/src/pdu.c
    ${SOURCE_DIR}/libcoap/src/resource.c
    ${SOURCE_DIR}/libcoap/src/str.c
    ${SOURCE_DIR}/libcoap/src/subscribe.c
    ${SOURCE_DIR}/libcoap/src/uri.c
    ${SOURCE_DIR}/port/coap_io_socket.c

    )

set(COMPONENT_REQUIRES lwip)

target_compile_definitions(coap PUBLIC WITH_POSIX)

set_source_files_properties(
    libcoap/src/debug.c
    libcoap/src/pdu.c
    PROPERTIES COMPILE_FLAGS
    -Wno-write-strings)

# Temporary suppress "fallthrough" warnings until they are fixed in libcoap repo
set_source_files_properties(
    libcoap/src/option.c
    PROPERTIES COMPILE_FLAGS
    -Wno-implicit-fallthrough)



target_compile_options(coap PRIVATE ${COMMON_FLAGS} ${WARNING_FLAGS})

target_include_directories(coap PUBLIC
        ${CMAKE_SOURCE_DIR}/include
        ${SOURCE_DIR}/port/include
        ${SOURCE_DIR}/port/include/coap
        ${SOURCE_DIR}/libcoap/include
        ${SOURCE_DIR}/libcoap/include/coap
        ${RTOS_DIR}/components/heap/include/
        ${RTOS_DIR}/components/heap/port/esp8266/include/
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/log/include
        ${RTOS_DIR}/components/bootloader_support/include
        ${RTOS_DIR}/components/cjson/include
        ${RTOS_DIR}/components/cjson/cJSON
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/espos/include
        ${RTOS_DIR}/components/freertos/include
        ${RTOS_DIR}/components/freertos/include/freertos
        ${RTOS_DIR}/components/freertos/include/freertos/private
        ${RTOS_DIR}/components/freertos/port/esp8266/include
        ${RTOS_DIR}/components/freertos/port/esp8266/include/freertos
        ${RTOS_DIR}/components/jsmn/include
        ${RTOS_DIR}/components/lwip/include
        ${RTOS_DIR}/components/lwip/include/lwip/apps
        ${RTOS_DIR}/components/lwip/lwip/src/include
        ${RTOS_DIR}/components/lwip/port/esp8266/include
        ${RTOS_DIR}/components/lwip/port/esp8266/include/port
        ${RTOS_DIR}/components/lwip/lwip/src/include/posix
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
        ${SOURCE_DIR}
        )