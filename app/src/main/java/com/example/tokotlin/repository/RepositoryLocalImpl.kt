package com.example.tokotlin.repository

import com.example.tokotlin.model.City
import com.example.tokotlin.model.Weather
import com.example.tokotlin.model.getRussianCities
import com.example.tokotlin.model.getWorldCities
import com.example.tokotlin.room.App
import com.example.tokotlin.room.HistoryWeatherEntity


class RepositoryLocalImpl: RepositoryCitiesList, RepositoryHistoryWeather{

    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()

    override fun getAllHistoryWeather(): List<Weather> {
        return convertHistoryWeatherEntityToWeather(App.getHistoryWeatherDAO().getAllHistoryWeather())
    }

    fun convertHistoryWeatherEntityToWeather(entityList: List<HistoryWeatherEntity>):List<Weather>{
        return entityList.map {
            Weather(
                City(it.name, 0.0,0.0),it.temperature,it.feelsLike,it.icon
            )
        }
    }

    override fun saveWeather(weather: Weather) {
        Thread{
            App.getHistoryWeatherDAO().insert(
                HistoryWeatherEntity(0,weather.city.name,weather.temperature,weather.feelsLike,weather.icon)
            )
        }.start()

    }

}