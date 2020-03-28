package com.example.pekomon.weatherapp.data.db.typeconverters

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

object LocalDateConverter {
    @TypeConverter
    @JvmStatic
    fun stringToDate(str: String?) = str?.let {
        LocalDate.parse(it.substring(0,10))
    }

    @TypeConverter
    @JvmStatic
    fun dateToString(dateTime: LocalDate) = dateTime?.format(DateTimeFormatter.ISO_LOCAL_DATE)
}