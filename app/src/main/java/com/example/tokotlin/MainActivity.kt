package com.example.tokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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


        var result = if (true) 1 else 2
        val text = "Result: "
        val tv:TextView = findViewById<Button>(R.id.textView_branching)
        tv.setText(text + result.toString())
        Log.d("my logs", "$result")

        result = when(WeatherType.CLOUDY) {
            WeatherType.SUNNY -> 1
            WeatherType.RAINY -> 2
            WeatherType.CLOUDY -> 3
        }
        tv.setText(text + result.toString())
        Log.d("my logs", "$result")


        for (i in 1..20) {
            Log.d("logs cycle", "$i")
        }
    }
}

enum class WeatherType {
    SUNNY,
    RAINY,
    CLOUDY,


}