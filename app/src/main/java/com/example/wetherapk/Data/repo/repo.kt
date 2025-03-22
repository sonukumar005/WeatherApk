package com.example.wetherapk.Data.repo

import com.example.wetherapk.API_KEY
import com.example.wetherapk.Data.remote.apiProvider
import com.example.wetherapk.Data.remote.responce.wetherResponce
import retrofit2.Response

class repo {
    suspend fun getWeather(city: String):Response<wetherResponce>{
        val response = apiProvider
            .providerApi()
            .getWeather(API_KEY, location = city)
        return response
    }




}
