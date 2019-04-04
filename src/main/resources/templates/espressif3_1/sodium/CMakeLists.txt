cmake_minimum_required(VERSION 3.10)
project(__{project_name}__ )

set(SOURCE_DIR ${RTOS_DIR}/components/libsodium)
set(SRC ${SOURCE_DIR}/libsodium/src/libsodium)


set(COMMON_SRC     "${SRC}/crypto_aead/chacha20poly1305/sodium/aead_chacha20poly1305.c"
                   "${SRC}/crypto_aead/xchacha20poly1305/sodium/aead_xchacha20poly1305.c"
                   "${SRC}/crypto_auth/crypto_auth.c"
                   "${SRC}/crypto_auth/hmacsha256/auth_hmacsha256.c"
                   "${SRC}/crypto_auth/hmacsha512/auth_hmacsha512.c"
                   "${SRC}/crypto_auth/hmacsha512256/auth_hmacsha512256.c"
                   "${SRC}/crypto_box/crypto_box.c"
                   "${SRC}/crypto_box/crypto_box_easy.c"
                   "${SRC}/crypto_box/crypto_box_seal.c"
                   "${SRC}/crypto_box/curve25519xchacha20poly1305/box_curve25519xchacha20poly1305.c"
                   "${SRC}/crypto_box/curve25519xsalsa20poly1305/box_curve25519xsalsa20poly1305.c"
                   "${SRC}/crypto_core/curve25519/ref10/curve25519_ref10.c"
                   "${SRC}/crypto_core/hchacha20/core_hchacha20.c"
                   "${SRC}/crypto_core/hsalsa20/core_hsalsa20.c"
                   "${SRC}/crypto_core/hsalsa20/ref2/core_hsalsa20_ref2.c"
                   "${SRC}/crypto_core/salsa/ref/core_salsa_ref.c"
                   "${SRC}/crypto_generichash/crypto_generichash.c"
                   "${SRC}/crypto_generichash/blake2b/generichash_blake2.c"
                   "${SRC}/crypto_generichash/blake2b/ref/blake2b-compress-avx2.c"
                   "${SRC}/crypto_generichash/blake2b/ref/blake2b-compress-ref.c"
                   "${SRC}/crypto_generichash/blake2b/ref/blake2b-compress-sse41.c"
                   "${SRC}/crypto_generichash/blake2b/ref/blake2b-compress-ssse3.c"
                   "${SRC}/crypto_generichash/blake2b/ref/blake2b-ref.c"
                   "${SRC}/crypto_generichash/blake2b/ref/generichash_blake2b.c"
                   "${SRC}/crypto_hash/crypto_hash.c"
                   "${SRC}/crypto_hash/sha256/hash_sha256.c"
                   "${SRC}/crypto_hash/sha256/cp/hash_sha256_cp.c"
                   "${SRC}/crypto_hash/sha512/hash_sha512.c"
                   "${SRC}/crypto_hash/sha512/cp/hash_sha512_cp.c"
                   "${SRC}/crypto_kdf/crypto_kdf.c"
                   "${SRC}/crypto_kdf/blake2b/kdf_blake2b.c"
                   "${SRC}/crypto_kx/crypto_kx.c"
                   "${SRC}/crypto_onetimeauth/crypto_onetimeauth.c"
                   "${SRC}/crypto_onetimeauth/poly1305/onetimeauth_poly1305.c"
                   "${SRC}/crypto_onetimeauth/poly1305/donna/poly1305_donna.c"
                   "${SRC}/crypto_onetimeauth/poly1305/sse2/poly1305_sse2.c"
                   "${SRC}/crypto_pwhash/crypto_pwhash.c"
                   "${SRC}/crypto_pwhash/argon2/argon2-core.c"
                   "${SRC}/crypto_pwhash/argon2/argon2-encoding.c"
                   "${SRC}/crypto_pwhash/argon2/argon2-fill-block-ref.c"
                   "${SRC}/crypto_pwhash/argon2/argon2-fill-block-ssse3.c"
                   "${SRC}/crypto_pwhash/argon2/argon2.c"
                   "${SRC}/crypto_pwhash/argon2/blake2b-long.c"
                   "${SRC}/crypto_pwhash/argon2/pwhash_argon2i.c"
                   "${SRC}/crypto_pwhash/scryptsalsa208sha256/crypto_scrypt-common.c"
                   "${SRC}/crypto_pwhash/scryptsalsa208sha256/pbkdf2-sha256.c"
                   "${SRC}/crypto_pwhash/scryptsalsa208sha256/pwhash_scryptsalsa208sha256.c"
                   "${SRC}/crypto_pwhash/scryptsalsa208sha256/scrypt_platform.c"
                   "${SRC}/crypto_pwhash/scryptsalsa208sha256/nosse/pwhash_scryptsalsa208sha256_nosse.c"
                   "${SRC}/crypto_pwhash/scryptsalsa208sha256/sse/pwhash_scryptsalsa208sha256_sse.c"
                   "${SRC}/crypto_scalarmult/crypto_scalarmult.c"
                   "${SRC}/crypto_scalarmult/curve25519/scalarmult_curve25519.c"
                   "${SRC}/crypto_scalarmult/curve25519/donna_c64/curve25519_donna_c64.c"
                   "${SRC}/crypto_scalarmult/curve25519/ref10/x25519_ref10.c"
                   "${SRC}/crypto_scalarmult/curve25519/sandy2x/curve25519_sandy2x.c"
                   "${SRC}/crypto_scalarmult/curve25519/sandy2x/fe51_invert.c"
                   "${SRC}/crypto_scalarmult/curve25519/sandy2x/fe_frombytes_sandy2x.c"
                   "${SRC}/crypto_secretbox/crypto_secretbox.c"
                   "${SRC}/crypto_secretbox/crypto_secretbox_easy.c"
                   "${SRC}/crypto_secretbox/xchacha20poly1305/secretbox_xchacha20poly1305.c"
                   "${SRC}/crypto_secretbox/xsalsa20poly1305/secretbox_xsalsa20poly1305.c"
                   "${SRC}/crypto_shorthash/crypto_shorthash.c"
                   "${SRC}/crypto_shorthash/siphash24/shorthash_siphash24.c"
                   "${SRC}/crypto_shorthash/siphash24/shorthash_siphashx24.c"
                   "${SRC}/crypto_shorthash/siphash24/ref/shorthash_siphash24_ref.c"
                   "${SRC}/crypto_shorthash/siphash24/ref/shorthash_siphashx24_ref.c"
                   "${SRC}/crypto_sign/crypto_sign.c"
                   "${SRC}/crypto_sign/ed25519/sign_ed25519.c"
                   "${SRC}/crypto_sign/ed25519/ref10/keypair.c"
                   "${SRC}/crypto_sign/ed25519/ref10/obsolete.c"
                   "${SRC}/crypto_sign/ed25519/ref10/open.c"
                   "${SRC}/crypto_sign/ed25519/ref10/sign.c"
                   "${SRC}/crypto_stream/crypto_stream.c"
                   "${SRC}/crypto_stream/aes128ctr/stream_aes128ctr.c"
                   "${SRC}/crypto_stream/aes128ctr/nacl/afternm_aes128ctr.c"
                   "${SRC}/crypto_stream/aes128ctr/nacl/beforenm_aes128ctr.c"
                   "${SRC}/crypto_stream/aes128ctr/nacl/consts_aes128ctr.c"
                   "${SRC}/crypto_stream/aes128ctr/nacl/int128_aes128ctr.c"
                   "${SRC}/crypto_stream/aes128ctr/nacl/stream_aes128ctr_nacl.c"
                   "${SRC}/crypto_stream/aes128ctr/nacl/xor_afternm_aes128ctr.c"
                   "${SRC}/crypto_stream/chacha20/stream_chacha20.c"
                   "${SRC}/crypto_stream/chacha20/dolbeau/chacha20_dolbeau-avx2.c"
                   "${SRC}/crypto_stream/chacha20/dolbeau/chacha20_dolbeau-ssse3.c"
                   "${SRC}/crypto_stream/chacha20/ref/chacha20_ref.c"
                   "${SRC}/crypto_stream/salsa20/stream_salsa20.c"
                   "${SRC}/crypto_stream/salsa20/ref/salsa20_ref.c"
                   "${SRC}/crypto_stream/salsa20/xmm6/salsa20_xmm6.c"
                   "${SRC}/crypto_stream/salsa20/xmm6int/salsa20_xmm6int-avx2.c"
                   "${SRC}/crypto_stream/salsa20/xmm6int/salsa20_xmm6int-sse2.c"
                   "${SRC}/crypto_stream/salsa2012/stream_salsa2012.c"
                   "${SRC}/crypto_stream/salsa2012/ref/stream_salsa2012_ref.c"
                   "${SRC}/crypto_stream/salsa208/stream_salsa208.c"
                   "${SRC}/crypto_stream/salsa208/ref/stream_salsa208_ref.c"
                   "${SRC}/crypto_stream/xchacha20/stream_xchacha20.c"
                   "${SRC}/crypto_stream/xsalsa20/stream_xsalsa20.c"
                   "${SRC}/crypto_verify/sodium/verify.c"
                   "${SRC}/randombytes/randombytes.c"
                   "${SRC}/randombytes/nativeclient/randombytes_nativeclient.c"
                   "${SRC}/randombytes/salsa20/randombytes_salsa20_random.c"
                   "${SRC}/randombytes/sysrandom/randombytes_sysrandom.c"
                   "${SRC}/sodium/core.c"
                   "${SRC}/sodium/runtime.c"
                   "${SRC}/sodium/utils.c"
                   "${SRC}/sodium/version.c"
                   "${SOURCE_DIR}/port/randombytes_esp8266.c")


file(READ ../include/sdkconfig.h SDKCONFIG)
string(FIND ${SDKCONFIG} "CONFIG_SSL_USING_MBEDTLS" FOUND)
if (FOUND GREATER 1)
    set(usingMBEDTLS ${SOURCE_DIR}/port/crypto_hash_mbedtls/crypto_hash_sha256_mbedtls.c
                     ${SOURCE_DIR}/port/crypto_hash_mbedtls/crypto_hash_sha512_mbedtls.c )

else(FOUND GREATER 1)
    set(notUsingMBEDTLS ${SRC}/crypto_hash/sha256/cp/hash_sha256_cp.c
                        ${SRC}/crypto_hash/sha512/cp/hash_sha512_cp.c)
endif()

add_library(sodium STATIC ${COMMON_SRC}  ${usingMBEDTLS} ${notUsingMBEDTLS})


target_compile_options(sodium  PRIVATE   -DICACHE_FLASH  ${COMMON_FLAGS} ${WARNING_FLAGS} -DCONFIGURED  -DNATIVE_LITTLE_ENDIAN -DHAVE_WEAK_SYMBOLS -D__STDC_LIMIT_MACROS -D__STDC_CONSTANT_MACROS )
target_include_directories(sodium PRIVATE
        ${SRC}/include/sodium
        ${SOURCE_DIR}/port_include/sodium
        ${SOURCE_DIR}/port
)

target_include_directories(sodium PUBLIC
        ${CMAKE_SOURCE_DIR}/include
        ${SRC}/include
        ${SOURCE_DIR}/port_include
        ${RTOS_DIR}/components/heap/include
        ${RTOS_DIR}/components/heap/port/esp8266/include
        ${RTOS_DIR}/components/smartconfig_ack/include
        ${RTOS_DIR}/components/bootloader_support/include
        ${RTOS_DIR}/components/cjson/include 
        ${RTOS_DIR}/components/cjson/cJSON 
        ${RTOS_DIR}/components/esp8266/include
        ${RTOS_DIR}/components/esp8266/include 
        ${RTOS_DIR}/components/espos/include
        ${RTOS_DIR}/components/freertos/include
        ${RTOS_DIR}/components/freertos/include
        ${RTOS_DIR}/components/freertos/include/freertos
        ${RTOS_DIR}/components/freertos/include/freertos/private
        ${RTOS_DIR}/components/freertos/port/esp8266/include
        ${RTOS_DIR}/components/freertos/port/esp8266/include/freertos
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
        -I . 
        )


# patch around warnings in third-party files
set_source_files_properties(
    ${SRC}/crypto_pwhash/argon2/pwhash_argon2i.c
    ${SRC}/crypto_pwhash/argon2/argon2-core.c
    ${SRC}/crypto_pwhash/scryptsalsa208sha256/pwhash_scryptsalsa208sha256.c
    PROPERTIES COMPILE_FLAGS
    -Wno-type-limits
    )
set_source_files_properties(
    ${SRC}/sodium/utils.c
    PROPERTIES COMPILE_FLAGS
    -Wno-unused-variable
    )
set_source_files_properties(
    ${SRC}/crypto_pwhash/argon2/argon2-fill-block-ref.c
    PROPERTIES COMPILE_FLAGS
    -Wno-unknown-pragmas
    )

set_source_files_properties(
    ${SRC}/crypto_shorthash/siphash24/ref/shorthash_siphashx24_ref.c
    ${SRC}/crypto_shorthash/siphash24/ref/shorthash_siphash24_ref.c
    PROPERTIES COMPILE_FLAGS
    -Wno-implicit-fallthrough
    )

set_source_files_properties( $(SRC)/randombytes/randombytes.c  PROPERTIES COMPILE_FLAGS  -DRANDOMBYTES_DEFAULT_IMPLEMENTATION)