package com.example.pekomon.weatherapp.data.db.entry

import org.threeten.bp.LocalDate

interface SimpleFutureWeatherEntry {
    val date: LocalDate  // top level
    val temp: Double // main.temp
    val description: String // weather.description
    val icon: String // weather.icon
}