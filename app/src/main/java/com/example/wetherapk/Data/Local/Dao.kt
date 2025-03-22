package com.example.wetherapk.Data.Local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weather: WeatherEntity)

    @Query("SELECT * FROM weather_data WHERE city = :city")
    suspend fun getWeatherData(city: String): WeatherEntity?
}