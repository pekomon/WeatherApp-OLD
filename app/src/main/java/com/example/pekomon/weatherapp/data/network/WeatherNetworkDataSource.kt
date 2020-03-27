package com.example.pekomon.weatherapp.data.network

import androidx.lifecycle.LiveData
import com.example.pekomon.weatherapp.data.network.response.CurrentWeatherResponse
import com.example.pekomon.weatherapp.data.network.response.FutureWeatherResponse

interface WeatherNetworkDataSource {
    val downLoadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String,
        unitsFormat: String
    )

    suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double,
        languageCode: String,
        unitsFormat: String
    )

    suspend fun fetchFutureWeather(
        location: String,
        languageCode: String,
        unitsFormat: String,
        cnt: Int
    )

    suspend fun fetchFutureWeather(
        latitude: Double,
        longitude: Double,
        languageCode: String,
        unitsFormat: String,
        cnt: Int
    )

}