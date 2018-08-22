ESP8266 open RTOS  development plugin for JetBrains CLion
====

This plugin allow you to develop a new project using the [ESP 8266 open RTOS](https://github.com/SuperHouse/esp-open-rtos) and directly flash it.


INSTALLATION
---
Install the plugin from the [Jetbrains Repository](https://plugins.jetbrains.com/plugin)
You have to install also the [ESP 8266 open RTOS](https://github.com/SuperHouse/esp-open-rtos), [esptool](https://github.com/espressif/esptool) and the xtensa compiler from [ESP8266 pen sdk](https://github.com/pfalcon/esp-open-sdk/).
Differently from the  [ESP 8266 open RTOS](https://github.com/SuperHouse/esp-open-rtos) instructions, you don't have to set in the PATH environment variable the path of the xtensa compiler, but it is enough to indicate in the plugin setup phase.


SETUP
---

In
Setting->Build,Execution,Deployment->Build Tools->ESP8266 config 

set the xtensa-lx106-elf-gcc and xtensa-lx106-elf-g++ program path


CREATING A NEW PROJECT
---
 * File -> New Project
 * Select the C ESP8266 Free RTOS type
 * Set the wanted flash size and flash mode and flash speed and seria port to use
 * Select the extra projects you want use
 * Press Crete button
 
Then the plugin will crete all the file needs by CLion in order to compile and flash (depending on the target selected).

It create also a starting basic file: mainUser.c

OTHERS
---
    If you like the plugin, you may :star: the project at github (button at top-right of the page) and at [jetbrains plugins repository](https://plugins.jetbrains.com/plugin/10115).



