package it.achdjian.plugin.serial

import com.intellij.util.Consumer
import jssc.SerialPort
import jssc.SerialPortEvent
import jssc.SerialPortException
import java.util.*

class JsscSerialService : SerialService {
    override var port: SerialPort? = null
    override var baudRate: Int = 115200;
    private val dataListeners = Collections.synchronizedSet(mutableSetOf<Consumer<ByteArray>>())
    private val portStateListeners = Collections.synchronizedSet(mutableSetOf<Consumer<Boolean>>())

    override fun isConnected() = port != null

    override fun connect(portName: String, baudRate: Int) {
        this.baudRate = baudRate
        val dataBits = SerialPort.DATABITS_8
        val stopBits = SerialPort.STOPBITS_1
        val parity = SerialPort.PARITY_NONE

        try {
            SerialPort(portName).let {
                port = it
                it.openPort()
                val res = it.setParams(baudRate, dataBits, stopBits, parity, true, true)
                if (!res) {
                    throw SerialMonitorException("Failed to set SerialPort parameters")
                }
                it.addEventListener {
                    serialEventListener(it)
                }
            }
        } catch (e: SerialPortException) {
            if (e.portName.startsWith("/dev") && SerialPortException.TYPE_PERMISSION_DENIED == e.exceptionType) {
                throw SerialMonitorException(String.format("Error opening serial port \"%s\".", portName))
            }
            port = null
            throw SerialMonitorException(String.format("Error opening serial port \"%s\".", portName), e)
        }

        if (port == null) {
            throw SerialMonitorException(String.format("Serial port \"%s\" not found.", portName))
        }
        notifyStateListeners(true) // notify successful connect
    }


    override fun close() {
        port?.let { it ->
            try {
                if (it.isOpened) {
                    it.removeEventListener()
                }
                it.closePort()
            } catch (e: SerialPortException) {
                e.message?.let { throw SerialMonitorException(it, e) }
            } finally {
                port = null
                notifyStateListeners(false)
            }
        }
    }

    override fun write(bytes: ByteArray) {
        check(isConnected()) { "Port is not opened!" }
        port?.let {
            try {
                it.writeBytes(bytes)
            } catch (e: SerialPortException) {
                throw SerialMonitorException(e.message, e)
            }
        }
    }

    override fun addDataListener(listener: Consumer<ByteArray>) {
        require(!dataListeners.contains(listener))
        dataListeners.add(listener)
    }

    override fun removeDataListener(listener: Consumer<ByteArray>) {
        dataListeners.remove(listener)
    }

    override fun addPortStateListener(listener: Consumer<Boolean>) {
        portStateListeners.add(listener)
    }

    override fun removePortStateListener(listener: Consumer<Boolean>) {
        portStateListeners.remove(listener)
    }

    override fun notifyStateListeners(isConnected: Boolean) {
        for (listener in portStateListeners) {
            listener.consume(isConnected)
        }
    }

    override fun dispose() {
        dataListeners.clear()
        portStateListeners.clear()
        close()
    }


    private fun serialEventListener(serialEvent: SerialPortEvent) {
        if (serialEvent.isRXCHAR) {
            if (dataListeners.isEmpty()) {
                return
            }
            try {
                port?.let {
                    val buf = it.readBytes(serialEvent.eventValue)
                    if (buf.isNotEmpty()) {
                        for (dataListener in dataListeners) {
                            dataListener.consume(buf)
                        }
                    }
                }
            } catch (e: SerialPortException) {
                throw SerialMonitorException(e.message, e)
            }

        }
    }
}