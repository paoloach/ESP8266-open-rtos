cmake_minimum_required(VERSION 3.10)
project(__{project_name}__)

__{ESP_OPEN_RTOS_DIR}__
__{ESPTOOLS2_PATH}__
set(RBOOT_DIR ${ESP_OPEN_RTOS_DIR}/bootloader/rboot)

add_executable(stage2a ${RBOOT_DIR}/rboot-stage2a.c)

target_compile_options(stage2a PRIVATE -Os -O3 -Wpointer-arith -Wundef -Werror -Wl,-EL -fno-inline-functions -nostdlib -mlongcalls -mtext-section-literals)
target_compile_definitions(stage2a PRIVATE -D__ets__ -DICACHE_FLASH)
target_include_directories(stage2a PRIVATE ${RBOOT_DIR})
target_include_directories(stage2a PRIVATE ${RBOOT_DIR}/..)
set_target_properties(stage2a PROPERTIES
        LINK_FLAGS "-L${RBOOT_DIR} -T${RBOOT_DIR}/rboot-stage2a.ld -Wl,--no-check-sections -u call_user_start -Wl,-static "
        SUFFIX .elf
        RUNTIME_OUTPUT_DIRECTORY build)

add_custom_target(rboot_hex2a)
add_custom_command(TARGET rboot_hex2a
        POST_BUILD
        COMMENT "create rboot-hex2a.h file"
        COMMAND ${ESPTOOL2} -quiet -header build/stage2a.elf build/rboot-hex2a.h .text)
add_dependencies(rboot_hex2a stage2a)

add_executable(rboot ${RBOOT_DIR}/rboot.c)
target_compile_options(rboot PRIVATE -Os -O3 -Wpointer-arith -Wundef -Werror -Wl,-EL -fno-inline-functions -nostdlib -mlongcalls -mtext-section-literals)
target_compile_definitions(rboot PRIVATE -D__ets__ -DICACHE_FLASH)
target_include_directories(rboot PRIVATE ${RBOOT_DIR})
target_include_directories(rboot PRIVATE ${RBOOT_DIR}/..)
target_include_directories(rboot PRIVATE ${CMAKE_BINARY_DIR}/rboot/build)
set_target_properties(rboot PROPERTIES
        LINK_FLAGS "-L${RBOOT_DIR} -T${RBOOT_DIR}/eagle.app.v6.ld -Wl,--no-check-sections -u call_user_start -Wl,-static "
        SUFFIX .elf
        RUNTIME_OUTPUT_DIRECTORY build)
add_dependencies(rboot rboot_hex2a)

file(MAKE_DIRECTORY ${CMAKE_BINARY_DIR}/rboot/firmware)

add_custom_target(rboot_bin)
add_custom_command(TARGET rboot_bin
        POST_BUILD
        COMMENT "create bootloader file"
        COMMAND ${ESPTOOL2} -quiet -bin -boot0 ${CMAKE_BINARY_DIR}/rboot/build/rboot.elf ${CMAKE_BINARY_DIR}/rboot/firmware/rboot.bin .text .rodata)
add_dependencies(rboot_bin rboot)
add_dependencies(rboot_bin stage2a)
