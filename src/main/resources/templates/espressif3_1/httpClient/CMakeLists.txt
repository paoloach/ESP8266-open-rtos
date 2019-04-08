cmake_minimum_required(VERSION 3.10)
project(untitled3 )

set(SOURCE_DIR ${RTOS_DIR}/components/esp_http_client)

string(FIND ${SDKCONFIG} "CONFIG_SSL_USING_MBEDTLS" FOUND_SSL_USING_MBEDTLS)
string(FIND ${SDKCONFIG} "CONFIG_SSL_USING_WOLFSSL" FOUND_SSL_USING_WOLFSSL)

if (FOUND_SSL_USING_MBEDTLS GREATER 1 OR     FOUND_SSL_USING_WOLFSSL GREATER 1)
    file(GLOB SOURCE ${SOURCE_DIR}/*.c ${SOURCE_DIR}/lib/*.c)

    add_library(espHttpClient STATIC ${SOURCE} ${DRIVER} )
    target_compile_options(espHttpClient  PRIVATE   ${COMMON_FLAGS} ${WARNING_FLAGS} )
    target_include_directories(espHttpClient PRIVATE ${SOURCE_DIR}/lib/include)
    target_include_directories(espHttpClient PUBLIC ${COMMON_INCLUDE} ${SOURCE_DIR}/include )

else(FOUND_SSL_USING_MBEDTLS GREATER 1 || FOUND_SSL_USING_WOLFSSL GREATER 1)
    add_library(espHttpClient INTERFACE)
endif()