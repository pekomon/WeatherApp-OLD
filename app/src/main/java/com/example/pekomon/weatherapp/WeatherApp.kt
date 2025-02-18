package com.example.pekomon.weatherapp

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.example.pekomon.weatherapp.ui.weather.current.CurrentWeatherViewModelFactory
import com.example.pekomon.weatherapp.data.db.WeatherDatabase
import com.example.pekomon.weatherapp.data.network.*
import com.example.pekomon.weatherapp.data.provider.LocationProvider
import com.example.pekomon.weatherapp.data.provider.LocationProviderImpl
import com.example.pekomon.weatherapp.data.provider.UnitProvider
import com.example.pekomon.weatherapp.data.provider.UnitProviderImpl
import com.example.pekomon.weatherapp.data.repository.WeatherRepository
import com.example.pekomon.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.pekomon.weatherapp.ui.weather.future.detail.FutureDetailWeatherViewModel
import com.example.pekomon.weatherapp.ui.weather.future.detail.FutureDetailWeatherViewModelFactory
import com.example.pekomon.weatherapp.ui.weather.future.list.FutureListWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import org.threeten.bp.LocalDate

class WeatherApp : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherApp))

        // WeatherDatabase() == WeatherDatabase.invoke()
        bind() from singleton { WeatherDatabase(instance())  }
        bind() from singleton { instance<WeatherDatabase>().currentWeatherDao() }
        bind() from singleton { instance<WeatherDatabase>().futureWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OpenWeatherMapApiService(instance())  }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<WeatherRepository>() with singleton { WeatherRepositoryImpl(instance(), instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(), instance()) }
        // Note: This binding is 'from factory' because we need to pass LocalDate to viewmodelFactory
        // Factory is to be called from fragment itself and there we can pass the current date
        bind() from factory { date: LocalDate -> FutureDetailWeatherViewModelFactory(date, instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        //Setting default values to prefs here. To be set only once in first launch with 'false' param
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}