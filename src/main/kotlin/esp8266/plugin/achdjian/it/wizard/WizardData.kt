package esp8266.plugin.achdjian.it.wizard

import esp8266.plugin.achdjian.it.wizard.free.rtos.extras.SimpleModule
import esp8266.plugin.achdjian.it.wizard.free.rtos.extras.SpiffsModule

class WizardData {
    val extras = listOf(SimpleModule("ad770x"),
            SimpleModule("ads111x"),
            SimpleModule("bearssl"),
            SimpleModule("bh1750"),
            SimpleModule("bme680"),
            SimpleModule("bmp180"),
            SimpleModule("bmp280"),
            SimpleModule("ccs811"),
            SimpleModule("cpp_support"),
            SimpleModule("crc_generic"),
            SimpleModule("dhcpserver"),
            SimpleModule("dht"),
            SimpleModule("ds1302"),
            SimpleModule("ds1307"),
            SimpleModule("ds18b20"),
            SimpleModule("ds3231"),
            SimpleModule("dsm"),
            SimpleModule("fatfs"),
            SimpleModule("fonts"),
            SimpleModule("fram"),
            SimpleModule("hd44780"),
            SimpleModule("hmc5883l"),
            SimpleModule("http-parser"),
            SimpleModule("http_client_ota"),
            SimpleModule("httpd"),
            SimpleModule("i2c"),
            SimpleModule("i2s_dma"),
            SimpleModule("ina3221"),
            SimpleModule("jsmn"),
            SimpleModule("l3gd20h"),
            SimpleModule("libesphttpd"),
            SimpleModule("lis3dh"),
            SimpleModule("lis3mdl"),
            SimpleModule("lsm303d"),
            SimpleModule("max7219"),
            SimpleModule("mbedtls"),
            SimpleModule("mcp4725"),
            SimpleModule("mdnsresponder"),
            SimpleModule("ms561101ba03"),
            SimpleModule("multipwm @ 44ecea5"),
            SimpleModule("onewire"),
            SimpleModule("paho_mqtt_c"),
            SimpleModule("pca9685"),
            SimpleModule("pcf8574"),
            SimpleModule("pcf8591"),
            SimpleModule("pwm"),
            SimpleModule("sdio"),
            SimpleModule("sht3x"),
            SimpleModule("sntp"),
            SimpleModule("softuart"),
            SpiffsModule(),
            SimpleModule("ssd1306"),
            SimpleModule("stdin_uart_interrupt"),
            SimpleModule("timekeeping"),
            SimpleModule("tsl2561"),
            SimpleModule("tsl4531"),
            SimpleModule("ultrasonic"),
            SimpleModule("wificfg"),
            SimpleModule("ws2812"),
            SimpleModule("ws2812_i2s") )
    var flashSize = FlashSize.KB_512
    var flashMode = "qio"
    var flashSpeed = "40"
    var espPort = "/dev/ttyUSB0"
    var floatSupport = false
    var otaSupport = false
}