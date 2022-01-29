package com.example.tokotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tokotlin.repository.RepositoryLocalImpl
import java.lang.Thread.sleep

class HistoryViewModel(private val lifeData:MutableLiveData<AppState> = MutableLiveData()):ViewModel(){

private val repositoryLocalImpl: RepositoryLocalImpl by lazy {
    RepositoryLocalImpl()
}


    fun getLiveData(): LiveData<AppState>{
        return lifeData
    }

    fun getAllHistory(){
        val listWeather = repositoryLocalImpl.getAllHistoryWeather()
        lifeData.postValue(AppState.SuccessCity(listWeather))
    }
}