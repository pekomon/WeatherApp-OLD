package com.example.pekomon.weatherapp.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * If types weren't normal types but some custom stuff like "MyData", we should
 * annotate vals with something like this:
 *
 * @Embedded(prefix = "mydata_"
 * val myData: MyData
 *
 * ...which would then create n columns with given prefix included in name
 *
 */

const val CURRENT_WEATHER_ID = 0;

@Entity(tableName = "current_weather")
data class MainWeatherEntry(
    @SerializedName("feels_like")
    val feelsLike: Double,
    val pressure: Int,
    val temp: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}