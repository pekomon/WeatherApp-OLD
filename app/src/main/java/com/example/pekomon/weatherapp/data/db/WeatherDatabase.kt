package com.example.pekomon.weatherapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pekomon.weatherapp.data.db.entity.CurrentWeatherEntity
import com.example.pekomon.weatherapp.data.db.entity.FutureWeatherEntity
import com.example.pekomon.weatherapp.data.db.typeconverters.LocalDateConverter
import com.example.pekomon.weatherapp.data.db.typeconverters.WeatherConverter

@Database(
    entities = [CurrentWeatherEntity::class, FutureWeatherEntity::class],
    version = 1
)
@TypeConverters(WeatherConverter::class, LocalDateConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentWeatherDao() : CurrentWeatherDao
    abstract fun futureWeatherDao(): FutureWeatherDao

    companion object {
        @Volatile private var instance: WeatherDatabase ? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                WeatherDatabase::class.java,
                "weather.db")
                .build()
    }
}