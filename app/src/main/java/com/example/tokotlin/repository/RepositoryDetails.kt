package com.example.tokotlin.repository

import okhttp3.Callback

interface RepositoryDetails {
    fun getWeatherFromServer(url:String, callback: Callback)
}