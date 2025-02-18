package com.example.pekomon.weatherapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pekomon.weatherapp.data.network.response.CurrentWeatherResponse
import com.example.pekomon.weatherapp.data.network.response.FutureWeatherResponse
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

    // Mutable val needed
    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()

    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchFutureWeather(
        location: String,
        languageCode: String,
        unitsFormat: String,
        cnt: Int
    ) {
        try {
            val fetchedFutureWeather = openWeatherMapApiService
                .getFutureWeather(location, languageCode, unitsFormat, cnt)
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity","No Connection", e)
        }
    }

    override suspend fun fetchFutureWeather(
        latitude: Double,
        longitude: Double,
        languageCode: String,
        unitsFormat: String,
        cnt: Int
    ) {
        try {
            val fetchedFutureWeather = openWeatherMapApiService
                .getFutureWeatherByCoords(latitude, longitude, languageCode, unitsFormat, cnt)
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity","No Connection", e)
        }
    }
}