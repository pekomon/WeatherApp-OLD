package com.example.pekomon.weatherapp

import android.app.Application
import androidx.preference.PreferenceManager
import com.example.pekomon.weatherapp.ui.weather.current.CurrentWeatherViewModelFactory
import com.example.pekomon.weatherapp.data.db.WeatherDatabase
import com.example.pekomon.weatherapp.data.network.*
import com.example.pekomon.weatherapp.data.provider.UnitProvider
import com.example.pekomon.weatherapp.data.provider.UnitProviderImpl
import com.example.pekomon.weatherapp.data.repository.WeatherRepository
import com.example.pekomon.weatherapp.data.repository.WeatherRepositoryImpl
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WeatherApp : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherApp))

        // WeatherDatabase() == WeatherDatabase.invoke()
        bind() from singleton { WeatherDatabase(instance())  }
        bind() from singleton { instance<WeatherDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OpenWeatherMapApiService(instance())  }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<WeatherRepository>() with singleton { WeatherRepositoryImpl(instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        //Setting default values to prefs here. To be set only once in first launch with 'false' param
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }

}