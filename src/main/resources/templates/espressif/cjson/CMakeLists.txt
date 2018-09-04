cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ )

set(SOURCE_DIR ${RTOS_DIR}/components/cjson/cJSON)

add_library(cjson STATIC
        ${SOURCE_DIR}/cJSON_Utils.c
        ${SOURCE_DIR}/test.c
        ${SOURCE_DIR}/cJSON.c)

target_compile_options(cjson  PRIVATE -DMEMLEAK_DEBUG -DICACHE_FLASH  ${COMMON_FLAGS} ${WARNING_FLAGS} )
target_include_directories(cjson PUBLIC
        ${CMAKE_SOURCE_DIR}/include
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/util/include
        ${RTOS_DIR}/components/bootloader/subproject/main/include
        ${RTOS_DIR}/components/bootloader_support/include
        ${RTOS_DIR}/components/bootloader_support/include_priv
        ${RTOS_DIR}/components/spi_flash/include
        ${RTOS_DIR}/components/log/include
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/util/include
        ${SOURCE_DIR}
        )