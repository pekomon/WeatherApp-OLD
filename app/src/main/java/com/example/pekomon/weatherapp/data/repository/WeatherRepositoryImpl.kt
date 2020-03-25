package com.example.pekomon.weatherapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.pekomon.weatherapp.data.db.CurrentWeatherDao
import com.example.pekomon.weatherapp.data.db.entity.CurrentWeatherEntity
import com.example.pekomon.weatherapp.data.network.WeatherNetworkDataSource
import com.example.pekomon.weatherapp.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class WeatherRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : WeatherRepository {

    init {
        weatherNetworkDataSource.downLoadedCurrentWeather.observeForever{ newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<CurrentWeatherEntity> {
        return withContext(Dispatchers.IO) {
            initWeatherData(metric)
            return@withContext currentWeatherDao.getWeather()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(CurrentWeatherEntity(fetchedWeather))
        }
    }

    private suspend fun initWeatherData(metric: Boolean) {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1))) {
            fetchCurrentWeather(metric)
        }
    }

    private suspend fun fetchCurrentWeather(metric: Boolean) {

        val unitFormat = if (metric) "metric" else "imperial"

        weatherNetworkDataSource.fetchCurrentWeather(
            "Helsinki",
            Locale.getDefault().language,
            unitFormat
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}