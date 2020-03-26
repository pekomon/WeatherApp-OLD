package com.example.pekomon.weatherapp.data.provider

import com.example.pekomon.weatherapp.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}