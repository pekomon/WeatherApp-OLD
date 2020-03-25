package com.example.pekomon.weatherapp.data.db.typeconverters

import androidx.room.TypeConverter
import com.example.pekomon.weatherapp.data.db.entity.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class WeatherConverter {

    @TypeConverter
    fun weatherListToString(weatherList: List<Weather>): String? {
        if (weatherList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Weather?>?>() {}.getType()
        return gson.toJson(weatherList, type)
    }

    @TypeConverter
    fun stringToWeatherList(weatherGson: String?): List<Weather>? {
        if (weatherGson == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Weather?>?>() {}.getType()
        return gson.fromJson<List<Weather>>(weatherGson, type)
    }
}