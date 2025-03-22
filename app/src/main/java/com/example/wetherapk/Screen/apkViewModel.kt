package com.example.wetherapk.Screen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wetherapk.Data.Local.WeatherDao
import com.example.wetherapk.Data.Local.WeatherEntity
import com.example.wetherapk.Data.NetworkState.NetworkResponse
import com.example.wetherapk.Data.repo.repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: repo, // Repository for API call
    private val weatherDao: WeatherDao // Injected Room DAO
) : ViewModel() {

    private val _weather = MutableStateFlow<NetworkResponse<WeatherEntity>?>(null)
    val weatherResult: StateFlow<NetworkResponse<WeatherEntity>?> = _weather

    fun getWeather(city: String) {
        _weather.value = NetworkResponse.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.getWeather(city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        val weatherEntity = WeatherEntity(
                            city = city,
                            country = it.location.country,
                            region = it.location.region,
                            temperature = it.current.temp_c,
                            condition = it.current.condition.text,
                            iconUrl = it.current.condition.icon,
                            humidity = it.current.humidity,
                            windSpeed = it.current.wind_kph,
                            lastUpdated = it.current.last_updated,
                            pressure = it.current.pressure_mb,
                            visibility = it.current.vis_km,
                            localtime = it.location.localtime
                        )
                        weatherDao.insertWeatherData(weatherEntity) // Store in DB
                        _weather.value = NetworkResponse.Success(weatherEntity) // Load from DB
                    }
                } else {
                    loadCachedData(city)
                }
            } catch (e: Exception) {
                loadCachedData(city)
            }
        }
    }

    private suspend fun loadCachedData(city: String) {
        val cachedData = weatherDao.getWeatherData(city)
        if (cachedData != null) {
            _weather.value = NetworkResponse.Success(cachedData)
        } else {
            _weather.value = NetworkResponse.Error("No data available offline")
        }
    }
}
