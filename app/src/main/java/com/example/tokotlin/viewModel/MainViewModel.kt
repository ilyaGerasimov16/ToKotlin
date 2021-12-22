package com.example.tokotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class MainViewModel(private val lifeData:MutableLiveData<Any> = MutableLiveData()):ViewModel() {

    fun getLiveData(): LiveData<Any>{
        return lifeData
    }

    fun getWeatherFromServer(){
        Thread{
            sleep(2000)
            lifeData.postValue(Any())
        }.start()
    }

}