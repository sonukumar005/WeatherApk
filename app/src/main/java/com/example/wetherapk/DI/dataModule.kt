package com.example.wetherapk.DI

import android.content.Context
import com.example.wetherapk.Data.Local.WeatherDao
import com.example.wetherapk.Data.Local.WeatherDatabase
import com.example.wetherapk.Data.repo.repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object dataModule {
    @Singleton
    @Provides
    fun providerepo():repo{
        return repo()

    }
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return WeatherDatabase
            .getInstance(context)
    }

    @Provides
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao {
        return database
            .weatherDao()
    }
}