cmake_minimum_required(VERSION 3.10)
project(__{project_name}__)

set(CMAKE_CXX_STANDARD 11)

# Flash size in megabits
# Valid values are same as for esptool.py - 2,4,8,16,32
SET(FLASH_SIZE __{FLASH_SIZE}__)

# Flash mode, valid values are same as for esptool.py - qio,qout,dio.dout
SET(FLASH_MODE __{FLASH_MODE}__)

# Flash speed in MHz, valid values are same as for esptool.py - 80, 40, 26, 20
set(FLASH_SPEED __{FLASH_SPEED}__)

set(ESPPORT __{ESP_PORT}__ )

# set this to 0 if you don't need floating point support in printf/scanf
# this will save approx 14.5KB flash space and 448 bytes of statically allocated
# data RAM
#
# NB: Setting the value to 0 requires a recent esptool.py (Feb 2016 / commit ebf02c9)
set(PRINTF_SCANF_FLOAT_SUPPORT, __{FLOAT_SUPPORT}__ )



SET(COMMON_FLAGS "-mlongcalls -mtext-section-literals -DGITSHORTREV=\"93d43d7\" -nostdlib  -ffunction-sections -fdata-sections -DLWIP_MDNS_RESPONDER=1 -DLWIP_NUM_NETIF_CLIENT_DATA=1 -DLWIP_NETIF_EXT_STATUS_CALLBACK=1")

SET(CMAKE_CXX_FLAGS "${COMMON_FLAGS} -std=c++11")
SET(CMAKE_C_FLAGS "${COMMON_FLAGS} -std=gnu99")


set(PROGRAM_SRC mainUser.c)


set(ESP_OPEN_RTOS_DIR /home/paolo/esp2866/esp-open-rtos)
file(GLOB LWIP_SRC ${ESP_OPEN_RTOS_DIR}/lwip)
set(LWIP_DIR ${ESP_OPEN_RTOS_DIR}/lwip/lwip/src/)
set(LWIP_INCLUDE ${LWIP_DIR}/include ${ESP_OPEN_RTOS_DIR}/lwip/include)
set(FREE_RTOS ${ESP_OPEN_RTOS_DIR}/FreeRTOS/Source)
set(OPEN_ESPLISBS  ${ESP_OPEN_RTOS_DIR}/open_esplibs)

file(GLOB LIB_MAIN_SRC ${ESP_OPEN_RTOS_DIR}/open_esplibs/libmain/*.c ${ESP_OPEN_RTOS_DIR}/open_esplibs/libmain/*.S)
file(GLOB LIB_MAIN_ASM ${ESP_OPEN_RTOS_DIR}/open_esplibs/libmain/*.S)
SET_SOURCE_FILES_PROPERTIES( ${LIB_MAIN_ASM} PROPERTIES LANGUAGE C )


file(GLOB LIB_NET_SRC ${ESP_OPEN_RTOS_DIR}/open_esplibs/libnet80211/*.c )
file(GLOB LIB_PHY_SRC ${ESP_OPEN_RTOS_DIR}/open_esplibs/libphy/*.c )
file(GLOB LIB_WPA_SRC ${ESP_OPEN_RTOS_DIR}/open_esplibs/libwpa/*.c )
file(GLOB LIB_PP_SRC ${ESP_OPEN_RTOS_DIR}/open_esplibs/libpp/*.c )
file(GLOB FREE_RTOS_SRC ${ESP_OPEN_RTOS_DIR}/FreeRTOS/Source/*.c  )
file(GLOB LWIP_SRC
        ${ESP_OPEN_RTOS_DIR}/lwip/lwip/src/api/*.c
        ${ESP_OPEN_RTOS_DIR}/lwip/lwip/src/core/*.c
        ${ESP_OPEN_RTOS_DIR}/lwip/lwip/src/core/ipv4/*.c
        ${ESP_OPEN_RTOS_DIR}/lwip/lwip/src/netif/*.c
        ${ESP_OPEN_RTOS_DIR}/lwip/lwip/src/apps/snmp/*.c
        ${ESP_OPEN_RTOS_DIR}/lwip/lwip/src/apps/http/*.c
        ${ESP_OPEN_RTOS_DIR}/lwip/lwip/src/apps/altcp_tls/*.c
        )
file(GLOB CORE_SRC ${ESP_OPEN_RTOS_DIR}/core/*.c  ${ESP_OPEN_RTOS_DIR}/core/*.cpp)

file(GLOB CORE_ASM  ${ESP_OPEN_RTOS_DIR}/core/*.S )
SET_SOURCE_FILES_PROPERTIES( ${CORE_ASM} PROPERTIES LANGUAGE C )
# Extras


__{EXTRAS_SOURCE_FILES}__


add_library(core STATIC ${CORE_SRC} ${CORE_ASM})
add_library(program STATIC ${PROGRAM_SRC} )
add_library(open_esplibs_libmain STATIC ${LIB_MAIN_SRC}  ${LIB_MAIN_ASM})

add_library(open_esplibs_net STATIC ${LIB_NET_SRC})
add_library(open_esplibs_phy STATIC ${LIB_PHY_SRC})
add_library(open_esplibs_pp STATIC ${LIB_PP_SRC})
add_library(open_esplibs_wpa STATIC ${LIB_WPA_SRC})
add_library(freeRTOS STATIC ${FREE_RTOS_SRC} ${ESP_OPEN_RTOS_DIR}/FreeRTOS/Source/portable/esp8266/port.c)
add_library(lwip STATIC ${LWIP_SRC}  ${ESP_OPEN_RTOS_DIR}/lwip/lwip/src/apps/lwiperf/lwiperf.c
        ${ESP_OPEN_RTOS_DIR}/lwip/lwip/src/apps/sntp/sntp.c
        ${ESP_OPEN_RTOS_DIR}/lwip/lwip/src/apps/netbiosns/netbiosns.c
        ${ESP_OPEN_RTOS_DIR}/lwip/lwip/src/apps/smtp/smtp.c
        ${ESP_OPEN_RTOS_DIR}/lwip/lwip/src/apps/mdns/mdns.c
        ${ESP_OPEN_RTOS_DIR}/lwip/lwip/src/apps/mqtt/mqtt.c
        ${ESP_OPEN_RTOS_DIR}/lwip/lwip/src/apps/tftp/tftp_server.c
        ${ESP_OPEN_RTOS_DIR}/lwip/sys_arch.c
        ${ESP_OPEN_RTOS_DIR}/lwip/esp_interface.c)


__{EXTRAS_LIBRARIES}__

add_executable(__{project_name}__ FreeRTOSConfig.h )

SET(CMAKE_EXE_LINKER_FLAGS "-Wl,--no-check-sections  -u call_user_start -Wl,-static -Wl,-Map=./drindrin.map  -Wl,-gc-sections -T${ESP_OPEN_RTOS_DIR}/ld/program.ld -T${ESP_OPEN_RTOS_DIR}/ld/rom.ld -Wl,--whole-archive" )

if (PRINTF_SCANF_FLOAT_SUPPORT)
    SET(CMAKE_EXE_LINKER_FLAGS, "-u _printf_float -u _scanf_float")
endif(PRINTF_SCANF_FLOAT_SUPPORT)

file(MAKE_DIRECTORY ${CMAKE_CURRENT_BINARY_DIR}/sdklib)

add_custom_target(remove)

add_custom_command(TARGET remove
        POST_BUILD
        COMMENT "Remove phase"
        COMMAND grep -v "^#" ${ESP_OPEN_RTOS_DIR}/libc/libc.remove | cat > libc.remove
        COMMAND cat ${ESP_OPEN_RTOS_DIR}/libc/xtensa-lx106-elf/lib/libc.a > libc.a
        COMMAND ${CMAKE_AR} d libc.a @libc.remove

        COMMAND grep -v "^#" ${ESP_OPEN_RTOS_DIR}/lib/libgcc.remove | cat > libgcc.remove
        COMMAND cat ${ESP_OPEN_RTOS_DIR}/lib/libgcc.a > libgcc.a
        COMMAND ${CMAKE_AR} d libgcc.a @libgcc.remove

        COMMAND grep -v "^#" ${ESP_OPEN_RTOS_DIR}/lib/libmain.remove | cat > sdklib/libmain.remove
        COMMAND cat ${ESP_OPEN_RTOS_DIR}/lib/libmain.a > sdklib/libmain_stage1.a
        COMMAND ${CMAKE_AR} d sdklib/libmain_stage1.a @sdklib/libmain.remove

        COMMAND grep -v "^#" ${ESP_OPEN_RTOS_DIR}/lib/libnet80211.remove | cat > sdklib/libnet80211.remove
        COMMAND cat ${ESP_OPEN_RTOS_DIR}/lib/libnet80211.a > sdklib/libnet80211_stage1.a
        COMMAND ${CMAKE_AR} d sdklib/libnet80211_stage1.a @sdklib/libnet80211.remove

        COMMAND grep -v "^#" ${ESP_OPEN_RTOS_DIR}/lib/libphy.remove | cat > sdklib/libphy.remove
        COMMAND cat ${ESP_OPEN_RTOS_DIR}/lib/libphy.a > sdklib/libphy_stage1.a
        COMMAND ${CMAKE_AR} d sdklib/libphy_stage1.a @sdklib/libphy.remove

        COMMAND grep -v "^#" ${ESP_OPEN_RTOS_DIR}/lib/libpp.remove | cat > sdklib/libpp.remove
        COMMAND cat ${ESP_OPEN_RTOS_DIR}/lib/libpp.a > sdklib/libpp_stage1.a
        COMMAND ${CMAKE_AR} d sdklib/libpp_stage1.a @sdklib/libpp.remove

        COMMAND grep -v "^#" ${ESP_OPEN_RTOS_DIR}/lib/libwpa.remove | cat > sdklib/libwpa.remove
        COMMAND cat ${ESP_OPEN_RTOS_DIR}/lib/libwpa.a > sdklib/libwpa_stage1.a
        COMMAND ${CMAKE_AR} d sdklib/libwpa_stage1.a @sdklib/libwpa.remove
        )

add_custom_target(rename)
add_custom_command(TARGET rename
        POST_BUILD
        COMMENT "Rename phase"
        COMMAND ${CMAKE_OBJCOPY} --redefine-syms ${ESP_OPEN_RTOS_DIR}/lib/allsymbols.rename --weaken sdklib/libmain_stage1.a sdklib/libmain.a
        COMMAND ${CMAKE_OBJCOPY} --redefine-syms ${ESP_OPEN_RTOS_DIR}/lib/allsymbols.rename --weaken sdklib/libnet80211_stage1.a sdklib/libnet80211.a
        COMMAND ${CMAKE_OBJCOPY} --redefine-syms ${ESP_OPEN_RTOS_DIR}/lib/allsymbols.rename --weaken sdklib/libphy_stage1.a sdklib/libphy.a
        COMMAND ${CMAKE_OBJCOPY} --redefine-syms ${ESP_OPEN_RTOS_DIR}/lib/allsymbols.rename --weaken sdklib/libpp_stage1.a sdklib/libpp.a
        COMMAND ${CMAKE_OBJCOPY} --redefine-syms ${ESP_OPEN_RTOS_DIR}/lib/allsymbols.rename --weaken sdklib/libwpa_stage1.a sdklib/libwpa.a
        )

add_custom_target(createImage)
add_custom_command(TARGET createImage
        POST_BUILD
        COMMENT "create image for ESP2866"
        COMMAND esptool.py elf2image --version=2 -fs ${FLASH_SIZE} -fm ${FLASH_MODE} -ff ${FLASH_SPEED}m __{project_name}__ -o __{project_name}__.bin
        )

add_custom_target(flash)
add_custom_command(TARGET flash
        POST_BUILD
        COMMENT "Flash esp2866"
        COMMAND esptool.py -p ${ESPPORT} --baud 115200 write_flash -fs ${FLASH_SIZE} -fm ${FLASH_MODE} -ff ${FLASH_SPEED}m  0x0  ${ESP_OPEN_RTOS_DIR}/bootloader/__{project_name}___prebuilt/rboot.bin 0x1000  ${ESP_OPEN_RTOS_DIR}/bootloader/__{project_name}___prebuilt/blank_config.bin 0x2000 __{project_name}__.bin
        )

add_dependencies(rename remove)
add_dependencies(__{project_name}__ rename)
add_dependencies(createImage __{project_name}__)
add_dependencies(flash createImage)

target_link_libraries(__{project_name}__
        -Wl,--start-group
        ${CMAKE_CURRENT_BINARY_DIR}/sdklib/libmain.a
        ${CMAKE_CURRENT_BINARY_DIR}/sdklib/libnet80211.a
        ${CMAKE_CURRENT_BINARY_DIR}/sdklib/libphy.a
        ${CMAKE_CURRENT_BINARY_DIR}/sdklib/libpp.a
        ${CMAKE_CURRENT_BINARY_DIR}/sdklib/libwpa.a
        -Wl,--no-whole-archive
        program
        __{EXTRAS_STATIC_LIBRARIES}__
        freeRTOS
        lwip
        core
        ${CMAKE_CURRENT_BINARY_DIR}/libgcc.a
        ${CMAKE_CURRENT_BINARY_DIR}/libc.a
        hal
        open_esplibs_libmain
        open_esplibs_net
        open_esplibs_phy
        open_esplibs_pp
        open_esplibs_wpa
        -Wl,--end-group)



target_include_directories(open_esplibs_libmain PUBLIC
        ./
        ./include
        ${ESP_OPEN_RTOS_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/libc/xtensa-lx106-elf/include
        ${FREE_RTOS}/include
        ${FREE_RTOS}/portable/esp8266
        ${LWIP_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/lwip/include
        ${LWIP_DIR}/include/compat/posix
        ${LWIP_DIR}/include/ipv4
        ${LWIP_DIR}/include/ipv4/lwip
        ${LWIP_DIR}/include/lwip
        ${ESP_OPEN_RTOS_DIR}/core/include
        ${ESP_OPEN_RTOS_DIR}/open_esplibs/include
        )

target_include_directories(open_esplibs_net PUBLIC
        ./
        ./include
        ${ESP_OPEN_RTOS_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/libc/xtensa-lx106-elf/include
        ${FREE_RTOS}/include
        ${FREE_RTOS}/portable/esp8266
        ${LWIP_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/lwip/include
        ${LWIP_DIR}/include/compat/posix
        ${LWIP_DIR}/include/ipv4
        ${LWIP_DIR}/include/ipv4/lwip
        ${LWIP_DIR}/include/lwip
        ${ESP_OPEN_RTOS_DIR}/core/include
        ${ESP_OPEN_RTOS_DIR}/open_esplibs/include
        )


target_include_directories(open_esplibs_pp PUBLIC
        ./
        ./include
        ${ESP_OPEN_RTOS_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/libc/xtensa-lx106-elf/include
        ${FREE_RTOS}/include
        ${FREE_RTOS}/portable/esp8266
        ${LWIP_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/lwip/include
        ${LWIP_DIR}/include/compat/posix
        ${LWIP_DIR}/include/ipv4
        ${LWIP_DIR}/include/ipv4/lwip
        ${LWIP_DIR}/include/lwip
        ${ESP_OPEN_RTOS_DIR}/core/include
        ${ESP_OPEN_RTOS_DIR}/open_esplibs/include
        )

target_include_directories(open_esplibs_phy PUBLIC
        ./
        ./include
        ${ESP_OPEN_RTOS_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/libc/xtensa-lx106-elf/include
        ${FREE_RTOS}/include
        ${FREE_RTOS}/portable/esp8266
        ${LWIP_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/lwip/include
        ${LWIP_DIR}/include/compat/posix
        ${LWIP_DIR}/include/ipv4
        ${LWIP_DIR}/include/ipv4/lwip
        ${LWIP_DIR}/include/lwip
        ${ESP_OPEN_RTOS_DIR}/core/include
        ${ESP_OPEN_RTOS_DIR}/open_esplibs/include
        )

target_include_directories(open_esplibs_wpa PUBLIC
        ./
        ./include
        ${ESP_OPEN_RTOS_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/libc/xtensa-lx106-elf/include
        ${FREE_RTOS}/include
        ${FREE_RTOS}/portable/esp8266
        ${LWIP_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/lwip/include
        ${LWIP_DIR}/include/compat/posix
        ${LWIP_DIR}/include/ipv4
        ${LWIP_DIR}/include/ipv4/lwip
        ${LWIP_DIR}/include/lwip
        ${ESP_OPEN_RTOS_DIR}/core/include
        ${ESP_OPEN_RTOS_DIR}/open_esplibs/include
        )

target_include_directories(freeRTOS PUBLIC
        ./
        ./include
        ${ESP_OPEN_RTOS_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/libc/xtensa-lx106-elf/include
        ${FREE_RTOS}/include
        ${FREE_RTOS}/portable/esp8266
        ${LWIP_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/lwip/include
        ${LWIP_DIR}/include/compat/posix
        ${LWIP_DIR}/include/ipv4
        ${LWIP_DIR}/include/ipv4/lwip
        ${LWIP_DIR}/include/lwip
        ${ESP_OPEN_RTOS_DIR}/core/include
        ${ESP_OPEN_RTOS_DIR}/open_esplibs/include
        )

target_include_directories(lwip PUBLIC
        ./
        ./include
        ${ESP_OPEN_RTOS_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/libc/xtensa-lx106-elf/include
        ${FREE_RTOS}/include
        ${FREE_RTOS}/portable/esp8266
        ${LWIP_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/lwip/include
        ${LWIP_DIR}/include/compat/posix
        ${LWIP_DIR}/include/ipv4
        ${LWIP_DIR}/include/ipv4/lwip
        ${LWIP_DIR}/include/lwip
        ${ESP_OPEN_RTOS_DIR}/core/include
        ${ESP_OPEN_RTOS_DIR}/open_esplibs/include
        )

__{EXTRA_INCLUDE_DIRECTORIES}__

target_include_directories(core PUBLIC
        ./
        ./include
        ${ESP_OPEN_RTOS_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/libc/xtensa-lx106-elf/include
        ${FREE_RTOS}/include
        ${FREE_RTOS}/portable/esp8266
        ${LWIP_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/lwip/include
        ${LWIP_DIR}/include/compat/posix
        ${LWIP_DIR}/include/ipv4
        ${LWIP_DIR}/include/ipv4/lwip
        ${LWIP_DIR}/include/lwip
        ${ESP_OPEN_RTOS_DIR}/core/include
        ${ESP_OPEN_RTOS_DIR}/open_esplibs/include
        )

target_include_directories(program PUBLIC
        ./
        ./include
        ${ESP_OPEN_RTOS_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/libc/xtensa-lx106-elf/include
        ${FREE_RTOS}/include
        ${FREE_RTOS}/portable/esp8266
        ${LWIP_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/lwip/include
        ${LWIP_DIR}/include/compat/posix
        ${LWIP_DIR}/include/ipv4
        ${LWIP_DIR}/include/ipv4/lwip
        ${LWIP_DIR}/include/lwip
        ${ESP_OPEN_RTOS_DIR}/core/include
        ${ESP_OPEN_RTOS_DIR}/open_esplibs/include
        )


target_include_directories(__{project_name}__ PUBLIC
        ./
        ./include
        ${ESP_OPEN_RTOS_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/libc/xtensa-lx106-elf/include
        ${FREE_RTOS}/include
        ${FREE_RTOS}/portable/esp8266
        ${LWIP_DIR}/include
        ${ESP_OPEN_RTOS_DIR}/lwip/include
        ${LWIP_DIR}/include/compat/posix
        ${LWIP_DIR}/include/ipv4
        ${LWIP_DIR}/include/ipv4/lwip
        ${LWIP_DIR}/include/lwip
        ${ESP_OPEN_RTOS_DIR}/core/include
        ${ESP_OPEN_RTOS_DIR}/open_esplibs/include
        )