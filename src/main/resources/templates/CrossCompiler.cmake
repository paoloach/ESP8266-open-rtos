SET(CMAKE_SYSTEM_NAME Generic)
SET(CMAKE_SYSTEM_VERSION 1)

# specify the cross compiler
set(CMAKE_C_COMPILER __{C_COMPILER_PATH}__ -nostdlib)
set(CMAKE_CXX_COMPILER __{CXX_COMPILER_PATH}__ -nostdlib)

#SET(COMMON_FLAGS " -mlongcalls -mtext-section-literals -DGITSHORTREV=\"93d43d7\" -nostdlib  -ffunction-sections -fdata-sections -DLWIP_MDNS_RESPONDER=1 -DLWIP_NUM_NETIF_CLIENT_DATA=1 -DLWIP_NETIF_EXT_STATUS_CALLBACK=1")
SET(CMAKE_CXX_FLAGS "${COMMON_FLAGS} -std=c++11 ")
SET(CMAKE_C_FLAGS "${COMMON_FLAGS} -std=gnu99 ")
