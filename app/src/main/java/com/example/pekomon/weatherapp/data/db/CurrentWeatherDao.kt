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

    // We need this to be called from Repository because there we can't get 'value' from LiveData, but
    // that always gives just null.
    // For example call method below and get val x = LiveData<CurrentWeatherEntity>
    // When trying to get CurrentWeatherEntity from Livedata by
    // val entity = x.value
    // => that returns just null
    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID" )
    fun getWeatherNonLive(): CurrentWeatherEntity?

}