package com.example.pekomon.weatherapp.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

import com.example.pekomon.weatherapp.R
import com.example.pekomon.weatherapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

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
            updateLocation(it.name)
            updateDateToToday()
            updateTemperatures(it.mainWeatherEntry.temp , it.mainWeatherEntry.feelsLike)
            updateCondition(it.weather?.get(0)?.description.toString())
            // TODO: Get also 'rain' from response
            updatePrecipitation(it.mainWeatherEntry.pressure.toDouble())
            updateWind(it.wind.deg, it.wind.speed )
            updateVisibility(it.visibility)

            // TODO: safety checks please
            Glide.with(this@CurrentWeatherFragment)
                .load("https://openweathermap.org/img/wn/${it.weather?.get(0)?.icon}.png")
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

    private fun updateWind(windDirection: Int, windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation( "m/s", "mph")
        textView_wind.text = "Wind: $windDirection $windSpeed $unitAbbreviation"
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation( "mm", "in")
        textView_precipitation.text = "Humidity: $precipitationVolume $unitAbbreviation"
    }

    private fun updateVisibility(visibility: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation( "m", "miles")
        textView_visibility.text = "Visibility: $visibility $unitAbbreviation"
    }

}
