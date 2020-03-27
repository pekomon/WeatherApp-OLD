package com.example.pekomon.weatherapp.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "future_weather", indices = [Index(value = ["dtTxt"], unique = true)])
data class FutureWeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @Embedded(prefix = "clouds_")
    val clouds: Clouds,
    val dt: Int,
    @SerializedName("dt_txt")
    val dtTxt: String,
    @Embedded(prefix = "main_")
    val main: Main,
    val weather: List<Weather>,
    @Embedded(prefix = "wind_")
    val wind: Wind
)