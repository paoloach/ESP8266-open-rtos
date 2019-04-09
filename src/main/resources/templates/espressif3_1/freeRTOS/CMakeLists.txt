cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ )

set(SOURCE_DIR ${RTOS_DIR}/components/freertos)

file(GLOB PORT_SRC_ASM ${SOURCE_DIR}/port/esp8266/*.S)
file(GLOB PORT_SRC_C ${SOURCE_DIR}/port/esp8266/*.c)


file(GLOB FREE_RTOS_SRC ${SOURCE_DIR}/freertos/*.c)


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

target_include_directories(freeRTOS PUBLIC ${COMMON_INCLUDE} )