cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ )

set(SOURCE_DIR ${RTOS_DIR}/components/wpa_supplicant)

add_library(wpaSupplicant  STATIC
    ${SOURCE_DIR}/src/crypto/aes-cbc.c
    ${SOURCE_DIR}/src/crypto/aes-internal-dec.c
    ${SOURCE_DIR}/src/crypto/aes-internal.c
    ${SOURCE_DIR}/src/crypto/aes-internal-enc.c
    ${SOURCE_DIR}/src/crypto/aes-wrap.c
    ${SOURCE_DIR}/src/crypto/bignum.h
    ${SOURCE_DIR}/src/crypto/dh_group5.c
    ${SOURCE_DIR}/src/crypto/libtommath.h
    ${SOURCE_DIR}/src/crypto/md5-internal.c
    ${SOURCE_DIR}/src/crypto/sha1.c
    ${SOURCE_DIR}/src/crypto/sha1-pbkdf2.c
    ${SOURCE_DIR}/src/crypto/sha256-internal.c
    ${SOURCE_DIR}/src/crypto/aes-unwrap.c
    ${SOURCE_DIR}/src/crypto/bignum.c
    ${SOURCE_DIR}/src/crypto/crypto_internal-modexp.c
    ${SOURCE_DIR}/src/crypto/dh_groups.c
    ${SOURCE_DIR}/src/crypto/md5.c
    ${SOURCE_DIR}/src/crypto/rc4.c
    ${SOURCE_DIR}/src/crypto/sha1-internal.c
    ${SOURCE_DIR}/src/crypto/sha256.c
    ${SOURCE_DIR}/port/os_xtensa.c
    )


target_compile_options(wpaSupplicant  PRIVATE  -DEMBEDDED_SUPP -D__ets__ -DESPRESSIF_USE ${COMMON_FLAGS} ${WARNING_FLAGS})


target_include_directories(wpaSupplicant PUBLIC
    ${SOURCE_DIR}/include
    ${SOURCE_DIR}/port/include
    ${RTOS_DIR}/components/heap/include
    ${RTOS_DIR}/components/heap/port/esp8266/include
    ${RTOS_DIR}/components/lwip/include
    ${RTOS_DIR}/components/lwip/include/lwip/apps
    ${RTOS_DIR}/components/lwip/lwip/src/include
    ${RTOS_DIR}/components/lwip/port/esp8266/include
    ${RTOS_DIR}/components/lwip/lwip/src/include/posix
    ${RTOS_DIR}/components/bootloader_support/include
    ${RTOS_DIR}/components/cjson/include
    ${RTOS_DIR}/components/cjson/cJSON
    ${RTOS_DIR}/components/esp8266/include
    ${RTOS_DIR}/components/espos/include
    ${RTOS_DIR}/components/freertos/include
    ${RTOS_DIR}/components/freertos/include/freertos
    ${RTOS_DIR}/components/freertos/include/freertos/private
    ${RTOS_DIR}/components/freertos/port/esp8266/include
    ${RTOS_DIR}/components/freertos/port/esp8266/include/freertos
    ${RTOS_DIR}/components/jsmn/include
    ${RTOS_DIR}/components/log/include
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
    ${RTOS_DIR}/components/util/include
    ${SOURCE_DIR}/port/esp8266/netif
    ${CMAKE_SOURCE_DIR}/include
    )