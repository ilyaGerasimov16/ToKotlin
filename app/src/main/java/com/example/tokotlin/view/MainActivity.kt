package com.example.tokotlin.view


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.tokotlin.R
import com.example.tokotlin.databinding.ActivityMainBinding
import com.example.tokotlin.lesson10.MapsFragment
import com.example.tokotlin.lesson6.ThreadsFragment
import com.example.tokotlin.lesson9.ContentProviderFragment
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
            R.id.menu_history -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, HistoryFragment.newInstance()).addToBackStack("").commit()
                true
            }
            R.id.menu_content -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, ContentProviderFragment.newInstance()).addToBackStack("").commit()
                true
            }
            R.id.menu_google_maps -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, MapsFragment()).addToBackStack("").commit()
                true
            }
            else ->{
                super.onOptionsItemSelected(item)
            }
        }
    }
}