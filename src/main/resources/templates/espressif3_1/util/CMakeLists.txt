cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ )

set(SOURCE_DIR ${RTOS_DIR}/components/util)

file(GLOB SOURCE ${SOURCE_DIR}/src/*.c)

add_library(util STATIC ${SOURCE})
add_library(util_boot STATIC ${SOURCE})

target_compile_options(util  PRIVATE  -DUSING_IBUS_FASTER_GET  ${COMMON_FLAGS} ${WARNING_FLAGS} )
target_compile_options(util_boot  PRIVATE   ${COMMON_FLAGS} ${WARNING_FLAGS} )

target_include_directories(util PUBLIC
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

target_include_directories(util_boot PUBLIC
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