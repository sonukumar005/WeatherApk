package com.example.wetherapk.Screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.wetherapk.Data.Local.WeatherEntity
import com.example.wetherapk.Data.NetworkState.NetworkResponse

@Composable
fun WeatherPage(viewModel: WeatherViewModel = hiltViewModel()) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var city by remember { mutableStateOf("") }
    val weatherResult by viewModel.weatherResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter City") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            viewModel.getWeather(city)
            keyboardController?.hide()

        }) {
            Text("Get Weather")
        }
        Spacer(modifier = Modifier.height(16.dp))

        when (val result = weatherResult) {
            is NetworkResponse.Success -> {
                val data = result.data
                WeatherDetails(data)
//                val finalUrl = "https:${data.iconUrl}".replace("64x64", "128x128")
//                Log.d("WeatherPage", "Final Image URL: $finalUrl")
//                AsyncImage(
//                    modifier = Modifier.size(160.dp),
//                    model = finalUrl,
//                    contentDescription = "Condition icon",
//                    onError = { error ->
//                        Log.e("WeatherPage", "Error loading image", error.result.throwable)
//                    }
//                )
//                Text("City: ${data.city}")
//                Text("Temperature: ${data.temperature}°C")
//                Text("Condition: ${data.condition}")

            }

            is NetworkResponse.Error -> {
                Text("Error: ${result.message}")
            }

            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }

            null -> {}
        }
    }
}

@Composable
fun WeatherDetails(data: WeatherEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location icon",
                modifier = Modifier.size(40.dp)
            )
            Text(text = data.region, fontSize = 30.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.country, fontSize = 18.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = " ${data.temperature}°c",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        val finalUrl = "https:${data.iconUrl}".replace("64x64", "128x128")
        Log.d("WeatherPage", "Final Image URL: $finalUrl")
        AsyncImage(
            modifier = Modifier.size(160.dp),
            model = finalUrl,
            contentDescription = "Condition icon",
            onError = { error ->
                Log.e("WeatherPage", "Error loading image", error.result.throwable)
            }
        )
        Text(
            text = data.condition,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Humidity", data.humidity)
                    WeatherKeyVal("Wind Speed", "${data.windSpeed} km/h")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Pressure", "${data.pressure} atm")
                    WeatherKeyVal("Precipitation", "${data.visibility} mm")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Local Time", data.localtime.split(" ")[1])
                    WeatherKeyVal("Local Date", data.localtime.split(" ")[0])
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Visibility", data.visibility)
                }
            }
        }
    }


}


@Composable
fun WeatherKeyVal(key: String, value: String) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }
}