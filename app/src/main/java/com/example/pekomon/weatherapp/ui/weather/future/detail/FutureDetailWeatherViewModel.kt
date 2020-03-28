package com.example.pekomon.weatherapp.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.example.pekomon.weatherapp.data.provider.UnitProvider
import com.example.pekomon.weatherapp.data.repository.WeatherRepository
import com.example.pekomon.weatherapp.internal.lazyDeferred
import com.example.pekomon.weatherapp.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

// Extra care here. Binding this with Kodein must take care of 'date' which is not a constant thing
// like weatherRepository or unitProvider
class FutureDetailWeatherViewModel(
    private val date: LocalDate,
    private val weatherRepository: WeatherRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(weatherRepository, unitProvider) {

    val futureWeather by lazyDeferred {
        weatherRepository.getFutureWeatherByDate(date, super.isMetric)
    }
}
