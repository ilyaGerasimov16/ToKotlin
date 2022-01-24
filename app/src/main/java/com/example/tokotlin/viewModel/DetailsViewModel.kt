package com.example.tokotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tokotlin.model.Weather
import com.example.tokotlin.model.WeatherDTO
import com.example.tokotlin.model.getDefaultCity
import com.example.tokotlin.repository.RepositoryImpl
import com.example.tokotlin.utils.YANDEX_API_URL
import com.example.tokotlin.utils.YANDEX_API_URL_END_POINT
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class DetailsViewModel(private val lifeData:MutableLiveData<AppState> = MutableLiveData()):ViewModel(){

private val repositoryImpl: RepositoryImpl by lazy {
    RepositoryImpl()
}

    fun getLiveData(): LiveData<AppState>{
        return lifeData
    }

    fun getWeatherFromRemoteServer(lat:Double, lon:Double){
        lifeData.postValue(AppState.Loading(0))
        repositoryImpl.getWeatherFromServer(YANDEX_API_URL + YANDEX_API_URL_END_POINT + "?lat=${lat}&" + "lon=${lon}",callback)
    }

    fun converterDTOToModel(weatherDTO: WeatherDTO):List<Weather>{
        return listOf(Weather(getDefaultCity(),weatherDTO.fact.temp.toInt(),weatherDTO.fact.feelsLike.toInt()))
    }

    private val callback = object : Callback {

        override fun onFailure(call: Call, e: IOException) {
            TODO("Not yet implemented")
        }

        override fun onResponse(call: Call, response: Response) {
            if(response.isSuccessful){
                response.body()?.let {
                    val json = it.string()
                    lifeData.postValue(AppState.Success(converterDTOToModel(Gson().fromJson(json, WeatherDTO::class.java ))))
                }
            }else{

            }
        }
    }
}