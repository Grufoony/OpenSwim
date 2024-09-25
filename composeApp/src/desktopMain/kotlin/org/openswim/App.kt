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
import kotlinx.coroutines.*
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.fazecast.jSerialComm.SerialPort

import openswim.composeapp.generated.resources.*

// import openswim.composeapp.generated.resources.Res

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        var ports by remember { mutableStateOf(emptyArray<SerialPort>()) }

        // Start a coroutine to fetch ports
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                while (true) {
                    ports = SerialPort.getCommPorts()
                    delay(500) // Fetch every 0.5 seconds
                }
            }
        }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Show ports")
            }
            AnimatedVisibility(showContent) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Available Serial Ports:")
                    if (ports.isEmpty()) {
                        Text(stringResource(Res.string.no_ports))
                    } else {
                        ports.forEach { port ->
                            Text("Name: ${port.getSystemPortName()}, Baud Rate: ${port.getBaudRate()}, Description: ${port.getPortDescription()}")
                        }
                    }
                }
            }
        }
    }
}
