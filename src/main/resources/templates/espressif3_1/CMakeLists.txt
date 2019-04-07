cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ C CXX ASM)

set(CMAKE_C_FLAGS_RELEASE "-Og" )
set(CMAKE_CXX_FLAGS_RELEASE "-Og" )
set(CMAKE_C_FLAGS_DEBUG "-O1" )
set(CMAKE_CXX_FLAGS_DEBUG "-O1" )

__{ESPRESSIF_RTOS_DIR}__
execute_process(COMMAND git describe --always --tags --dirty
        WORKING_DIRECTORY ${RTOS_DIR}
        OUTPUT_VARIABLE IDF_VER
        OUTPUT_STRIP_TRAILING_WHITESPACE)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_C_STANDARD 99)
set(COMMON_FLAGS  -ggdb -ffunction-sections -fdata-sections -fstrict-volatile-bitfields -mlongcalls -nostdlib __{CONFIG_FLAS}__ -D__ESP_FILE__=__FILE__  -DMBEDTLS_CONFIG_FILE=\"mbedtls/esp_config.h\" -DICACHE_FLASH -DESP_PLATFORM -DIDF_VER=\"${IDF_VER}\" -MMD -MP   -DWITH_POSIX  )
set(WARNING_FLAGS -Wall -Werror=all -Wno-error=unused-function -Wno-error=unused-but-set-variable -Wno-error=unused-variable -Wno-error=deprecated-declarations -Wextra -Wno-unused-parameter -Wno-sign-compare -Wno-error=char-subscripts -Wno-error=unknown-pragmas -Wno-error=implicit-function-declaration -Wno-error=pointer-sign -Wno-error=switch -Wno-error=maybe-uninitialized -Wno-error=format= -Wno-error=unused-value -Wno-error=address -Wno-error=return-type -Wno-error=format-extra-args -Wno-error=format-zero-length -Wno-error=unused-label -Wno-error=sizeof-pointer-memaccess)

file(READ include/sdkconfig.h SDKCONFIG)
string(FIND ${SDKCONFIG} "CONFIG_BOOTLOADER_CHECK_APP_SUM" FOUND_CHECK_APP_SUM)
if (FOUND_CHECK_APP_SUM GREATER 1)
    list(APPEND COMMON_FLAGS -DCONFIG_ENABLE_BOOT_CHECK_SUM=1 )
endif()
string(FIND ${SDKCONFIG} "CONFIG_BOOTLOADER_CHECK_APP_HASH" FOUND_CHECK_APP_HASH)
if (FOUND_CHECK_APP_HASH GREATER 1)
    list(APPEND COMMON_FLAGS -DCONFIG_ENABLE_BOOT_CHECK_SHA256=1 )
endif()


set(FLASH_SIZE __{flash_size_hex}__)
set(SPI_FLASG_MODE __{flash_SPI_MODE}__)


add_subdirectory(bootloader)
add_subdirectory(appUpdate)
add_subdirectory(awsIOT)
add_subdirectory(bootloaderSupport)
add_subdirectory(coap)
add_subdirectory(log)
add_subdirectory(esp8266)
add_subdirectory(util)
add_subdirectory(cjson)
add_subdirectory(espOS)
add_subdirectory(espTls)
add_subdirectory(heap)
add_subdirectory(freeRTOS)
add_subdirectory(jsmn)
add_subdirectory(lwip)
add_subdirectory(mqtt)
add_subdirectory(mdns)
add_subdirectory(newlib)
add_subdirectory(nvs_flash)
add_subdirectory(smartConfig)
add_subdirectory(sodium)
add_subdirectory(spiFlash)
add_subdirectory(spiffs)
add_subdirectory(ssl)
add_subdirectory(tcpipAdapter)
add_subdirectory(wpaSupplicant)

set(PROGRAM_SRC main.c include/sdkconfig.h)



add_custom_target(linkerDef
        COMMAND ${CMAKE_C_COMPILER} -DAPP_OFFSET=0x10000 -DAPP_SIZE=0xF0000  -I ../include -C -P -x c -E ${RTOS_DIR}/components/esp8266/ld/esp8266.ld -o esp8266_out.ld
        COMMAND ${CMAKE_C_COMPILER} -I ../include -C -P -x c -E ${CMAKE_SOURCE_DIR}/ld/esp8266.common.ld -o esp8266_common_out.ld
        COMMENT "create linker files")

add_custom_target(image
        COMMAND ${RTOS_DIR}/components/esptool_py/esptool/esptool.py --chip esp8266 elf2image --flash_mode __{flash_mode}__ --flash_freq __{flash_freq}__ --flash_size __{flash_size}__ --version=3 -o ./firmware.bin ./firmware
        COMMENT "create image file")

add_custom_target(flash_image
        COMMAND python ${RTOS_DIR}/components/esptool_py/esptool/esptool.py
        --chip esp8266
        --port "__{esptool_port}__"
        --baud __{esptool_baudRate}__
        --before __{esptool_before}__
        --after __{esptool_after}__
        write_flash __{esptool_compression}__
        --flash_mode __{flash_mode}__
        --flash_freq __{flash_freq}__
        --flash_size __{flash_size}__
        0x0000 ${CMAKE_BINARY_DIR}/bootloader/bootloader0x00000.bin
        0x10000 ./firmware.bin
        0x8000 ${CMAKE_BINARY_DIR}/bootloader/partitions_singleapp.bin
        COMMENT "flash image")




add_executable(firmware ${PROGRAM_SRC})
add_dependencies(firmware
        bootloader
        bootloaderSupport
        appUpdate
        awsIOT
        coap
        espTls
        log
        esp8266
        util
        cjson
        jsmn
        espOS
        freeRTOS
        lwip
        mqtt
        mdns
        newlib
        nvs_flash
        smartConfig
        spiFlash
        spiffs
        ssl
        tcpipAdapter
        wpaSupplicant)

add_dependencies(image firmware linkerDef)
add_dependencies(flash_image image bootloader.bin partitions_singleapp.bin)

set_target_properties(firmware PROPERTIES
        LINK_FLAGS "-nostdlib\
                    -u call_user_start_cpu0\
                    -Wl,--gc-sections\
                    -Wl,-static\
                    -T esp8266_out.ld\
                    -T esp8266.peripherals.ld\
                    -Wl,--no-check-sections\
                    -u call_user_start\
                    -T esp8266_common_out.ld\
                    -T esp8266.rom.ld\
                    -Wl,-EL\
                    -Wl,-Map=firmware.map\
                    -L ${RTOS_DIR}/components/esp8266/ld\
                    -L ${RTOS_DIR}/components/esp8266/lib")

add_dependencies(firmware linkerDef)

target_link_libraries(firmware
        -Wl,--start-group
        appUpdate
        awsIOT
        bootloaderSupport
        cjson
        coap
        espTls
        esp8266
        espOS
        freeRTOS
        heap
        jsmn
        sodium
        log
        lwip
        mdns
        mqtt
        newlib
        nvs_flash
        smartConfig
        spiFlash
        ssl
        tcpipAdapter
        util
        wpaSupplicant
        gcc
        hal
        core
        net80211
        phy
        pp
        smartconfig
        ssc
        wpa
        espnow
        wps
        ${RTOS_DIR}/components/newlib/newlib/lib/libc_nano.a
        ${RTOS_DIR}/components/newlib/newlib/lib/libm.a
        stdc++
        gcov
        -Wl,--end-group)


target_compile_options(firmware PUBLIC  -DICACHE_FLASH ${COMMON_FLAGS})
target_include_directories(firmware PUBLIC
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
        )