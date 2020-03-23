package com.example.pekomon.weatherapp.data.response


import com.google.gson.annotations.SerializedName

data class MainWeatherEntry(
    @SerializedName("feels_like")
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
)