package com.example.pekomon.weatherapp.UI.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.pekomon.weatherapp.R
import com.example.pekomon.weatherapp.UI.base.ScopedFragment
import com.example.pekomon.weatherapp.data.network.ConnectivityInterceptorImpl
import com.example.pekomon.weatherapp.data.network.OpenWeatherMapApiService
import com.example.pekomon.weatherapp.data.network.WeatherNetworkDataSource
import com.example.pekomon.weatherapp.data.network.WeatherNetworkDataSourceImpl
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.*
import org.kodein.di.Kodein
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


//        val apiService = OpenWeatherMapApiService(ConnectivityInterceptorImpl(this.context!!))
//        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
//
//        weatherNetworkDataSource.downLoadedCurrentWeather.observe(this, Observer {
//            textView.text = it.toString()
//        })
//
//
//        // Test the connection temporarily
//        GlobalScope.launch { Dispatchers.Main {
//            weatherNetworkDataSource.fetchCurrentWeather("Berlin", "en", "metric")
//        } }

    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        // humm, should viewLifeCycleOwner be replaced by this@CurrentWeatherFragment like it used to be in early days??
        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                return@Observer
            }
            textView.text = it.toString()
        })
    }

}
