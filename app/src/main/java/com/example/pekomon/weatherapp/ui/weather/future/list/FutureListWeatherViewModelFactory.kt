package com.example.pekomon.weatherapp.ui.weather.future.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pekomon.weatherapp.data.provider.UnitProvider
import com.example.pekomon.weatherapp.data.repository.WeatherRepository

class FutureListWeatherViewModelFactory(
    private val weatherRepository: WeatherRepository,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureListWeatherViewModel(
            weatherRepository,
            unitProvider
        ) as T
    }
}