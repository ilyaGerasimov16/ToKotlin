package com.example.tokotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tokotlin.repository.RepositoryLocalImpl
import java.lang.Thread.sleep

class MainViewModel(private val lifeData:MutableLiveData<AppState> = MutableLiveData()):ViewModel(){

private val repositoryLocalImpl: RepositoryLocalImpl by lazy {
    RepositoryLocalImpl()
}


    fun getLiveData(): LiveData<AppState>{
        return lifeData
    }

    fun getWeatherFromLocalSourceRus() = getWeatherFromLocalServer(true)

    fun getWeatherFromLocalSourceWorld() = getWeatherFromLocalServer(false)

    fun getWeatherFromRemoteSource() = getWeatherFromLocalServer(true)   //в будущем реализуем

    private fun getWeatherFromLocalServer(isRussian:Boolean){
        Thread{
            lifeData.postValue(AppState.Loading(0))
            sleep(1000)
                lifeData.postValue(AppState.SuccessCity(
                    with(repositoryLocalImpl){
                        if (isRussian) {
                            getWeatherFromLocalStorageRus()
                        } else {
                            getWeatherFromLocalStorageWorld()
                        }
                    }
                    ))
        }.start()
    }
}