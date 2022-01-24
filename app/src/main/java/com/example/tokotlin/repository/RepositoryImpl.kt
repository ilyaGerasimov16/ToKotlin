package com.example.tokotlin.repository

import com.example.tokotlin.BuildConfig
import com.example.tokotlin.model.getRussianCities
import com.example.tokotlin.model.getWorldCities
import com.example.tokotlin.utils.API_KEY
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

class RepositoryImpl: RepositoryCitiesList,RepositoryDetails {

    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()

    override fun getWeatherFromServer(url: String, callback: Callback) {
        val builder = Request.Builder().apply {
            header(API_KEY, BuildConfig.WEATHER_API_KEY)
            //url(YANDEX_API_URL + YANDEX_API_URL_END_POINT + "?lat=${localWeather.city.lat}&" + "lon=${localWeather.city.lon}")
            url(url)
        }

        OkHttpClient().newCall(builder.build()).enqueue(callback)
    }

    //private var client: OkHttpClient? = null


}