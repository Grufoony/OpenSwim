package org.openswim

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.*
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.fazecast.jSerialComm.SerialPort

import openswim.composeapp.generated.resources.*

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var showBaudRateMenu by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        var ports by remember { mutableStateOf(emptyArray<SerialPort>()) }
        var selectedPort by remember { mutableStateOf<SerialPort?>(null) }
        var response by remember { mutableStateOf<String?>(null) }
        val baudRates = listOf(9600, 14400, 19200, 38400, 57600, 115200)
        var selectedBaudRate by remember { mutableStateOf<Int>(19200) }

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
                            Button(onClick = { selectedPort = port }) {
                                Text("Name: ${port.systemPortName}, Description: ${port.portDescription}")
                            }
                        }
                    }
                }
            }
            selectedPort?.let { port ->
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Selected Port: ${port.systemPortName}")
                    Text("Select Baud Rate:")
                    DropdownMenu(
                        expanded = showBaudRateMenu,
                        onDismissRequest = { showBaudRateMenu = false }
                    ) {
                        baudRates.forEach { rate ->
                            DropdownMenuItem(onClick = {
                                selectedBaudRate = rate
                                showBaudRateMenu = false
                            }) {
                                Text("$rate")
                            }
                        }
                    }
                    Button(onClick = { showBaudRateMenu = true }) {
                        Text("Baud Rate: ${selectedBaudRate ?: "Select"}")
                    }
                    Button(onClick = {
                        coroutineScope.launch {
                            response = Master3(port, selectedBaudRate).getVersion()
                        }
                    }) {
                        Text("Get Version")
                    }
                    Text("Response: ${response ?: "Error: No response received"}")
                }
            }
        }
    }
}
