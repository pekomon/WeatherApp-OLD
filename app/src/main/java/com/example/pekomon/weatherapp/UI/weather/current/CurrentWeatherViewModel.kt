package com.example.pekomon.weatherapp.UI.weather.current

import androidx.lifecycle.ViewModel
import com.example.pekomon.weatherapp.data.repository.WeatherRepository
import com.example.pekomon.weatherapp.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val weather by lazyDeferred{
        weatherRepository.getCurrentWeather()
    }
}
