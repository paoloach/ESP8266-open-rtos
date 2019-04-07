cmake_minimum_required(VERSION 3.10)
project(untitled3 )

set(SOURCE_DIR ${RTOS_DIR}/components/esp8266)

file(GLOB DRIVER ${SOURCE_DIR}/driver/*.c)
file(GLOB SOURCE ${SOURCE_DIR}/source/*.c)

add_library(esp8266 STATIC ${SOURCE} ${DRIVER} )
add_library(esp8266_boot STATIC ${SOURCE_DIR}/source/ets_printf)

target_compile_options(esp8266 PRIVATE  ${COMMON_FLAGS} ${WARNING_FLAGS})
target_compile_options(esp8266_boot PRIVATE -DBOOTLOADER_BUILD=1  ${COMMON_FLAGS} ${WARNING_FLAGS})

target_include_directories(esp8266_boot PUBLIC
        ${CMAKE_SOURCE_DIR}/include
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/util/include
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/bootloader/subproject/main/include
        ${RTOS_DIR}/components/bootloader_support/include
        ${RTOS_DIR}/components/bootloader_support/include_priv
        ${RTOS_DIR}/components/spi_flash/include
        ${RTOS_DIR}/components/log/include
        ${RTOS_DIR}/components/util/include
        ${SOURCE_DIR}/source
        )

target_include_directories(esp8266 PUBLIC
        ${CMAKE_SOURCE_DIR}/include
        ${COMMON_INCLUDE}
        ${SOURCE_DIR}/include
        ${SOURCE_DIR}/include/driver
        )