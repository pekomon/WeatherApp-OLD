package com.example.pekomon.weatherapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pekomon.weatherapp.data.network.response.CurrentWeatherResponse
import com.example.pekomon.weatherapp.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val openWeatherMapApiService: OpenWeatherMapApiService
) : WeatherNetworkDataSource {

    // Mutable val needed
    private val _downLoadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downLoadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downLoadedCurrentWeather

    override suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String,
        unitsFormat: String
    ) {
        try {
            val fetchedCurrentWeather = openWeatherMapApiService
                .getCurrentWeather(location, languageCode, unitsFormat)
            _downLoadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No network", e)
        }
    }

    override suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double,
        languageCode: String,
        unitsFormat: String
    ) {
        try {
            val fetchedCurrentWeather = openWeatherMapApiService
                .getCurrentWeatherByCoords(latitude, longitude, languageCode, unitsFormat)
            _downLoadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No network", e)
        }
    }
}