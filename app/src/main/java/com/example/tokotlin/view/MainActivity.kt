package com.example.tokotlin.view

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.tokotlin.R
import com.example.tokotlin.databinding.ActivityMainBinding
import com.example.tokotlin.lesson6.*
import com.example.tokotlin.view.main.MainFragment
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val receiver = MyBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit()
        }
        startService(Intent(this, MyService::class.java).apply{
            //putExtra(MAIN_SERVICE_KEY_EXTRAS, "Hello")
        })

        val manager = WorkManager.getInstance(this)
        val worker = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInitialDelay(5,TimeUnit.SECONDS)
            .build()
        manager.enqueue(worker)


        registerReceiver(receiver,IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        registerReceiver(receiver,IntentFilter("myAction"))

        sendBroadcast(Intent("myAction").apply {
            putExtra(MAIN_SERVICE_KEY_EXTRAS, "Hello")
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_threads ->{
                supportFragmentManager.beginTransaction().add(R.id.container, ThreadsFragment.newInstance())
                    .addToBackStack("").commit()
                true
            }
            else ->{
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

}