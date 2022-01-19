package com.example.tokotlin.lesson6

import android.app.IntentService
import android.content.Intent
import android.util.Log

const val MAIN_SERVICE_KEY_EXTRAS = "key_"

class MyService(name:String=""): IntentService(name) {

    private val TAG = "myLogs"

    private fun createLogMessage(message:String){
        Log.d(TAG, message)
    }

    override fun onHandleIntent(intent: Intent?) {
        createLogMessage("onHandleIntent ${intent?.getStringExtra(MAIN_SERVICE_KEY_EXTRAS)}")

    }

    override fun onCreate() {
        super.onCreate()
        createLogMessage("onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        createLogMessage("onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createLogMessage("onStartCommand ${flags}")
        return super.onStartCommand(intent, flags, startId)
    }
}