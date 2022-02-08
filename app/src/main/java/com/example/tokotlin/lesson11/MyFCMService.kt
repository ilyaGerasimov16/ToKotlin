package com.example.tokotlin.lesson11

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.tokotlin.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCMService:FirebaseMessagingService() {
    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.d("myLogs","token $s")
    }


    override fun onMessageReceived(message: RemoteMessage) {
        val data = message.data
        if(data.isNotEmpty()){
            val title = data[KEY_TITLE]
            val message = data[KEY_MESSAGE]
            if (!title.isNullOrBlank()&&!message.isNullOrBlank()){
                pushNotification(title,message)
            }

        }
    }

    companion object{
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_1"
        private const val KEY_TITLE = "myTitle"
        private const val KEY_MESSAGE = "myMessage"

    }

    private fun pushNotification(tittle:String,message:String){
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_kotlin_logo)
            setContentTitle(tittle)
            setContentText(message)
            priority = NotificationCompat.PRIORITY_MAX
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelName = "Name $CHANNEL_ID"
            val channelDescription = "Description for $CHANNEL_ID"
            val channelPriority = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(CHANNEL_ID,channelName,channelPriority).apply {
                description = channelDescription
            }

            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID,notificationBuilder.build())

    }
}