package org.openswim

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import io.github.oshai.kotlinlogging.KotlinLogging
private val LoggerSingleton = KotlinLogging.logger{}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "OpenSwim",
    ) {
        LoggerSingleton.info{"Before app"}
        App()
        LoggerSingleton.info{"After app"}
        LoggerSingleton.debug{"Debug test"}
    }
}