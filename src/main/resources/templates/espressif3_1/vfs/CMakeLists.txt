cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ )

set(SOURCE_DIR ${RTOS_DIR}/components/vfs)

string(FIND ${SDKCONFIG} "CONFIG_USING_ESP_VFS" FOUND_CONFIG_USING_ESP_VFS)
if (FOUND_CONFIG_USING_ESP_VFS GREATER 1)

    file(GLOB SOURCE ${SOURCE_DIR}/*.c)

    add_library(vfs STATIC ${SOURCE})

    target_compile_options(vfs  PRIVATE ${COMMON_FLAGS} ${WARNING_FLAGS} )

    target_include_directories(vfs PUBLIC
            ${COMMON_INCLUDE}
            ${SOURCE_DIR}
            ${SOURCE_DIR}/include
            )
else(FOUND_CONFIG_USING_ESP_VFS GREATER 1)
    add_library(vfs INTERFACE )
endif()
