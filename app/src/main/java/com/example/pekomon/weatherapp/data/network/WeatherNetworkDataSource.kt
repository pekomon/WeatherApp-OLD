package com.example.pekomon.weatherapp.data.network

import androidx.lifecycle.LiveData
import com.example.pekomon.weatherapp.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downLoadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String,
        unitsFormat: String
    )

}