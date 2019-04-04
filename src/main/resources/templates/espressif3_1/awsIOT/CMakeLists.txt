cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ )

set(SOURCE_DIR ${RTOS_DIR}/components/aws_iot)

file(READ ../include/sdkconfig.h SDKCONFIG)
string(FIND ${SDKCONFIG} "AWS_IOT_SDK" FOUND)
if (FOUND GREATER 1)
    add_library(awsIOT STATIC
     ${SOURCE_DIR}/aws-iot-device-sdk-embedded-C/src/aws_iot_mqtt_client_yield.c
     ${SOURCE_DIR}/aws-iot-device-sdk-embedded-C/src/aws_iot_mqtt_client_common_internal.c
     ${SOURCE_DIR}/aws-iot-device-sdk-embedded-C/src/aws_iot_mqtt_client_subscribe.c
     ${SOURCE_DIR}/aws-iot-device-sdk-embedded-C/src/aws_iot_json_utils.c
     ${SOURCE_DIR}/aws-iot-device-sdk-embedded-C/src/aws_iot_shadow.c
     ${SOURCE_DIR}/aws-iot-device-sdk-embedded-C/src/aws_iot_mqtt_client_unsubscribe.c
     ${SOURCE_DIR}/aws-iot-device-sdk-embedded-C/src/aws_iot_shadow_records.c
     ${SOURCE_DIR}/aws-iot-device-sdk-embedded-C/src/aws_iot_mqtt_client_connect.c
     ${SOURCE_DIR}/aws-iot-device-sdk-embedded-C/src/aws_iot_shadow_json.c
     ${SOURCE_DIR}/aws-iot-device-sdk-embedded-C/src/aws_iot_mqtt_client.c
     ${SOURCE_DIR}/aws-iot-device-sdk-embedded-C/src/aws_iot_mqtt_client_publish.c
     ${SOURCE_DIR}/aws-iot-device-sdk-embedded-C/src/aws_iot_shadow_actions.c
     ${SOURCE_DIR}/port/network_mbedtls_wrapper.c
     ${SOURCE_DIR}/port/threads_freertos.c
     ${SOURCE_DIR}/port/timer.c)
    target_compile_options(awsIOT PRIVATE ${COMMON_FLAGS} ${WARNING_FLAGS})
    target_include_directories(awsIOT PUBLIC
        ${CMAKE_SOURCE_DIR}/include
        ${SOURCE_DIR}/include
        ${SOURCE_DIR}/aws-iot-device-sdk-embedded-C/include
        ${RTOS_DIR}/components/esp-tls
        ${RTOS_DIR}/components/heap/include/
        ${RTOS_DIR}/components/heap/port/esp8266/include/
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/log/include
        ${RTOS_DIR}/components/bootloader_support/include
        ${RTOS_DIR}/components/cjson/include
        ${RTOS_DIR}/components/cjson/cJSON
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/espos/include
        ${RTOS_DIR}/components/freertos/include
        ${RTOS_DIR}/components/freertos/include/freertos
        ${RTOS_DIR}/components/freertos/include/freertos/private
        ${RTOS_DIR}/components/freertos/port/esp8266/include
        ${RTOS_DIR}/components/freertos/port/esp8266/include/freertos
        ${RTOS_DIR}/components/jsmn/include
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
        ${RTOS_DIR}/components/util/include
        ${SOURCE_DIR}
        )

else(FOUND GREATER 1)
    add_library(awsIOT INTERFACE )
endif()
