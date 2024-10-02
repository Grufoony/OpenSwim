package org.openswim

import kotlinx.coroutines.*

import io.github.oshai.kotlinlogging.KotlinLogging
private val LoggerSingleton = KotlinLogging.logger{}

class SerialThread(val serialComm: SerialComm, val chrono: ChronoDefs) {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private fun getResponse() : String? {
        var response: String? = null
        try {
            response = serialComm.receiveString(chrono.end_cmd)
        } catch (e: Exception) {
            LoggerSingleton.error{"Exception has been thrown."}
            this.stop()
        }
        return response
    }

    fun ping() : Boolean {
        var actualResponse: String? = null
        scope.launch {
            serialComm.sendString(chrono.version, chrono.end_cmd)
            actualResponse = getResponse() // Update the mutable state
        }
        val strResponse: String = actualResponse ?: return false // Check for null before using
        val lstStrArgs: List<String> = strResponse.split(chrono.sep)
        if (lstStrArgs[0] != chrono.id_name) {
            return false
        }
        LoggerSingleton.info{"Connected to ${chrono.name} version ${lstStrArgs.last()}"}
        return true
    }

    fun start() {
        LoggerSingleton.info{"Starting serial communication thread."}
        scope.launch {
            while (true) {
                serialComm.sendString(chrono.next_msg, chrono.end_cmd)
                getResponse()
            }
        }
    }

    fun stop() {
        LoggerSingleton.info{"Stopping serial communication thread."}
        job.cancel()
    }
}