package com.example.pekomon.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.pekomon.weatherapp.data.db.entity.CurrentWeatherEntity
import com.example.pekomon.weatherapp.data.db.entry.detail.DetailFutureWeatherEntry
import com.example.pekomon.weatherapp.data.db.entry.detail.DetailFutureWeatherEntryImpl
import com.example.pekomon.weatherapp.data.db.entry.list.SimpleFutureWeatherEntry
import com.example.pekomon.weatherapp.data.db.entry.list.SimpleFutureWeatherEntryImpl
import org.threeten.bp.LocalDate

interface WeatherRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<CurrentWeatherEntity>

    suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean): LiveData<List<SimpleFutureWeatherEntryImpl>>

    suspend fun getFutureWeatherByDate(date: LocalDate, metric: Boolean): LiveData<DetailFutureWeatherEntryImpl>
}