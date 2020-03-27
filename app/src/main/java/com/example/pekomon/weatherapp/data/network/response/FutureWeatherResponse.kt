package com.example.pekomon.weatherapp.data.network.response


import com.example.pekomon.weatherapp.data.db.entity.City
import com.example.pekomon.weatherapp.data.db.entity.FutureWeatherEntity
import com.google.gson.annotations.SerializedName

data class FutureWeatherResponse(
    val city: City,
    @SerializedName("list")
    val entries: List<FutureWeatherEntity>
)