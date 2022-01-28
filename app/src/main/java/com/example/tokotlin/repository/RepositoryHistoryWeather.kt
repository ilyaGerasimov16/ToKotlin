package com.example.tokotlin.repository

import com.example.tokotlin.model.Weather

interface RepositoryHistoryWeather {
    fun getAllHistoryWeather():List<Weather>
    fun saveWeather(weather: Weather)
}