cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ )

set(BOOT_DIR ${RTOS_DIR}/components/bootloader/subproject/main)

set(BOOT_SRC ${BOOT_DIR}/bootloader_start.c)

add_executable(bootloader ${BOOT_SRC})
set_target_properties(bootloader
        PROPERTIES SUFFIX ".elf")
add_dependencies(bootloader
        bootloaderSupport_boot
        log_boot
        esp8266_boot
        util)


add_custom_target(bootloader.bin
        COMMAND python ${RTOS_DIR}/components/esptool_py/esptool/esptool.py
        --chip esp8266
        elf2image
        --flash_mode __{flash_mode}__
        --flash_freq __{flash_freq}__
        --flash_size __{flash_size}__
        -o ${CMAKE_CURRENT_BINARY_DIR}/bootloader
        ${CMAKE_CURRENT_BINARY_DIR}/bootloader.elf
        COMMENT "Create bootloader")

add_custom_target(partitions_singleapp.bin
        COMMAND python ${RTOS_DIR}/components/partition_table/gen_esp32part.py
        -q ${RTOS_DIR}/components/partition_table/partitions_singleapp.csv
        ${CMAKE_CURRENT_BINARY_DIR}/partitions_singleapp.bin
        COMMENT "Create partition map")


add_dependencies(bootloader.bin bootloader)



set_target_properties(bootloader PROPERTIES
        LINK_FLAGS "-nostdlib\
                    -u call_user_start_cpu0\
                    -Wl,--gc-sections\
                    -Wl,-static\
                    -T esp8266.bootloader.ld\
                    -T ${RTOS_DIR}/components/esp8266/ld/esp8266.rom.ld\
                    -T esp8266.bootloader.rom.ld\
                    -Wl,-EL\
                    -Wl,-Map=bootloader.map\
                    -L ${RTOS_DIR}/components/bootloader/subproject/main")

target_link_libraries(bootloader
        -Wl,--start-group
        bootloaderSupport_boot
        log_boot
        esp8266_boot
        util
        gcc
        stdc++
        gcov
        -Wl,--end-group)

target_compile_options(bootloader  PRIVATE -DMEMLEAK_DEBUG -DBOOTLOADER_BUILD=1 ${COMMON_FLAGS} ${WARNING_FLAGS} )
target_include_directories(bootloader PUBLIC
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
          )



