cmake_minimum_required(VERSION 3.10)
project(untitled1 )

set(SOURCE_DIR ${RTOS_DIR}/components/esp_ringbuf)

file(GLOB SOURCE ${SOURCE_DIR}/*.c)

add_library(espRingbuf STATIC ${SOURCE})

target_compile_options(espRingbuf PRIVATE ${COMMON_FLAGS} ${WARNING_FLAGS} )

target_include_directories(espRingbuf PUBLIC
        ${COMMON_INCLUDE}
        ${SOURCE_DIR}/include
        ${SOURCE_DIR}/include/freertos
        )