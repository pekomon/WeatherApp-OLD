package com.example.pekomon.weatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pekomon.weatherapp.data.db.entity.FutureWeatherEntity
import com.example.pekomon.weatherapp.data.db.entry.SimpleFutureWeatherEntryImpl
import org.threeten.bp.LocalDate

@Dao
interface FutureWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<FutureWeatherEntity>)

    // SQLites's date() can parse date from String. For startDate, our TypeConverter will be used
    // automatically to convert LocalDate to String even here in DAO
    @Query("select * from future_weather where date(dtTxt) >= date(:startDate)")
    fun getSimpleWeatherEntry(startDate: LocalDate): LiveData<List<SimpleFutureWeatherEntryImpl>>

    @Query("select * from future_weather")
    fun getSimpleWeatherEntryAll(startDate: LocalDate): LiveData<List<SimpleFutureWeatherEntryImpl>>

    @Query("select count(id) from future_weather where date(dtTxt) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate): Int

    @Query("select count(id) from future_weather")
    fun countFutureWeatherAll(startDate: LocalDate): Int

    @Query("delete from future_weather where date(dtTxt) = date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: LocalDate)
}