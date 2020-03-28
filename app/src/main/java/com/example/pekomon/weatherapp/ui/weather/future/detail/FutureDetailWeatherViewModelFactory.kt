package com.example.pekomon.weatherapp.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pekomon.weatherapp.data.provider.UnitProvider
import com.example.pekomon.weatherapp.data.repository.WeatherRepository
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModelFactory(
    private val date: LocalDate,
    private val weatherRepository: WeatherRepository,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureDetailWeatherViewModel(date, weatherRepository, unitProvider) as T
    }
}