package com.example.tokotlin.view


import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.tokotlin.R
import com.example.tokotlin.databinding.ActivityMainBinding
import com.example.tokotlin.lesson6.ThreadsFragment
import com.example.tokotlin.room.App
import com.example.tokotlin.view.history.HistoryFragment
import com.example.tokotlin.view.main.MainFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit()
        }

        val sp = getSharedPreferences("TAG",Context.MODE_PRIVATE)

        val activityP = getPreferences(Context.MODE_PRIVATE) // на уровне activity

        val appP = getDefaultSharedPreferences(this) // на уровне приложения

        appP.getString("key", "")

        val editor = appP.edit()
        editor.putString("key","value")
        editor.putString("key2","value2")
        editor.putString("key3","value3")
        editor.putBoolean("key4",true)
        editor.apply()

        val listWeather = App.getHistoryWeatherDAO().getAllHistoryWeather()
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
            R.id.menu_history ->{
                supportFragmentManager.beginTransaction().add(R.id.container, HistoryFragment.newInstance())
                    .addToBackStack("").commit()
                true
            }

            else ->{
                super.onOptionsItemSelected(item)
            }
        }
    }
}