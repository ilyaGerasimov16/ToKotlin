package com.example.tokotlin.view.details

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.tokotlin.BuildConfig
import com.example.tokotlin.model.WeatherDTO
import com.example.tokotlin.utils.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection



class DetailsService(name:String=""): IntentService(name) {


    override fun onHandleIntent(intent: Intent?) {
        intent?.let{
            val lat = intent.getDoubleExtra(BUNDLE_KEY_LAT, 0.0)
            val lon = intent.getDoubleExtra(BUNDLE_KEY_LON, 0.0)
            loadWeather(lat, lon)
        }
    }

    private fun loadWeather(lat:Double, lon:Double){
        lateinit var httpsURLConnection: HttpsURLConnection

            try {
                val url = URL("https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon")
                httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {
                    requestMethod = "GET"
                    readTimeout = 2000
                    addRequestProperty(API_KEY, BuildConfig.WEATHER_API_KEY)
                }
                val bufferedReader = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
                val weatherDTO: WeatherDTO? = Gson().fromJson(convertBufferToResult(bufferedReader), WeatherDTO::class.java )


                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(Intent(BROADCAST_ACTION).apply {
                    putExtra(BUNDLE_KEY_WEATHER, weatherDTO)
                })
            } catch (e: Exception){
                //onWeatherLoaded.onFailed("Ошибка ${e.message}")
            }
            finally {
                httpsURLConnection.disconnect()
            }

    }

    private fun convertBufferToResult(bufferedReader: BufferedReader):String {
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }

}