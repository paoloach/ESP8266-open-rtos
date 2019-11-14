package it.achdjian.plugin.esp8266.settings

const val GCC = "xtensa-lx106-elf-gcc"
const val CXX = "xtensa-lx106-elf-g++"
const val SERIAL_PORT="/dev/ttyUSB0"
const val BAUD = 115200

val DEFAULT = ESP8266SettingsState(GCC, CXX)