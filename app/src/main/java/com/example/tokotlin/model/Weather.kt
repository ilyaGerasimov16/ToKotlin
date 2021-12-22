package com.example.tokotlin.model

data class Weather(val city: City = getDefaultCity(), val temperature:Int = 20, val feelsLike:Int = 22)

data class City(val name:String, val lon:Double, val lat:Double)

fun getDefaultCity() = City("Москва", 37.5, 55.5)
