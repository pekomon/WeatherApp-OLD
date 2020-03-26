package com.example.pekomon.weatherapp.data.provider

class LocationProviderImpl : LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: String): Boolean {
        return true
    }

    override suspend fun getPreferredLocationString(): String {
        return "Oulu"
    }
}