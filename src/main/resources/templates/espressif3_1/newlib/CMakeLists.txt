cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ )

set(SOURCE_DIR ${RTOS_DIR}/components/newlib)

string(FIND ${SDKCONFIG} "CONFIG_NEWLIB_ENABLE" FOUND_CONFIG_NEWLIB_ENABLE)
if (FOUND_CONFIG_NEWLIB_ENABLE GREATER 1)

    file(GLOB SOURCE ${SOURCE_DIR}/newlib/port/*.c)
    add_library(newlib STATIC ${SOURCE} )

    target_compile_options(newlib  PRIVATE  ${COMMON_FLAGS} ${WARNING_FLAGS} )
    target_include_directories(newlib PUBLIC
            ${COMMON_INCLUDE}
            ${SOURCE_DIR}/newlib/port/include
            ${SOURCE_DIR}/newlib/include
            )
else(FOUND_CONFIG_NEWLIB_ENABLE GREATER 1)
   add_library(newlib INTERFACE )
endif()