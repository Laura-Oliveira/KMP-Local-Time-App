package com.kmp.time.view

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
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

@Composable
fun Time() {
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

                Button(onClick = { timeAtLocation = calculateCurrentTime(location) }) {
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



//    Column(
//            modifier = Modifier
//                .safeContentPadding()
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally // 👈 centraliza dentro da Column
//        ) {
//            TextField(
//                value = location, onValueChange = { location = it },
//                placeholder = { Text("Enter the city") })
//
//            Spacer(modifier = Modifier.padding(top = 8.dp))
//
//            Button(onClick = { timeAtLocation = calculateCurrentTime(location) }) {
//                Text("Show Time at Location")
//            }
//
//            // Espaçamento entre o botão e o texto
//            Spacer(modifier = Modifier.padding(top = 16.dp))
//
//            // Horário centralizado abaixo do botão
//            Text(
//                text = timeAtLocation,
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            Spacer(modifier = Modifier.padding(top = 16.dp))
//        }
//    }
//}

//=================== Navegador
//val mapCityToTimeZone = mapOf(
//    "sao paulo" to -3, // UTC-3
//    "nova york" to -4, // UTC-4 (horário de verão não considerado)
//    "barcelona" to 2,  // UTC+2
//    "tokyo" to 9       // UTC+9
//)




//Timezones
val mapCityToTimeZone = mapOf(
    "são paulo" to "America/Sao_Paulo",
    "new york" to "America/New_York",
    "madrid" to "Europe/Madrid",
    "tokyo" to "Asia/Tokyo"
)

@OptIn(ExperimentalTime::class)
fun calculateCurrentTime(location: String): String {
    val city = location.lowercase().trim()
    val timezoneId = mapCityToTimeZone[city] ?: return "no city selected"

    val timezone = TimeZone.of(timezoneId)
    val now = kotlin.time.Clock.System.now().toLocalDateTime(timezone)
    val localTime: LocalTime = now.time

    // Formata HH:mm:ss sem String.format
    val hour = localTime.hour.toString().padStart(2, '0')
    val minute = localTime.minute.toString().padStart(2, '0')
    val second = localTime.second.toString().padStart(2, '0')

    return "$hour:$minute:$second"

//    return localTime.toString()
    // return "%02d:%02d:%02d".format(localTime.hour, localTime.minute, localTime.second)
}



