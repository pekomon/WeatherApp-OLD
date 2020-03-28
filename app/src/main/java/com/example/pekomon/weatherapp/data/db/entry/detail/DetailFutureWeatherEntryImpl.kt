package com.example.pekomon.weatherapp.data.db.entry.detail

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

data class DetailFutureWeatherEntryImpl(
    @ColumnInfo(name = "dtTxt")
    override val date: LocalDate,
    @ColumnInfo(name = "main_tempMax")
    override val tempMax: Double,
    @ColumnInfo(name = "main_tempMin")
    override val tempMin: Double,
    @ColumnInfo(name = "main_temp")
    override val temp: Double,
    // weather has description, iconurl
    @ColumnInfo(name = "weather")
    override val weather: String,
    @ColumnInfo(name = "wind_speed")
    override val wind: Double,
    @ColumnInfo(name = "main_humidity")
    override val humidity: Int,
    // visibility
    @ColumnInfo(name = "main_pressure")
    override val pressure: Int,
    //uv
    @ColumnInfo(name = "main_seaLevel")
    override val sealevel: Int
) : DetailFutureWeatherEntry