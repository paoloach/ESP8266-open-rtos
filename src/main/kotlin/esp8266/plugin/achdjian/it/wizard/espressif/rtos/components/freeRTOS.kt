package esp8266.plugin.achdjian.it.wizard.espressif.rtos.components

import esp8266.plugin.achdjian.it.wizard.espressif.rtos.ConfigurationEntry
import esp8266.plugin.achdjian.it.wizard.espressif.rtos.configentry.*


val freeRTOSHz = IntConfigEntry("Tick rate (Hz)", "FREERTOS_HZ", 100, 1, 1000)
val useTraceFacility = BoolConfigEntry("Enable FreeRTOS trace facility", "FREERTOS_USE_TRACE_FACILITY", false)
val generateRunTimeStats = BoolConfigEntry("Enable FreeRTOS to collect run time stats", "FREERTOS_GENERATE_RUN_TIME_STATS", false)
val runTimeStatsUsingEspTimer = BoolConfigEntry("Use ESP TIMER for run time stats", "FREERTOS_RUN_TIME_STATS_USING_ESP_TIMER", true)

val freeRTOSConfig = listOf<ConfigurationEntry>(
        BoolConfigEntry("Enable \"reent\" function", "FREERTOS_ENABLE_REENT", false),
        freeRTOSHz,
        IntConfigEntry("FreeRTOS hook max number", "FREERTOS_MAX_HOOK", 2, 1, 16),
        IntConfigEntry("ISR stack size", "FREERTOS_ISR_STACKSIZE", 1536, 1, 10000),
        BoolConfigEntry("Use FreeRTOS extened hooks", "FREERTOS_EXTENED_HOOKS", false),
        BoolConfigEntry("Link FreeRTOS global data to IRAM", "FREERTOS_GLOBAL_DATA_LINK_IRAM", true, And( Not(lwipHighThroughput), Not(socFullICache) )),

        IntConfigEntry("Timer stack size", "FREERTOS_TIMER_STACKSIZE", 2048, 1, 10000),
        BoolConfigEntry("Task switch faster", "TASK_SWITCH_FASTER", true),
        BoolConfigEntry("Using Queue Sets", "USE_QUEUE_SETS", false),
        BoolConfigEntry("Enable FreeRTOS SLEEP", "ENABLE_FREERTOS_SLEEP", false),
        useTraceFacility,
        BoolConfigEntry("Enable FreeRTOS stats formatting functions", "FREERTOS_USE_STATS_FORMATTING_FUNCTIONS", false,useTraceFacility),
        BoolConfigEntry("Enable FreeRTOS stats formatting functions", "FREERTOS_GENERATE_RUN_TIME_STATS", false,useTraceFacility),
        generateRunTimeStats,
        ChoiceConfigEntry("Choose the clock source for run time stats", "FREERTOS_RUN_TIME_STATS_CLK", listOf(
                runTimeStatsUsingEspTimer,
                BoolConfigEntry("Use CPU Clock for run time stats", "FREERTOS_RUN_TIME_STATS_USING_CPU_CLK")
        ), runTimeStatsUsingEspTimer,generateRunTimeStats)
)