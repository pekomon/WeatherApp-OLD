package com.example.pekomon.weatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pekomon.weatherapp.data.db.entity.CURRENT_WEATHER_ID
import com.example.pekomon.weatherapp.data.db.entity.CurrentWeatherEntity

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntity)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID" )
    fun getWeather(): LiveData<CurrentWeatherEntity>
}