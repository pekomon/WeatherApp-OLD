package com.example.pekomon.weatherapp.UI.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pekomon.weatherapp.R
import com.example.pekomon.weatherapp.data.OpenWeatherMapApiService
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.*

class CurrentWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)
        // TODO: Use the ViewModel

        val apiService = OpenWeatherMapApiService()

        // Test the connection temporarily
        GlobalScope.launch { Dispatchers.Main {
            val currentWeatherResponse2 = apiService.getCurrentWeather("london")
            textView.text = currentWeatherResponse2.toString()
        } }
    }
}
