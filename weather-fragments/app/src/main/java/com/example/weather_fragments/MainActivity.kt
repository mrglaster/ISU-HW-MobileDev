package com.example.weather_fragments

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.weather_fragments.ui.WeatherDialogFragment

class MainActivity : AppCompatActivity() {
    private lateinit var showWeatherButton:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showWeatherButton = findViewById<Button>(R.id.showWeather)
        showWeatherButton.setOnClickListener {
            val dialog = WeatherDialogFragment()
            dialog.show(supportFragmentManager, "Погода")
        }
    }
}