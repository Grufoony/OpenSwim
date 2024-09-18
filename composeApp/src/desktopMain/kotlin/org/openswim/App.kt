package org.openswim

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.fazecast.jSerialComm.*

import openswim.composeapp.generated.resources.Res
import openswim.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var ports by remember { mutableStateOf(emptyArray<SerialPort>()) }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                showContent = !showContent
                if (showContent) {
                    println("Button clicked, fetching ports...")
                    ports = SerialPort.getCommPorts()
                    if (ports.isEmpty()) {
                        println("No ports found.")
                    } else {
                        ports.forEach { port ->
                            println("Ports fetched: ${port.systemPortName}")
                        }
                    }
                }
            }) {
                Text("Fetch ports")
            }
            AnimatedVisibility(showContent) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    // Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Available Serial Ports:")
                    ports.forEach { port ->
                        Text(port.getSystemPortName())
                    }
                }
            }
        }
    }
}
