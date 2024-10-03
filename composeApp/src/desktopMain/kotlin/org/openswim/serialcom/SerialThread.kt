package org.openswim

import kotlinx.coroutines.*

import io.github.oshai.kotlinlogging.KotlinLogging
private val LoggerSingleton = KotlinLogging.logger{}

class SerialThread(val serialComm: SerialComm, val chrono: ChronoDefs) {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    fun ping(timeoutMillis: Long = 1500) : Boolean {
        serialComm.sendString(chrono.version, chrono.end_cmd)
        LoggerSingleton.warn{ "Bro I'm here lol" }
        val responseAsync = serialComm.receiveStringAsync(chrono.end_cmd) // Suspend and wait for response
        var lstStrArgs: List<String>
        runBlocking {
            val response: String = withTimeout(timeoutMillis) {
                responseAsync.await()
            }
            lstStrArgs = response.split(chrono.sep)
        }
        if (lstStrArgs.size == 0 || lstStrArgs[0] != chrono.id_name) {
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
                serialComm.receiveStringAsync(chrono.end_cmd)
            }
        }
    }

    fun stop() {
        LoggerSingleton.info{"Stopping serial communication thread."}
        job.cancel()
    }
}