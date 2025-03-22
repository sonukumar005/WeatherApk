package com.example.wetherapk.Data.Local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class WeatherEntity(
    @PrimaryKey val city: String,
    val country: String,
    val region: String,
    val temperature: String,
    val condition: String,
    val iconUrl: String,
    val humidity: String,
    val windSpeed: String,
    val lastUpdated: String,
    val pressure: String,
    val visibility: String,
    val localtime: String,
)