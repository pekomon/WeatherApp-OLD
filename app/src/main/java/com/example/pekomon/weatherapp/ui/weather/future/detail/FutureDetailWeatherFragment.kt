package com.example.pekomon.weatherapp.ui.weather.future.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

import com.example.pekomon.weatherapp.R
import com.example.pekomon.weatherapp.data.db.typeconverters.LocalDateConverter
import com.example.pekomon.weatherapp.data.db.typeconverters.WeatherConverter
import com.example.pekomon.weatherapp.internal.DateNotFoundException
import com.example.pekomon.weatherapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.future_detail_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.threeten.bp.LocalDate

class FutureDetailWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    //Note: in other fragments viewModelFactory is got 'by instance()'
    // Here we have a factory of factory because we don't have the date yet (it is in safeArgs)
    // That's why it needs to be done differently to be able to pass date in
    private val viewModelFactoryInstanceFactory : ((LocalDate) -> FutureDetailWeatherViewModelFactory) by factory()

    private lateinit var viewModel: FutureDetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_detail_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { FutureDetailWeatherFragmentArgs.fromBundle(it) }
        val date = LocalDateConverter.stringToDate(safeArgs?.dateString) ?: throw DateNotFoundException()

        viewModel = ViewModelProviders.of(this, viewModelFactoryInstanceFactory(date))
            .get(FutureDetailWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val futureWeather = viewModel.futureWeather.await()

        futureWeather.observe(viewLifecycleOwner, Observer { weatherEntry ->
            if (weatherEntry == null) {
                return@Observer
            }

            val weathers = WeatherConverter().stringToWeatherList(weatherEntry.weather)
            val subWeatherEntry = weathers?.get(0)

            updateDate(weatherEntry.date)
            updateTemperatures(weatherEntry.temp, weatherEntry.tempMin, weatherEntry.tempMax)
            subWeatherEntry?.description?.let { updateCondition(it) }
            updatePressure(weatherEntry.pressure)
            updateWindSpeed(weatherEntry.wind)
            updateHumidity(weatherEntry.humidity)
            updateSealevel(weatherEntry.sealevel)

            Glide.with(this@FutureDetailWeatherFragment)
                .load("https://openweathermap.org/img/wn/${subWeatherEntry?.icon}.png")
                .into(imageView_condition_icon)
        })
    }

    private fun updateDate(date: LocalDate) {
        (activity as AppCompatActivity)?.supportActionBar?.subtitle = date.toString()
    }

    private fun updateTemperatures(temp: Double, tempMin: Double, tempMax: Double) {
        //TODO: check metric
        textView_temperature.text = temp.toString()
        textView_min_max_temperature.text = "Min: $tempMin, Max: $tempMax"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updatePressure(pressure: Int) {
        textView_precipitation.text = "Pressure: $pressure"
    }

    private fun updateWindSpeed(wind: Double) {
        textView_wind.text = "Wind: $wind"
    }

    private fun updateHumidity(visibility: Int) {
        textView_visibility.text = "Visibility: $visibility"
    }

    private fun updateSealevel(sealevel: Int) {
        textView_uv.text = "Sea level: $sealevel"
    }
}
