package com.example.pekomon.weatherapp.data.db.entry.list

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

interface SimpleFutureWeatherEntry {
    val date: LocalDate  // top level
    val temp: Double // main.temp
    // TODO: should only have weather: String
    val description: String // weather.description
    val icon: String // weather.icon

}