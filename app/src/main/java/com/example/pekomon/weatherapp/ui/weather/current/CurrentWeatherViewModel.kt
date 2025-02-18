package com.example.pekomon.weatherapp.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.pekomon.weatherapp.data.provider.UnitProvider
import com.example.pekomon.weatherapp.data.repository.WeatherRepository
import com.example.pekomon.weatherapp.internal.UnitSystem
import com.example.pekomon.weatherapp.internal.lazyDeferred
import com.example.pekomon.weatherapp.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val weatherRepository: WeatherRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(weatherRepository, unitProvider) {
    private val unitSystem = unitProvider.getUnitSystem()

    val weather by lazyDeferred{
        weatherRepository.getCurrentWeather(super.isMetric)
    }
}
