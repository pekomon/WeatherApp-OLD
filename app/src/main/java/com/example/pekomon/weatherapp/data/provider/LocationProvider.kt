package com.example.pekomon.weatherapp.data.provider

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: String): Boolean
    suspend fun getPreferredLocationString(): String
}