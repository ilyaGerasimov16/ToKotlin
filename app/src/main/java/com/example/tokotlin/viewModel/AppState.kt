package com.example.tokotlin.viewModel

import com.example.tokotlin.model.Weather
import com.example.tokotlin.model.WeatherDTO

sealed class AppState {
    data class Loading(val progress:Int): AppState()
    data class SuccessCity(val weatherDataCity:List<Weather>): AppState()
    data class SuccessDetails(val weatherData:WeatherDTO): AppState()
    data class Error(val error:Throwable): AppState()
}