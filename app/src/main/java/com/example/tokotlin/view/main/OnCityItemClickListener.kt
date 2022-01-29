package com.example.tokotlin.view.main

import com.example.tokotlin.model.Weather

interface OnCityItemClickListener {
    fun onItemClick(weather: Weather)
}