cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ )

set(SOURCE_DIR ${RTOS_DIR}/components/mqtt)

add_library(mqtt STATIC
        ${SOURCE_DIR}/paho/MQTTClient-C/src/MQTTClient.c
        ${SOURCE_DIR}/paho/MQTTClient-C/src/FreeRTOS/MQTTFreeRTOS.c
        ${SOURCE_DIR}/paho/MQTTPacket/src/MQTTPacket.c
        ${SOURCE_DIR}/paho/MQTTPacket/src/MQTTUnsubscribeServer.c
        ${SOURCE_DIR}/paho/MQTTPacket/src/MQTTSerializePublish.c
        ${SOURCE_DIR}/paho/MQTTPacket/src/MQTTFormat.c
        ${SOURCE_DIR}/paho/MQTTPacket/src/MQTTConnectServer.c
        ${SOURCE_DIR}/paho/MQTTPacket/src/MQTTSubscribeClient.c
        ${SOURCE_DIR}/paho/MQTTPacket/src/MQTTDeserializePublish.c
        ${SOURCE_DIR}/paho/MQTTPacket/src/MQTTUnsubscribeClient.c
        ${SOURCE_DIR}/paho/MQTTPacket/src/MQTTSubscribeServer.c
        ${SOURCE_DIR}/paho/MQTTPacket/src/MQTTConnectClient.c)

target_compile_options(mqtt  PRIVATE  -DMEMLEAK_DEBUG -DICACHE_FLASH  ${COMMON_FLAGS} ${WARNING_FLAGS} )
target_include_directories(mqtt PUBLIC
        ${CMAKE_SOURCE_DIR}/include
        ${RTOS_DIR}/components/mqtt/include
        ${RTOS_DIR}/components/mqtt/paho/MQTTClient-C/src 
        ${RTOS_DIR}/components/mqtt/paho/MQTTClient-C/src/FreeRTOS
        ${RTOS_DIR}/components/mqtt/paho/MQTTPacket/src
        ${RTOS_DIR}/components/bootloader_support/include
        ${RTOS_DIR}/components/cjson/include
        ${RTOS_DIR}/components/cjson/cJSON
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/espos/include
        ${RTOS_DIR}/components/freertos/include
        ${RTOS_DIR}/components/freertos/include
        ${RTOS_DIR}/components/freertos/include/freertos
        ${RTOS_DIR}/components/freertos/include/freertos/private
        ${RTOS_DIR}/components/freertos/port/esp8266/include
        ${RTOS_DIR}/components/freertos/port/esp8266/include/freertos
        ${RTOS_DIR}/components/jsmn/include
        ${RTOS_DIR}/components/log/include
        ${RTOS_DIR}/components/lwip/include
        ${RTOS_DIR}/components/lwip/include/lwip/apps
        ${RTOS_DIR}/components/lwip/lwip/src/include
        ${RTOS_DIR}/components/lwip/port/esp8266/include
        ${RTOS_DIR}/components/lwip/lwip/src/include/posix
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
        ${RTOS_DIR}/components/tcpip_adapter/include 
        ${RTOS_DIR}/components/util/include 
        paho/MQTTPacket/src
        )