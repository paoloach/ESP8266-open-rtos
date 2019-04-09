package esp8266.plugin.achdjian.it.wizard.espressif.rtos.components

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configurations.*

val socFullICache = BooleanInteger("Enable full cache mode", "SOC_FULL_ICACHE", false, 0x8000, 0xC000)

private val defaultLineEnding = BoolConfigEntry("CRLF", "NEWLIB_STDOUT_LINE_ENDING_CRLF", true)
private val defaultCrystalUsed = BoolConfigEntry("26Mh", "CRYSTAL_USED_26MHZ", true)
private val choiceCrystalUsed = ChoiceConfigEntry("Crystal Used", "CRYSTAL_USED", listOf(
        defaultCrystalUsed,
        BoolConfigEntry("40Mh", "CRYSTAL_USED_40MHZ")
), defaultCrystalUsed)

val taskWdTimeout15N = BoolConfigEntry("26.2144s", "TASK_WDT_TIMEOUT_15N", true)
val taskWdTimeout14N = BoolConfigEntry("13.1072s", "TASK_WDT_TIMEOUT_14N")
val taskWdTimeout13N = BoolConfigEntry("6.5536s", "TASK_WDT_TIMEOUT_13N")
val choiceWT = ChoiceConfigEntry("Task Watchdog timeout period (seconds)", "TASK_WDT_TIMEOUT", listOf(
        taskWdTimeout15N,
        taskWdTimeout14N,
        taskWdTimeout13N
), taskWdTimeout15N)


val consoleUartDefault = BoolConfigEntry("Default: UART0", "CONSOLE_UART_DEFAULT", true)
val consoleUartCustom = BoolConfigEntry("Custom", "CONSOLE_UART_CUSTOM", false)
val consoleUartNone = BoolConfigEntry("None", "CONSOLE_UART_NONE", false)
val consoleUart = ChoiceConfigEntry("UART for console output", "CONSOLE_UART", listOf(
        consoleUartDefault,
        consoleUartCustom,
        consoleUartNone
), consoleUartDefault)


val consoleUartCustom0 = BoolConfigEntry("UART0", "CONSOLE_UART_CUSTOM_NUM_0", true,consoleUartCustom )
val consoleUartCustom1 = BoolConfigEntry("UART1", "CONSOLE_UART_CUSTOM_NUM_1", false,consoleUartCustom )
val consoleUartNum = ChoiceConfigEntry("UART peripheral to use for console output (0-1)", "CONSOLE_UART_NUM", listOf(
        consoleUartCustom0,
        consoleUartCustom1
), consoleUartCustom0,consoleUartCustom)

val esp8266ConfigurationMenu = listOf(
        socFullICache,
        consoleUart,
        consoleUartNum,
        IntConfigEntry("UART console baud rate", "CONSOLE_UART_BAUDRATE", 115200, 1200, 4000000, Or(consoleUartDefault,consoleUartCustom )),
        BoolConfigEntry("Swap UART0 I/O pins", "UART0_SWAP_IO", false),
        IntConfigEntry("Main task stack size", "MAIN_TASK_STACK_SIZE", 3584, 0, 10000),
        BoolConfigEntry("Initialize Task Watchdog Timer on startup", "TASK_WDT", true),
        BoolConfigEntry("Invoke panic handler on Task Watchdog timeout", "TASK_WDT_PANIC", true),
        choiceWT,
        BoolConfigEntry("Enable reset reason", "RESET_REASON", true),
        IntConfigEntry("ppT task stack size", "WIFI_PPT_TASKSTACK_SIZE", 2048, 2048, 8192),
        IntConfigEntry("Event loop stack size", "EVENT_LOOP_STACK_SIZE", 2048, 0, 10000),

        BoolConfigEntry("Link libcore.a internal global data to IRAM", "ESP8266_CORE_GLOBAL_DATA_LINK_IRAM", true, And(Not(lwipHighThroughput), Not(socFullICache))),

        ChoiceConfigEntry("Line ending for UART output", "NEWLIB_STDOUT_LINE_ENDING", listOf(
                defaultLineEnding,
                BoolConfigEntry("LF", "NEWLIB_STDOUT_LINE_ENDING_LF"),
                BoolConfigEntry("CR", "NEWLIB_STDOUT_LINE_ENDING_CR")
        ), defaultLineEnding),

        choiceCrystalUsed,
        BoolConfigEntry("Use a partition to store PHY init data", "ESP_PHY_INIT_DATA_IN_PARTITION"),
        BoolConfigEntry("Store phy calibration data in NVS", "ESP_PHY_CALIBRATION_AND_DATA_STORAGE", true),
        BoolConfigEntry("Using new ets_vprintf instead of rom code", "USING_NEW_ETS_VPRINTF", true),
        BoolConfigEntry("Set wifi tx rate from 54M to 1M", "WIFI_TX_RATE_SEQUENCE_FROM_HIGH", true)

)