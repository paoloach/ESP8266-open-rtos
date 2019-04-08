cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ )

set(SOURCE_DIR ${RTOS_DIR}/components/esp_ringbuf)

file(GLOB SOURCE ${SOURCE_DIR}/*.c)

add_library(espRingbuf STATIC ${SOURCE})

target_include_directories(espRingbuf PUBLIC
        ${COMMON_INCLUDE}
        ${SOURCE_DIR}/include
        ${SOURCE_DIR}/include/freertos
        )