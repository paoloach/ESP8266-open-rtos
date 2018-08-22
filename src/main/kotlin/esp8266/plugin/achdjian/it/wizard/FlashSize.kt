package esp8266.plugin.achdjian.it.wizard

enum class FlashSize (val size: Int){
    KB_256(256*1024),
    KB_512(512*1024),
    MB_1(1024*1024),
    MB_2(2*1024*1024),
    MB_4(4*1024*1024);

    companion object {
        fun getElement(name:String): FlashSize {
            return values().first { it.strSize() == name }
        }
    }

    fun strSize() : String{
        if (size < 1024*1024){
            return (size / 1024).toString() + "KB"
        } else {
            return (size / (1024*1024)).toString() + "MB"
        }
    }

    fun startingRom2() : String {
        return "0x"+ ((size /  2) +  0x40202010).toString(16)
    }

    fun sizeRom2(): String {
        return "0x"+ ((size /  2) - 0x2010).toString(16)
    }

    fun startingFlashRom2(): String {
        return "0x" + ((size/2) + 0x2000).toString(16)
    }
}