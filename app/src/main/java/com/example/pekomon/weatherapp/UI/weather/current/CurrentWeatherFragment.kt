package com.example.pekomon.weatherapp.UI.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

import com.example.pekomon.weatherapp.R
import com.example.pekomon.weatherapp.UI.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.reflect.MalformedParameterizedTypeException

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    // From application class
    override val kodein by closestKodein()

    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()


    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)

        bindUI()

    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        // humm, should viewLifeCycleOwner be replaced by this@CurrentWeatherFragment like it used to be in early days??
        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                return@Observer

            }
            group_loading.visibility = View.GONE
            updateLocation("Los Angeles")
            updateDateToToday()
            updateTemperatures(it.temp, it.tempMax)
            updateCondition("cond: ${it.id}")
            updatePrecipitation(it.tempMax)
            updateWind("SE :) ", it.tempMax)
            updateVisibility(it.tempMax)

            // TODO: parse real icon url
            Glide.with(this@CurrentWeatherFragment)
                .load("https://openweathermap.org/img/wn/01d.png")
                .into(imageView_condition_icon)



        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetric) metric else imperial
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "today"
    }

    private fun updateTemperatures(temperature: Double, feelsLike: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation( "°C", "°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = "Feels like $feelsLike$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        textViewCondition.text = "$condition"
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation( "mm", "in")
        textView_precipitation.text = "Precipitation: $precipitationVolume $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation( "kmh", "mph")
        textView_wind.text = "Wind: $windDirection $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibility: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation( "mm", "in")
        textView_precipitation.text = "Visibility: $visibility $unitAbbreviation"
    }

}
