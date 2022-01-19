package com.example.tokotlin.utils

import android.os.Handler
import android.os.Looper
import com.example.tokotlin.BuildConfig
import com.example.tokotlin.model.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class WeatherLoader (private val onWeatherLoaded: OnWeatherLoaded){



    fun loadWeather(lat:Double, lon:Double,){
        lateinit var httpsURLConnection:HttpsURLConnection
        Thread{
            try {
                val url = URL("https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon")
                httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {
                    requestMethod = "GET"
                    readTimeout = 2000
                    addRequestProperty(API_KEY, BuildConfig.WEATHER_API_KEY)
                }
                val bufferedReader = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
                val weatherDTO:WeatherDTO? = Gson().fromJson(convertBufferToResult(bufferedReader), WeatherDTO::class.java )
                Handler(Looper.getMainLooper()).post{
                    onWeatherLoaded.onLoaded(weatherDTO)
                }
            } catch (e:Exception){
                onWeatherLoaded.onFailed("Ошибка ${e.message}")
            }
            finally {
                httpsURLConnection.disconnect()
            }
        }.start()
    }

    private fun convertBufferToResult(bufferedReader: BufferedReader):String {
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }

    interface OnWeatherLoaded{
        fun onLoaded(weatherDTO: WeatherDTO?)
        fun onFailed(message:String)
    }
}

