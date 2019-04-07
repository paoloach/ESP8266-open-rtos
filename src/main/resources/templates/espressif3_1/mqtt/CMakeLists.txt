cmake_minimum_required(VERSION 3.10)
project(untitled3 )

set(SOURCE_DIR ${RTOS_DIR}/components/mqtt)

string(FIND ${SDKCONFIG} "CONFIG_MQTT_USING_IBM" FOUND_USING_IBM)
if(FOUND_USING_IBM GREATER 1)
    SET(private_include ${SOURCE_DIR}/ibm-mqtt/MQTTClient-C/src ${SOURCE_DIR}/ibm-mqtt/MQTTClient-C/src/FreeRTOS ${SOURCE_DIR}/ibm-mqtt/MQTTPacket/src)
    file(GLOB SOURCE
            ${SOURCE_DIR}/ibm-mqtt/MQTTClient-C/src/*.c
            ${SOURCE_DIR}/ibm-mqtt/MQTTClient-C/src/FreeRTOS/*.c
            ${SOURCE_DIR}/ibm-mqtt/MQTTPacket/src/*.c)
else(FOUND_USING_IBM GREATER 1)
    string(FIND ${SDKCONFIG} "CONFIG_MQTT_USING_ESP" FOUND_USING_ESP)
    if (FOUND_USING_ESP GREATER 1)
            set(public_include  ${SOURCE_DIR}/esp-mqtt/include)
            set(private_include  ${SOURCE_DIR}/esp-mqtt/lib/include)
            file(GLOB SOURCE
                    ${SOURCE_DIR}/esp-mqtt/*.c
                    ${SOURCE_DIR}/esp-mqtt/lib/*.c
                    )
    endif()
endif()

add_library(mqtt STATIC ${SOURCE})
target_compile_options(mqtt  PRIVATE   -DICACHE_FLASH  ${COMMON_FLAGS} ${WARNING_FLAGS} )
target_include_directories(mqtt PRIVATE ${private_include} )
target_include_directories(mqtt PUBLIC
        ${COMMON_INCLUDE}
        ${RTOS_DIR}/components/tcp_transport/include
        ${RTOS_DIR}/components/http_parser/include/
        ${public_include}
        )