package com.example.pekomon.weatherapp.data.provider

import com.example.pekomon.weatherapp.data.db.entity.CurrentWeatherEntity

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeather: CurrentWeatherEntity): Boolean
    suspend fun getPreferredLocationString(): String
}