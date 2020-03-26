package com.example.pekomon.weatherapp.data.db.entity

import androidx.room.*
import com.example.pekomon.weatherapp.data.db.typeconverters.WeatherConverter
import com.example.pekomon.weatherapp.data.network.response.CurrentWeatherResponse
import com.google.gson.annotations.SerializedName
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime


const val CURRENT_WEATHER_ID = 0;

@Entity(tableName = "current_weather")
data class CurrentWeatherEntity(
    val base: String,
    @Embedded(prefix = "clouds_")
    val clouds: Clouds,
    val cod: Int,
    @Embedded(prefix = "coord_")
    val coord: Coord,
    val dt: Long,
    @SerializedName("id")
    val cityId: Int,
    @SerializedName("main")
    @Embedded(prefix = "main_")
    val mainWeatherEntry: MainWeatherEntry,
    val name: String,
    @Embedded(prefix = "sys_")
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>?,
    @Embedded(prefix = "wind_")
    val wind: Wind
)  {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID

    @Ignore
    constructor(currentWeather: CurrentWeatherResponse) : this(
        base = currentWeather.base,
        clouds = currentWeather.clouds,
        cod = currentWeather.cod,
        coord = currentWeather.coord,
        dt = currentWeather.dt,
        cityId = currentWeather.id,
        mainWeatherEntry = currentWeather.mainWeatherEntry,
        name = currentWeather.name,
        sys = currentWeather.sys,
        timezone = currentWeather.timezone,
        visibility = currentWeather.visibility,
        weather = currentWeather.weather,
        wind = currentWeather.wind
    )

    val zonedDateTime: ZonedDateTime
        get() {
            val instant = Instant.ofEpochSecond(dt)
            return ZonedDateTime.ofInstant(instant, ZoneOffset.UTC)
        }
}