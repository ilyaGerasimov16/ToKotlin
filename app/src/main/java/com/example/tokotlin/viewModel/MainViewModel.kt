package com.example.tokotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.IllegalStateException
import java.lang.Thread.sleep

class MainViewModel(private val lifeData:MutableLiveData<AppState> = MutableLiveData()):ViewModel() {

    fun getLiveData(): LiveData<AppState>{
        return lifeData
    }

    fun getWeatherFromServer(){
        Thread{
            lifeData.postValue(AppState.Loading(0))
            sleep(1000)
            lifeData.postValue(AppState.Error(IllegalStateException("")))

            val rand = (1..40).random()
            if(rand>20){
                lifeData.postValue(AppState.Success("Жарко", ""))
            } else {
                lifeData.postValue(AppState.Error(IllegalStateException("")))
            }

        }.start()
    }

}