package com.example.tokotlin.repository

import com.example.tokotlin.model.WeatherDTO

interface RepositoryDetails {
    fun getWeatherFromServer(lat:Double, lon:Double, callback: retrofit2.Callback<WeatherDTO>)
}