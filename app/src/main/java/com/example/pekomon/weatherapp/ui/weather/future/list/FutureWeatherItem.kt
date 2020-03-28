package com.example.pekomon.weatherapp.ui.weather.future.list

import com.example.pekomon.weatherapp.R
import com.example.pekomon.weatherapp.data.db.entry.list.SimpleFutureWeatherEntry
import com.example.pekomon.weatherapp.data.db.typeconverters.WeatherConverter
import com.example.pekomon.weatherapp.internal.glide.GlideApp
//import com.example.pekomon.weatherapp.internal.glide.GlideApp
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_future_weather.view.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


class FutureWeatherItem(
    val weatherEntry: SimpleFutureWeatherEntry
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        // Uhhhh, fixing this is a must-do TODO
        val weathers = WeatherConverter().stringToWeatherList(weatherEntry.description)

        viewHolder.apply {
            containerView.textView_condition.text = weathers?.get(0)?.description
            updateDate()
            updateTemperature()
            updateConditionIcon()
        }
    }

    override fun getLayout() = R.layout.item_future_weather

    private fun GroupieViewHolder.updateDate() {
        val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        containerView.textView_date.text = weatherEntry.date.format(dtFormatter)
    }

    private fun GroupieViewHolder.updateTemperature() {
        //TODO:  unitAbbreviation stuff needs to be added
        containerView.textView_temperature.text = "${weatherEntry.temp}"
    }

    private fun GroupieViewHolder.updateConditionIcon() {

        // TODO: Damn this is ugly... soooo ugly
        val weathers = WeatherConverter().stringToWeatherList(weatherEntry.description)

        // TODO: Some backup plan would be nice here
        if (weathers != null) {
            GlideApp.with(this.containerView)
                .load("https://openweathermap.org/img/wn/${weathers?.get(0)?.icon}.png")
                .into(containerView.imageView_condition_icon)
        }
    }
}


