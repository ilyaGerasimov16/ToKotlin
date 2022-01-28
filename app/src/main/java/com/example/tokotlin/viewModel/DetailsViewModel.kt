package com.example.tokotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tokotlin.model.Weather
import com.example.tokotlin.model.WeatherDTO
import com.example.tokotlin.model.getDefaultCity
import com.example.tokotlin.repository.RepositoryLocalImpl
import com.example.tokotlin.repository.RepositoryRemoteImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel(private val lifeData:MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryLocalImpl: RepositoryLocalImpl = RepositoryLocalImpl()
):ViewModel(){

    private val repositoryRemoteImpl: RepositoryRemoteImpl by lazy {
        RepositoryRemoteImpl()
    }

    fun getLiveData(): LiveData<AppState>{
        return lifeData
    }

    fun saveWeather(weather: Weather){
        repositoryLocalImpl.saveWeather(weather)
    }

    fun getWeatherFromRemoteServer(lat:Double, lon:Double){
        lifeData.postValue(AppState.Loading(0))
        repositoryRemoteImpl.getWeatherFromServer(lat,lon,callback)
    }

    fun converterDTOToModel(weatherDTO: WeatherDTO):Weather{
        return Weather(getDefaultCity(),weatherDTO.fact.temp.toInt(),weatherDTO.fact.feelsLike.toInt(),weatherDTO.fact.icon)
    }

    private val callback = object : Callback<WeatherDTO> {
        override fun onFailure(call: Call<WeatherDTO>, t:Throwable) {
            TODO("Not yet implemented")
        }

        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            if (response.body() != null){
                if(response.isSuccessful){
                    response.body()?.let {
                        lifeData.postValue(AppState.SuccessDetails(converterDTOToModel(it)))
                    }
                }else{
                    if(response.code() in 300..399){
                        lifeData.postValue(AppState.Error(error = Throwable("Redirect")))
                    } else if (response.code() in 400..499){
                        lifeData.postValue(AppState.Error(error = Throwable("Client Error")))
                    }
                }
            } else{
                lifeData.postValue(AppState.Error(error = Throwable("Нет связи с сервером")))
            }
        }
    }
}