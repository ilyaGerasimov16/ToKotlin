package com.example.tokotlin.repository

import com.example.tokotlin.model.Weather

interface RepositoryCitiesList {
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}