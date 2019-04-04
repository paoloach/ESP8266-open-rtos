cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ )

set(SOURCE_DIR ${RTOS_DIR}/components/freertos)

set(PORT_SRC_ASM
        ${SOURCE_DIR}/port/esp8266/xtensa_context.S
        ${SOURCE_DIR}/port/esp8266/os_cpu_a.S
        ${SOURCE_DIR}/port/esp8266/xtensa_vectors.S
        )


set(FREE_RTOS_SRC
        ${SOURCE_DIR}/freertos/event_groups.c
        ${SOURCE_DIR}/freertos/list.c
        ${SOURCE_DIR}/freertos/queue.c
        ${SOURCE_DIR}/freertos/ringbuf.c
        ${SOURCE_DIR}/freertos/stream_buffer.c
        ${SOURCE_DIR}/freertos/tasks.c
        ${SOURCE_DIR}/freertos/timers.c
        )

set(PORT_SRC_C
        ${SOURCE_DIR}/port/esp8266/freertos_hooks.c
        ${SOURCE_DIR}/port/esp8266/impure.c
        ${SOURCE_DIR}/port/esp8266/panic.c
        ${SOURCE_DIR}/port/esp8266/port.c
        ${SOURCE_DIR}/port/esp8266/xtensa_init.c
)


add_library(freeRTOS STATIC
        ${FREE_RTOS_SRC}
        ${PORT_SRC_C}
        ${PORT_SRC_ASM}
        )


SET_SOURCE_FILES_PROPERTIES( ${PORT_SRC_ASM} PROPERTIES
        LANGUAGE C
        COMPILE_FLAGS "-DESP_PLATFORM -MMD -MP -DMQTT_TASK -DMQTTCLIENT_PLATFORM_HEADER=MQTTFreeRTOS.h -DWOLFSSL_USER_SETTINGS -ggdb" )

SET(C_FLAGS " -DICACHE_FLASH  ${COMMON_FLAGS} ${WARNING_FLAGS} "   )
SET_SOURCE_FILES_PROPERTIES( ${PORT_SRC_C} ${FREE_RTOS_SRC} PROPERTIES
        COMPILE_FLAGS "-DICACHE_FLASH  -Os -ggdb -ffunction-sections -fdata-sections -fstrict-volatile-bitfields -mlongcalls -D__ESP_FILE__=__FILE__ -DESP_PLATFORM -D IDF_VER=\"v2.0.0-699-gea34c2a-dirty\" -MMD -MP   -DMQTT_TASK -DMBEDTLS_CONFIG_FILE=\"mbedtls/esp_config.h\" -DWOLFSSL_USER_SETTINGS"
        )

target_compile_options(freeRTOS  PRIVATE  )
target_include_directories(freeRTOS PUBLIC
        ${CMAKE_SOURCE_DIR}/include
        ${RTOS_DIR}/components/heap/include
        ${RTOS_DIR}/components/heap/port/esp8266/include
        ${RTOS_DIR}/components/freertos/include
        ${RTOS_DIR}/components/freertos/include
        ${RTOS_DIR}/components/freertos/include/freertos
        ${RTOS_DIR}/components/freertos/include/freertos/private
        ${RTOS_DIR}/components/freertos/port/esp8266/include
        ${RTOS_DIR}/components/freertos/port/esp8266/include/freertos
        ${RTOS_DIR}/components/bootloader_support/include
        ${RTOS_DIR}/components/cjson/include
        ${RTOS_DIR}/components/cjson/cJSON
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/espos/include
        ${RTOS_DIR}/components/jsmn/include
        ${RTOS_DIR}/components/log/include
        ${RTOS_DIR}/components/lwip/include
        ${RTOS_DIR}/components/lwip/include/lwip/apps
        ${RTOS_DIR}/components/lwip/lwip/src/include
        ${RTOS_DIR}/components/lwip/port/esp8266/include
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
        ${RTOS_DIR}/components/tcpip_adapter/include
        ${RTOS_DIR}/components/util/include
        ${SOURCE_DIR}/freertos
        )