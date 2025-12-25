package org.openswim

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import io.github.oshai.kotlinlogging.KotlinLogging
private val LoggerSingleton = KotlinLogging.logger{}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "OpenSwim"
    ) {
        ChronoSetupWindow(
            onConfirm = { baudRate, chronoType, port ->
                // Handle confirmed baud rate and chrono type here
                println("Baud Rate: $baudRate, Chrono Type: ${chronoType.name}")
            }
        )
    }
}