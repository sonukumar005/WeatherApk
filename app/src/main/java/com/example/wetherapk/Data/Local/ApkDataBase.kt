package com.example.wetherapk.Data.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [WeatherEntity::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile private var instance: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_db"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
        }
    }
}
