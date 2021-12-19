package com.example.tokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.button).setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity,"Button pressed", Toast.LENGTH_SHORT).show()
            }
        })

        val house = House("Оранжевый", 100, 9)
        val newHouse = house.copy("Желтый")

        val color:TextView = findViewById(R.id.textView_color);
        val countFlats:TextView = findViewById(R.id.textView_countFlats);
        val countFloors:TextView = findViewById(R.id.textView_countFloors);

        color.setText(newHouse.color)
        countFlats.setText(newHouse.countFlat.toString())
        countFloors.setText(newHouse.countFloors.toString())
    }
}