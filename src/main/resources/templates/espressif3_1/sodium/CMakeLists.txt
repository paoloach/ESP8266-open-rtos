cmake_minimum_required(VERSION 3.10)
project(untitled)

set(SOURCE_DIR ${RTOS_DIR}/components/libsodium)
set(LSRC ${SOURCE_DIR}/libsodium/src/libsodium)


file(GLOB SOURCE ${SOURCE_DIR}/port/*.c
        ${LSRC}/crypto_aead/chacha20poly1305/sodium/*.c
        ${LSRC}/crypto_aead/xchacha20poly1305/sodium/*.c
        ${LSRC}/crypto_auth/*.c
        ${LSRC}/crypto_auth/hmacsha256/*.c
        ${LSRC}/crypto_auth/hmacsha512/*.c
        ${LSRC}/crypto_auth/hmacsha512256/*.c
        ${LSRC}/crypto_box/*.c
        ${LSRC}/crypto_box/curve25519xsalsa20poly1305/*.c
        ${LSRC}/crypto_core/curve25519/ref10/*.c
        ${LSRC}/crypto_core/hchacha20/*.c
        ${LSRC}/crypto_core/hsalsa20/ref2/*.c
        ${LSRC}/crypto_core/hsalsa20/*.c
        ${LSRC}/crypto_core/salsa/ref/*.c
        ${LSRC}/crypto_generichash/*.c
        ${LSRC}/crypto_generichash/blake2b/*.c
        ${LSRC}/crypto_generichash/blake2b/ref/*.c
        ${LSRC}/crypto_hash/*.c
        ${LSRC}/crypto_hash/sha256/*.c
        ${LSRC}/crypto_hash/sha512/*.c
        ${LSRC}/crypto_kdf/blake2b/*.c
        ${LSRC}/crypto_kdf/*.c
        ${LSRC}/crypto_kx/*.c
        ${LSRC}/crypto_onetimeauth/*.c
        ${LSRC}/crypto_onetimeauth/poly1305/*.c
        ${LSRC}/crypto_onetimeauth/poly1305/donna/*.c
        ${LSRC}/crypto_pwhash/argon2/*.c
        ${LSRC}/crypto_pwhash/*.c
        ${LSRC}/crypto_pwhash/scryptsalsa208sha256/*.c
        ${LSRC}/crypto_pwhash/scryptsalsa208sha256/nosse/*.c
        ${LSRC}/crypto_scalarmult/*.c
        ${LSRC}/crypto_scalarmult/curve25519/*.c
        ${LSRC}/crypto_scalarmult/curve25519/ref10/*.c
        ${LSRC}/crypto_secretbox/*.c
        ${LSRC}/crypto_secretbox/xsalsa20poly1305/*.c
        ${LSRC}/crypto_shorthash/*.c
        ${LSRC}/crypto_shorthash/siphash24/*.c
        ${LSRC}/crypto_shorthash/siphash24/ref/*.c
        ${LSRC}/crypto_sign/*.c
        ${LSRC}/crypto_sign/ed25519/*.c
        ${LSRC}/crypto_sign/ed25519/ref10/*.c
        ${LSRC}/crypto_stream/chacha20/*.c
        ${LSRC}/crypto_stream/chacha20/ref/*.c
        ${LSRC}/crypto_stream/*.c
        ${LSRC}/crypto_stream/salsa20/*.c
        ${LSRC}/crypto_stream/salsa20/ref/*.c
        ${LSRC}/crypto_stream/xsalsa20/*.c
        ${LSRC}/crypto_verify/sodium/*.c
        ${LSRC}/randombytes/*.c
        ${LSRC}/sodium/*.c)


string(FIND ${SDKCONFIG} "CONFIG_LIBSODIUM_USE_MBEDTLS_SHA" FOUND_LIBSODIUM_USE_MBEDTLS_SHA)
if (FOUND_LIBSODIUM_USE_MBEDTLS_SHA GREATER 1)
    file(GLOB COMPONENT_SOURCE ${SOURCE_DIR}/port/crypto_hash_mbedtls/*.c)
else(FOUND_LIBSODIUM_USE_MBEDTLS_SHA GREATER 1)
     file(GLOB COMPONENT_SOURCE $(LSRC)/crypto_hash/sha256/cp/*.c  $(LSRC)/crypto_hash/sha512/cp/*.c)
endif()

add_library(sodium STATIC ${SOURCE} ${COMPONENT_SOURCE} )


target_compile_options(sodium PRIVATE -DICACHE_FLASH ${COMMON_FLAGS} ${WARNING_FLAGS} -DCONFIGURED -DNATIVE_LITTLE_ENDIAN -DHAVE_WEAK_SYMBOLS -D__STDC_LIMIT_MACROS -D__STDC_CONSTANT_MACROS)
target_include_directories(sodium PRIVATE
        ${LSRC}/include/sodium
        ${SOURCE_DIR}/port_include/sodium
        ${SOURCE_DIR}/port
        )

target_include_directories(sodium PUBLIC
        ${COMMON_INCLUDE}
        ${LSRC}/include
        ${SOURCE_DIR}/port_include
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

set_source_files_properties($(SRC)/randombytes/randombytes.c PROPERTIES COMPILE_FLAGS -DRANDOMBYTES_DEFAULT_IMPLEMENTATION)