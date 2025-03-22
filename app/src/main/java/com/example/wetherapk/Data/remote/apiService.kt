package com.example.wetherapk.Data.remote

import com.example.wetherapk.Data.remote.responce.wetherResponce
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface apiService {
    @GET("v1/current.json")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String
    ): Response<wetherResponce>
}