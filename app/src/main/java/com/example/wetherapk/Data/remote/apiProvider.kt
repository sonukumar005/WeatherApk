package com.example.wetherapk.Data.remote

import com.example.wetherapk.BASE_URL
import com.example.wetherapk.Data.remote.apiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object apiProvider {
    fun providerApi() = Retrofit.Builder().baseUrl(BASE_URL).client(OkHttpClient.Builder().build())
        .addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(apiService::class.java)
}