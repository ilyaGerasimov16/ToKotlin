package com.example.tokotlin.utils

import android.content.Context

class MyPreference(context: Context) {

    val PREFERENCE_NAME = "IsRussianSharedPreference"
    val PREFERENCE_KEY_IS_RUSSIAN = "KEY_IS_RUSSIAN"


    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getIsRussian ():Boolean{
        return preference.getBoolean(PREFERENCE_KEY_IS_RUSSIAN, true)
    }

    fun setIsRussian(isRus:Boolean){
        val editor = preference.edit()
        editor.putBoolean(PREFERENCE_KEY_IS_RUSSIAN,isRus)
        editor.apply()
    }
}