package com.example.pekomon.weatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pekomon.weatherapp.data.db.entity.FutureWeatherEntity
import com.example.pekomon.weatherapp.data.db.entry.detail.DetailFutureWeatherEntry
import com.example.pekomon.weatherapp.data.db.entry.detail.DetailFutureWeatherEntryImpl
import com.example.pekomon.weatherapp.data.db.entry.list.SimpleFutureWeatherEntry
import com.example.pekomon.weatherapp.data.db.entry.list.SimpleFutureWeatherEntryImpl
import org.threeten.bp.LocalDate

@Dao
interface FutureWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<FutureWeatherEntity>)

    // SQLites's date() can parse date from String. For startDate, our TypeConverter will be used
    // automatically to convert LocalDate to String even here in DAO
    @Query("select dtTxt, main_temp, weather as weather_description, main_feelsLike as weather_icon from future_weather where date(dtTxt) >= date(:startDate)")
    fun getSimpleWeatherEntry(startDate: LocalDate): LiveData<List<SimpleFutureWeatherEntryImpl>>

    /*
    @ColumnInfo(name = "dtTxt")
    override val date: LocalDate,
    @ColumnInfo(name = "main_temp")
    override val temp: Double,
    @ColumnInfo(name = "weather_description")
    override val description: String,
    @ColumnInfo(name = "weather_icon")
    override val icon: String
    */

    @Query("select dtTxt, main_temp, weather as weather_description, main_feelsLike as weather_icon from future_weather")
    fun getSimpleWeatherEntryAll(): LiveData<List<SimpleFutureWeatherEntryImpl>>

    @Query("select * from future_weather where date(dtTxt) = date(:startDate)")
    fun getDetailedWeatherEntry(startDate: LocalDate): LiveData<DetailFutureWeatherEntryImpl>

    @Query("select count(id) from future_weather where date(dtTxt) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate): Int

    @Query("select count(id) from future_weather")
    fun countFutureWeatherAll(): Int

    @Query("delete from future_weather where date(dtTxt) = date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: LocalDate)
}