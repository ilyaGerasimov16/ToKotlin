package com.example.tokotlin.viewModel

sealed class AppState {
    data class Loading(var progress:Int): AppState()
    data class Success(var weatherData:String, var weatherDataFeelsLike:String): AppState()
    data class Error(var error:Throwable): AppState()
}