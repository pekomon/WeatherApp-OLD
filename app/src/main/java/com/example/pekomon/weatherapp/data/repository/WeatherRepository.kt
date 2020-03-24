package com.example.pekomon.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.pekomon.weatherapp.data.db.entity.MainWeatherEntry

interface WeatherRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<MainWeatherEntry>
}