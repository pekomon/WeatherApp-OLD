package com.example.pekomon.weatherapp.ui.weather.future.list

import androidx.lifecycle.ViewModel
import com.example.pekomon.weatherapp.data.provider.UnitProvider
import com.example.pekomon.weatherapp.data.repository.WeatherRepository
import com.example.pekomon.weatherapp.internal.lazyDeferred
import com.example.pekomon.weatherapp.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val unitProvider: UnitProvider
) : WeatherViewModel(weatherRepository, unitProvider) {

    val weatherEntries by lazyDeferred {
        weatherRepository.getFutureWeatherList(LocalDate.now(), super.isMetric)
    }

}
