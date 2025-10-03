package com.kmp.time

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun TimeWeb() {
    MaterialTheme {
        var location by remember { mutableStateOf("") }
        var timeAtLocation: String by remember { mutableStateOf("No location selected") }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
        ) {
            // Topo: TextField e Button
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter) // fica no topo
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = location,
                    onValueChange = { location = it },
                    placeholder = { Text("Enter the city") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { timeAtLocation = calculateCurrentTimeWeb(location) }) {
                    Text("Show Time at Location")
                }
            }

            // Centro: horário
            Text(
                text = timeAtLocation,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

val mapCityToOffset = mapOf(
    "sao paulo" to -3,
    "nova york" to -4,
    "barcelona" to 2,
    "tokyo" to 9
)

@OptIn(ExperimentalTime::class)
fun calculateCurrentTimeWeb(location: String): String {
    val cityKey = location.lowercase().trim()
    val offset = mapCityToOffset[cityKey] ?: return "City not found"

    // Hora atual em UTC
    val nowUTC = Clock.System.now()
    val nowLocal = nowUTC.toLocalDateTime(TimeZone.UTC)

    // Ajusta o offset manualmente
    val hour = (nowLocal.hour + offset).mod(24)
    val minute = nowLocal.minute
    val second = nowLocal.second

    return "${hour.toString().padStart(2,'0')}:${minute.toString().padStart(2,'0')}:${second.toString().padStart(2,'0')}"
}