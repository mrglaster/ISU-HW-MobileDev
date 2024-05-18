package com.example.weather_worker
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    private lateinit var firstCity: EditText
    private lateinit var secondCity: EditText

    private lateinit var weatherFirst: TextView
    private lateinit var weatherSecond: TextView

    private lateinit var windFirst: TextView
    private lateinit var windSecond: TextView

    private lateinit var cityFirstTV: TextView
    private lateinit var citySecondTV: TextView

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstCity = findViewById(R.id.editTextCity)
        secondCity = findViewById(R.id.editTextCity2)

        cityFirstTV = findViewById(R.id.cityName1)
        citySecondTV = findViewById(R.id.cityName2)

        weatherFirst = findViewById(R.id.textViewWeather1)
        weatherSecond = findViewById(R.id.textViewWeather2)
        windFirst = findViewById(R.id.textViewWind1)
        windSecond = findViewById(R.id.textViewWind2)

        val buttonRefresh = findViewById<Button>(R.id.buttonRefresh)
        buttonRefresh.setOnClickListener {
            val cityNameText = firstCity.text.toString()
            val cityNameText2 = secondCity.text.toString()

            if (cityNameText.isEmpty() || cityNameText2.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "FILL ALL FIELDS",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                getWeather(cityNameText) { weatherData ->
                    cityFirstTV.text = cityNameText
                    weatherFirst.text = "Weather: ${weatherData.weather[0].description}"
                    windFirst.text = "Wind: ${weatherData.wind.speed}"
                }
                getWeather(cityNameText2) { weatherData ->
                    citySecondTV.text = cityNameText2
                    weatherSecond.text = "Weather: ${weatherData.weather[0].description}"
                    windSecond.text = "Wind: ${weatherData.wind.speed}"
                }
            }
        }
    }

   @OptIn(DelicateCoroutinesApi::class)
    private fun getWeather(cityName: String, callback: (WeatherData) -> Unit) {
        val API_KEY = getString(R.string.api_key)
        val weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$API_KEY&units=metric"

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val stream = URL(weatherURL).openStream()
                val data = stream.bufferedReader().use { it.readText() }
                val weatherData = Gson().fromJson(data, WeatherData::class.java)
                launch(Dispatchers.Main) {
                    callback(weatherData)
                }
            } catch (e: Exception) {
                Toast.makeText(
                    applicationContext,
                    "CITY NOT FOUND!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}