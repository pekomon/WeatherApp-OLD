package com.example.pekomon.weatherapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.pekomon.weatherapp.data.db.CurrentWeatherDao
import com.example.pekomon.weatherapp.data.db.FutureWeatherDao
import com.example.pekomon.weatherapp.data.db.entity.CurrentWeatherEntity
import com.example.pekomon.weatherapp.data.db.entry.SimpleFutureWeatherEntryImpl
import com.example.pekomon.weatherapp.data.network.WeatherNetworkDataSource
import com.example.pekomon.weatherapp.data.network.response.CurrentWeatherResponse
import com.example.pekomon.weatherapp.data.network.response.FutureWeatherResponse
import com.example.pekomon.weatherapp.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime

import java.util.*

private const val FORECAST_DAYS_COUNT = 7

class WeatherRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : WeatherRepository {

    init {
        weatherNetworkDataSource.apply {
            downLoadedCurrentWeather.observeForever{ newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)
            }
            downloadedFutureWeather.observeForever{ newFutureWeather ->
                persistFetchedFutureWeather(newFutureWeather)
            }

        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<CurrentWeatherEntity> {
        return withContext(Dispatchers.IO) {
            initWeatherData(metric)
            return@withContext currentWeatherDao.getWeather()
        }
    }

    override suspend fun getFutureWeatherList(
        startDate: LocalDate,
        metric: Boolean
    ): LiveData<List<SimpleFutureWeatherEntryImpl>> {
        return withContext(Dispatchers.IO) {
            initWeatherData(metric)
            return@withContext futureWeatherDao.getSimpleWeatherEntry(startDate)
        }

    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(CurrentWeatherEntity(fetchedWeather))
        }
    }

    //persistFetchedFutureWeather
    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse) {

        fun deleteOldForecastData() {
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.entries
            futureWeatherDao.insert(futureWeatherList)
            // Locations aren't updated now...  maybe we should do something about it...
        }
    }

    private suspend fun initWeatherData(metric: Boolean) {
        val lastWeather = currentWeatherDao.getWeatherNonLive()

        if (lastWeather == null
            || locationProvider.hasLocationChanged(lastWeather)) {
            fetchCurrentWeather(metric)
            fetchFutureWeather(metric)
            return
        }

        if (isFetchCurrentNeeded(lastWeather.zonedDateTime)) {
            fetchCurrentWeather(metric)
        }

        if (isFetchFutureNeeded()) {
            fetchFutureWeather(metric)
        }
    }

    private suspend fun fetchCurrentWeather(metric: Boolean) {
        val unitFormat = if (metric) "metric" else "imperial"

        var locationString = locationProvider.getPreferredLocationString()
        if (locationString.contains(",")) {
            val coords = locationString.split(",")
            weatherNetworkDataSource.fetchCurrentWeather(
                coords[0].toDouble(),
                coords[1].toDouble(),
                Locale.getDefault().language,
                unitFormat
            )
        } else {
            weatherNetworkDataSource.fetchCurrentWeather(
                locationString,
                Locale.getDefault().language,
                unitFormat
            )
        }
    }

    private suspend fun fetchFutureWeather(metric: Boolean) {
        val unitFormat = if (metric) "metric" else "imperial"

        var locationString = locationProvider.getPreferredLocationString()
        if (locationString.contains(",")) {
            val coords = locationString.split(",")
            weatherNetworkDataSource.fetchFutureWeather(
                coords[0].toDouble(),
                coords[1].toDouble(),
                Locale.getDefault().language,
                unitFormat,
                FORECAST_DAYS_COUNT
            )
        } else {
            weatherNetworkDataSource.fetchFutureWeather(
                locationString,
                Locale.getDefault().language,
                unitFormat,
                FORECAST_DAYS_COUNT
            )
        }
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private fun isFetchFutureNeeded(): Boolean {
        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < FORECAST_DAYS_COUNT
    }
}