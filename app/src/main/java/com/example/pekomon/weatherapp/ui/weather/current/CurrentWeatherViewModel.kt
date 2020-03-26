package com.example.pekomon.weatherapp.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.pekomon.weatherapp.data.provider.UnitProvider
import com.example.pekomon.weatherapp.data.repository.WeatherRepository
import com.example.pekomon.weatherapp.internal.UnitSystem
import com.example.pekomon.weatherapp.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val weatherRepository: WeatherRepository,
    unitProvider: UnitProvider
) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred{
        weatherRepository.getCurrentWeather(isMetric)
    }
}
