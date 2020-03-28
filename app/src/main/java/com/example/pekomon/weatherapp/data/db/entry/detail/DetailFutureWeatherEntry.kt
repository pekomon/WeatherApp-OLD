package com.example.pekomon.weatherapp.data.db.entry.detail

import org.threeten.bp.LocalDate


// Humm.. this class has no use, has it?
interface DetailFutureWeatherEntry {

    val date: LocalDate
    val tempMax: Double
    val tempMin: Double
    val temp: Double
    // weather has description, iconurl
    val weather: String
    val wind: Double
    val humidity: Int
    // visibility
    val pressure: Int
    //uv
    val sealevel: Int
}