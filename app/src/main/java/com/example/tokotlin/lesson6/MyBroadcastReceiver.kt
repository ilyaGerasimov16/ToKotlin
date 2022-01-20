package com.example.tokotlin.lesson6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyBroadcastReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("myLogs2","onReceive ${intent?.action} ${intent?.getStringExtra(MAIN_SERVICE_KEY_EXTRAS)}")
    }
}