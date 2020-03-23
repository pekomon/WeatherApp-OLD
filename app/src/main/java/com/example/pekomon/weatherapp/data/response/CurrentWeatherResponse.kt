package com.example.pekomon.weatherapp.data.response

import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    @SerializedName("main")
    val mainWeatherEntry: MainWeatherEntry,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)