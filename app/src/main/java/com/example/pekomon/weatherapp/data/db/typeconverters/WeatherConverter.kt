package com.example.pekomon.weatherapp.data.db.typeconverters

import android.os.Parcel
import android.os.Parcelable
import androidx.room.TypeConverter
import com.example.pekomon.weatherapp.data.db.entity.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class WeatherConverter() : Parcelable {

    constructor(parcel: Parcel) : this() {
    }

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherConverter> {
        override fun createFromParcel(parcel: Parcel): WeatherConverter {
            return WeatherConverter(parcel)
        }

        override fun newArray(size: Int): Array<WeatherConverter?> {
            return arrayOfNulls(size)
        }
    }
}