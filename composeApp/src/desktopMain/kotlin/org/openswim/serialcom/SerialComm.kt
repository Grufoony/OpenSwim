package org.openswim

import com.fazecast.jSerialComm.SerialPort

import io.github.oshai.kotlinlogging.KotlinLogging

private val LoggerSingleton = KotlinLogging.logger {}

class SerialComm(
    val port: SerialPort,
    val iBaudRate: Int = 19200,
    val iNDataBits: Int = 8,
    val iNStopBits: Int = 1,
    val iParity: Int = SerialPort.NO_PARITY
) {
    init {
        port.setComPortParameters(iBaudRate, iNDataBits, iNStopBits, iParity);
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        port.openPort()
    }

    fun sendString(strData: String, strEndCommand: String = "") {
        val fullCmdBuilder = StringBuilder()
        fullCmdBuilder.append(strData).append(strEndCommand)
        var strFullCommand = fullCmdBuilder.toString()
        LoggerSingleton.debug { "Sending message ${strFullCommand}" }
        val bCommand = strFullCommand.toByteArray()
        try {
            port.writeBytes(bCommand, bCommand.size)
        } catch (e: Exception) {
            LoggerSingleton.error("Failed to send data: ${e.message}")
            throw e
        }
    }

    fun receiveString(strEndCommand: String): String {
        val buffer = ByteArray(1024)
        val responseBuilder = StringBuilder()
        var numRead: Int

        try {
            do {
                numRead = port.readBytes(buffer, buffer.size)
                if (numRead > 0) {
                    responseBuilder.append(String(buffer, 0, numRead))
                }
            } while (!responseBuilder.endsWith(strEndCommand))
        } catch (e: Exception) {
            LoggerSingleton.error { "Failed to receive data: ${e.message}" }
            throw e
        }

        val strResponse = responseBuilder.toString()
        LoggerSingleton.debug { "Received message: $strResponse" }
        return strResponse
    }

    fun close() {
        port.closePort()
    }
}