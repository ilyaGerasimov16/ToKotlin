package com.example.tokotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tokotlin.model.Repository
import com.example.tokotlin.model.RepositoryImpl
import java.lang.IllegalStateException
import java.lang.Thread.sleep

class MainViewModel(private val lifeData:MutableLiveData<AppState> = MutableLiveData(),
private val repositoryImpl: RepositoryImpl = RepositoryImpl()):ViewModel() {

    fun getLiveData(): LiveData<AppState>{
        return lifeData
    }

    fun getWeatherFromLocalSourceRus() = getWeatherFromLocalServer(true)

    fun getWeatherFromLocalSourceWorld() = getWeatherFromLocalServer(false)

    fun getWeatherFromRemoteSource() = getWeatherFromLocalServer(true)   //в будущем реализуем

    fun getWeatherFromLocalServer(isRussian:Boolean){
        Thread{
            lifeData.postValue(AppState.Loading(0))
            sleep(1000)
                lifeData.postValue(AppState.Success(
                    if (isRussian) {
                        repositoryImpl.getWeatherFromLocalStorageRus()
                    } else {
                        repositoryImpl.getWeatherFromLocalStorageWorld()
                    }))
        }.start()
    }

}