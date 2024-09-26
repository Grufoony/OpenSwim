package org.openswim

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import org.apache.logging.log4j.kotlin.logger

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "OpenSwim",
    ) {
        val klogger = logger()

        klogger.warn{"Warning eheh"}
        App()
        klogger.info{"Info eheh"}
    }
}