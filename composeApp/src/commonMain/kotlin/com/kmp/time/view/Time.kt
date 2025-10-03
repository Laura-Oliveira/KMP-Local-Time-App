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
            //Top: Textfield City
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = location,
                    onValueChange = { location = it },
                    placeholder = { Text("Enter the city") }
                )//Textfield

                //Spacer
                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { timeAtLocation = calculateCurrentTime(location) }) {
                    Text("Show Time at Location")
                }//Button
            }//Column

            //Center: Time
            Text(
                text = timeAtLocation,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.Center)
            )//Text
        }//Box
    }//Material Theme
}//Composable

//Timezones
val mapCityToTimeZone = mapOf(
    "são paulo" to "America/Sao_Paulo",
    "new york" to "America/New_York",
    "madrid" to "Europe/Madrid",
    "tokyo" to "Asia/Tokyo"
)

@OptIn(ExperimentalTime::class)
fun calculateCurrentTime(location: String): String
{
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
}



