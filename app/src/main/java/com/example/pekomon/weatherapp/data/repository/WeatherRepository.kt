package com.example.pekomon.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.pekomon.weatherapp.data.db.entity.CurrentWeatherEntity

interface WeatherRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<CurrentWeatherEntity>
}