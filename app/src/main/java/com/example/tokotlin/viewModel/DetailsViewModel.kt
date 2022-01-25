package com.example.tokotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tokotlin.model.Weather
import com.example.tokotlin.model.WeatherDTO
import com.example.tokotlin.model.getDefaultCity
import com.example.tokotlin.repository.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel(private val lifeData:MutableLiveData<AppState> = MutableLiveData()):ViewModel(){

private val repositoryImpl: RepositoryImpl by lazy {
    RepositoryImpl()
}

    fun getLiveData(): LiveData<AppState>{
        return lifeData
    }

    fun getWeatherFromRemoteServer(lat:Double, lon:Double){
        lifeData.postValue(AppState.Loading(0))
        repositoryImpl.getWeatherFromServer(lat,lon,callback)
    }

    fun converterDTOToModel(weatherDTO: WeatherDTO):List<Weather>{
        return listOf(Weather(getDefaultCity(),weatherDTO.fact.temp.toInt(),weatherDTO.fact.feelsLike.toInt()))
    }

    private val callback = object : Callback<WeatherDTO> {
        override fun onFailure(call: Call<WeatherDTO>, t:Throwable) {
            TODO("Not yet implemented")
        }

        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            if(response.isSuccessful){
                response.body()?.let {
                    lifeData.postValue(AppState.Success(converterDTOToModel(it)))
                }
            }else{
                //
            }
        }
    }
}