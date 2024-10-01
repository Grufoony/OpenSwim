package org.openswim

import com.fazecast.jSerialComm.*

class Master3(port: SerialPort, baudRate: Int) : SCInterface(port, baudRate) {
    override fun getVersion() : String? {
        val command = "?V\r\n".toByteArray()
        port.writeBytes(command, command.size)

        val buffer = ByteArray(1024)
        val messageBuilder = StringBuilder()
        var numRead: Int

        do {
            numRead = port.readBytes(buffer, buffer.size)
            if (numRead > 0) {
                messageBuilder.append(String(buffer, 0, numRead))
            }
        } while (!messageBuilder.endsWith("\r\n"))

        val response = messageBuilder.toString()

        println("Response: $response")
        val args: List<String> = response.split(',')

        if (args[0] != "\$MASTA") {
            return null
        }

        return args.last()
    }
    override fun askMessage() {
        val command = "?D\r\n".toByteArray()
        port.writeBytes(command, command.size)
    }
    override fun getMessage() : String {
        val buffer = ByteArray(1024)
        val numRead = port.readBytes(buffer, buffer.size)
        val response = String(buffer, 0, numRead)

        println("Response: $response")
        return response
    }
}