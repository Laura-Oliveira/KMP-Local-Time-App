package com.kmp.time.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kmplocaltime.composeapp.generated.resources.Res
import kmplocaltime.composeapp.generated.resources.eg
import kmplocaltime.composeapp.generated.resources.fr
import kmplocaltime.composeapp.generated.resources.jp
import kmplocaltime.composeapp.generated.resources.mx
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class Country(val name: String, val zone: TimeZone, val image: Painter)

@OptIn(ExperimentalTime::class)
fun currentTimeAt(location: String, zone: TimeZone): String {
    //  val city = location.lowercase().trim()
    //  val timezoneId = mapCityToTimeZone[city] ?: return "no city selected"

    val timezone = zone
    val now = Clock.System.now().toLocalDateTime(timezone)
    val localTime: LocalTime = now.time

    // Formata HH:mm:ss manualmente
    val hour = localTime.hour.toString().padStart(2, '0')
    val minute = localTime.minute.toString().padStart(2, '0')
    val second = localTime.second.toString().padStart(2, '0')

    return "$hour:$minute:$second"
}


@Composable
fun countries() = listOf(
    Country("Japan", TimeZone.of("Asia/Tokyo"), painterResource(Res.drawable.jp)),
    Country("France", TimeZone.of("Europe/Paris"), painterResource(Res.drawable.fr)),
    Country("Mexico", TimeZone.of("America/Mexico_City"), painterResource(Res.drawable.mx)),
    Country("Indonesia", TimeZone.of("Asia/Jakarta"), painterResource(Res.drawable.jp)),
    Country("Egypt", TimeZone.of("Africa/Cairo"), painterResource(Res.drawable.eg)),
)

@Composable
fun Time() {
    MaterialTheme {
        var showCountries by remember { mutableStateOf(false) }
        var timeAtLocation by remember { mutableStateOf("No location selected") }
        var selectedCountry by remember { mutableStateOf<Country?>(null) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .safeContentPadding()
                    .fillMaxSize()
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Row(modifier = Modifier.padding(start = 20.dp, top = 10.dp)) {
                    DropdownMenu(
                        expanded = showCountries,
                        onDismissRequest = { showCountries = false }
                    ) {
                        countries().forEach { country ->
                            DropdownMenuItem(
//                                text = { Text(country.name) },
                                onClick = {
                                    selectedCountry = Country(country.name, country.zone, country.image)
                                    timeAtLocation = currentTimeAt(country.name, country.zone)
                                    showCountries = false
                                },
                                text = {
                                    Row (verticalAlignment = Alignment.CenterVertically){
                                        Image(
                                            painter = country.image,
                                            contentDescription = "country flag",
                                            modifier = Modifier
                                                .size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))

                                        Text(country.name)
                                    }
                                }
                            )
                        }//list
                    }//Dropdown Menu
                }//Row
                Button(
                    modifier = Modifier.padding(10.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    onClick = { showCountries = !showCountries })
                {
                    Text("Select Location")
                }//Button

                //Time
                if(selectedCountry != null)
                {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center))
                    {
                        Image(
                            painter = selectedCountry!!.image,
                            contentDescription = "country flag",
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "${selectedCountry!!.name}: $timeAtLocation",
                            style = TextStyle(fontSize = 20.sp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                else
                {
                    Text(
                        text = "No Location Selected",
                        style = TextStyle(fontSize = 20.sp),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                }

//                Text(
////                    text = when {
////                        selectedCountry != null -> "${selectedCountry?.image} ${selectedCountry?.name}: $timeAtLocation"
////                        else -> "No Location Selected"
////                                },
//
////                    if(timeAtLocation != "No location selected")
////                    { "Current Time $timeAtLocation" }
////                    else
////                    { timeAtLocation },
//                    style = TextStyle(fontSize = 20.sp),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .wrapContentSize(Alignment.Center)
//                )//Time
            }//Column
        }//Box
    }//MaterialTheme
}//Composable

//
//@Composable
//fun Time() {
//    MaterialTheme {
//        var location by remember { mutableStateOf("") }
//        var timeAtLocation: String by remember { mutableStateOf("No location selected") }
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .safeContentPadding()
//        ) {
//            //Top: Textfield City
//            Column(
//                modifier = Modifier
//                    .align(Alignment.TopCenter)
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                TextField(
//                    value = location,
//                    onValueChange = { location = it },
//                    placeholder = { Text("Enter the city") }
//                )//Textfield
//
//                //Spacer
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Button(onClick = { timeAtLocation = calculateCurrentTime(location) }) {
//                    Text("Show Time at Location")
//                }//Button
//            }//Column
//
//            //Center: Time
//            Text(
//                text = timeAtLocation,
//                style = MaterialTheme.typography.headlineLarge,
//                modifier = Modifier.align(Alignment.Center)
//            )//Text
//        }//Box
//    }//Material Theme
//}//Composable
//
////Timezones
//val mapCityToTimeZone = mapOf(
//    "são paulo" to "America/Sao_Paulo",
//    "new york" to "America/New_York",
//    "madrid" to "Europe/Madrid",
//    "tokyo" to "Asia/Tokyo"
//)
//
//@OptIn(ExperimentalTime::class)
//fun calculateCurrentTime(location: String): String
//{
//    val city = location.lowercase().trim()
//    val timezoneId = mapCityToTimeZone[city] ?: return "no city selected"
//
//    val timezone = TimeZone.of(timezoneId)
//    val now = Clock.System.now().toLocalDateTime(timezone)
//    val localTime: LocalTime = now.time
//
//    // Formata HH:mm:ss sem String.format
//    val hour = localTime.hour.toString().padStart(2, '0')
//    val minute = localTime.minute.toString().padStart(2, '0')
//    val second = localTime.second.toString().padStart(2, '0')
//
//    return "$hour:$minute:$second"
//}
