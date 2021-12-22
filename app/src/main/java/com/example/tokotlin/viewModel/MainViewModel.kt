package com.example.tokotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class MainViewModel(private val lifeData:MutableLiveData<AppState> = MutableLiveData()):ViewModel() {

    fun getLiveData(): LiveData<AppState>{
        return lifeData
    }

    fun getWeatherFromServer(){
        Thread{
            lifeData.postValue(AppState.LOADING)
            sleep(5000)
            lifeData.postValue(AppState.SUCCESS)
        }.start()
    }

}