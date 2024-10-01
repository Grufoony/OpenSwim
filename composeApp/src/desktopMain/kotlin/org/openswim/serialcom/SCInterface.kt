package org.openswim

import com.fazecast.jSerialComm.SerialPort

abstract class SCInterface(val port: SerialPort, val baudRate: Int) {
    init {
        port.setComPortParameters(baudRate, 8, 1, SerialPort.NO_PARITY);
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        port.openPort()
    }
    abstract fun getVersion() : String?
    abstract fun askMessage()
    abstract fun getMessage() : String
}