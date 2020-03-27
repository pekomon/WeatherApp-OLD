package com.example.pekomon.weatherapp.data.db.entry

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

class SimpleFutureWeatherEntryImpl(
    @ColumnInfo(name = "dtTxt")
    override val date: LocalDate,
    @ColumnInfo(name = "main_temp")
    override val temp: Double,
    @ColumnInfo(name = "weather_description")
    override val description: String,
    @ColumnInfo(name = "weather_icon")
    override val icon: String
) : SimpleFutureWeatherEntry