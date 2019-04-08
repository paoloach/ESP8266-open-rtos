cmake_minimum_required(VERSION 3.10)
project(untitled3 )

set(SOURCE_DIR ${RTOS_DIR}/components/esp_http_server)

file(GLOB SOURCE ${SOURCE_DIR}/src/*.c ${SOURCE_DIR}/src/util/*.c)

add_library(espHttpServer STATIC ${SOURCE} )
target_compile_options(espHttpServer  PRIVATE   ${COMMON_FLAGS} ${WARNING_FLAGS} )

target_include_directories(espHttpServer PRIVATE ${SOURCE_DIR}/src/port/esp8266 ${SOURCE_DIR}/src/util)

target_include_directories(espHttpServer PUBLIC ${COMMON_INCLUDE} ${SOURCE_DIR}/include )
