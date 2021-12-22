package com.example.tokotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tokotlin.R
import com.example.tokotlin.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tv
    }
}