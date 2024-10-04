package org.openswim

import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.*
import org.jetbrains.compose.resources.stringResource
import com.fazecast.jSerialComm.SerialPort
import openswim.composeapp.generated.resources.*

@Composable
fun ChronoSetupWindow(
    onConfirm: (selectedBaudRate: Int, chronoType: ChronoDefs, port: SerialPort) -> Unit
) {
    // Constants
    val baudRates = listOf(9600, 14400, 19200, 38400, 57600, 115200)
    // Selected values
    var selectedBaudRate by remember { mutableStateOf(19200) }
    var selectedChrono by remember { mutableStateOf<ChronoDefs>(Master3())}
    var availablePorts by remember { mutableStateOf(emptyArray<SerialPort>()) }
    var selectedPort by remember { mutableStateOf<SerialPort?>(null) }
    // Show vars
    var showChronoMenu by remember { mutableStateOf(false) }
    var showBaudRateMenu by remember { mutableStateOf(false) }
    var showPortsMenu by remember { mutableStateOf(false) }

    // Start a coroutine to fetch ports (assuming you have a function for that)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                availablePorts = SerialPort.getCommPorts()
                delay(50000) // Fetch every 0.5 seconds
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Chrono Setup", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(16.dp))

        Box {
            Button(onClick = { showBaudRateMenu = !showBaudRateMenu }) {
                Text("Baud Rate: ${selectedBaudRate}")
            }
            DropdownMenu(
                expanded = showBaudRateMenu,
                onDismissRequest = { showBaudRateMenu = false }
            ) {
                baudRates.forEach { bR ->
                    DropdownMenuItem(onClick = {
                        selectedBaudRate = bR
                        showBaudRateMenu = false
                    }) {
                        Text(bR.toString())
                    }
                }
            }
        }
        

        Spacer(modifier = Modifier.height(16.dp))
        
        Box {
            Button(onClick = { showChronoMenu = !showChronoMenu }) {
                Text(selectedChrono.name) // Display the selected ChronoDefs name
            }
            DropdownMenu(
                expanded = showChronoMenu,
                onDismissRequest = { showChronoMenu = false }
            ) {
                listOf(Master3::class, Master::class).forEach { clazz ->
                val instance = clazz.java.getDeclaredConstructor().newInstance() as ChronoDefs
                DropdownMenuItem(onClick = {
                    selectedChrono = instance
                    showChronoMenu = false
                }) {
                    Text(instance.name) // Display the ChronoDefs name
                }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        Box {
            Button(onClick = { if (!availablePorts.isEmpty()) showPortsMenu = !showPortsMenu }) {
                Text(
                    "Selected port: ${selectedPort?.let { "${it.systemPortName}-${it.descriptivePortName}" } ?: stringResource(Res.string.no_ports)}"
                )
            }
            DropdownMenu(
                expanded = showPortsMenu, // Show only if ports available and no port selected
                onDismissRequest = { showPortsMenu == false }
            ) {
                availablePorts.forEach { port ->
                    DropdownMenuItem(onClick = { selectedPort = port }) {
                        Text(text = "Name: ${port.systemPortName}, Description: ${port.portDescription}")
                    }
                }
            }
        }

        Button(
            onClick = {
                if (selectedBaudRate > 0 && selectedPort != null) {
                    onConfirm(selectedBaudRate, selectedChrono, selectedPort!!)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirm")
        }
    }
}