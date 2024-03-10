package com.example.weather_app

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.InputStream
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var weatherNameTextView: TextView
    private lateinit var weatherDescriptionTextView: TextView
    private lateinit var weatherTemperature:TextView
    private lateinit var weatherFeelsLike:TextView
    private lateinit var firstWeatherIcon: ImageView
    private lateinit var windDirectionIcon:ImageView
    private lateinit var dayLen: TextView
    private lateinit var toolbar: Toolbar


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        weatherFeelsLike = findViewById(R.id.FeelsLikeValue)
        weatherNameTextView = findViewById(R.id.weatherValue)
        weatherDescriptionTextView = findViewById(R.id.weatherDescription)
        weatherTemperature = findViewById(R.id.tempreatureValue)
        firstWeatherIcon = findViewById(R.id.imageView)
        val citiesArray = resources.getStringArray(R.array.cities).toList()
        spinner = findViewById(R.id.citiesSpinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, citiesArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)
        dayLen = findViewById(R.id.DayLenValue)
        val button = findViewById<Button>(R.id.button)
        button.callOnClick()
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                button.callOnClick()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                spinner.setSelection(0)
                button.callOnClick()
            }
        }
        windDirectionIcon = findViewById(R.id.wind)
    }
    fun getWindDirection(degree: Double): String {
        val directions = listOf("https://i.ibb.co/zs05XRT/n.png", "https://i.ibb.co/7zzYpvQ/ne.png", "https://i.ibb.co/7zzYpvQ/ne.png", "https://i.ibb.co/7zzYpvQ/ne.png", "https://i.ibb.co/WtKzyZG/e.png", "ESE", "https://i.ibb.co/ZSDPmXn/se.png", "https://i.ibb.co/ZSDPmXn/se.png", "https://i.ibb.co/687wx8k/s.png", "https://i.ibb.co/687wx8k/s.png", "https://i.ibb.co/GMnWW6y/sw.png", "https://i.ibb.co/GMnWW6y/sw.png", "https://i.ibb.co/CVNMsnX/w.png", "https://i.ibb.co/CVNMsnX/w.png", "https://i.ibb.co/3c5X7YH/nw.png", "https://i.ibb.co/3c5X7YH/nw.png", "https://i.ibb.co/zs05XRT/n.png")
        val index = ((degree + 11.25) / 22.5).toInt() % 16
        return directions[index]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.lang_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.en_lang -> {
                setLocale("en")
                true
            }

            R.id.ru_lang -> {
                setLocale("ru")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        baseContext.resources.updateConfiguration(
            configuration,
            baseContext.resources.displayMetrics
        )
        recreate()
    }



    suspend fun loadWeather() {
            val API_KEY = getString(R.string.api_key)
            val weatherURL =
                "https://api.openweathermap.org/data/2.5/weather?q=${spinner.selectedItem}&appid=$API_KEY&units=metric";
            try {
                val stream = URL(weatherURL).getContent() as InputStream
                val data = Scanner(stream).nextLine()
                Log.d("Received data", data)
                val sourceJson = JSONObject(data)

                CoroutineScope(Dispatchers.Main).launch {
                    weatherTemperature.text =
                        sourceJson.getJSONObject("main").getDouble("temp").toString()
                    weatherFeelsLike.text =
                        sourceJson.getJSONObject("main").getDouble("feels_like").toString()

                    val sunriseTimestamp = sourceJson.getJSONObject("sys").getLong("sunrise")
                    val sunsetTimestamp = sourceJson.getJSONObject("sys").getLong("sunset")
                    val sunriseDate = Date(sunriseTimestamp * 1000)
                    val sunsetDate = Date(sunsetTimestamp * 1000)
                    val durationInMillis = sunsetDate.time - sunriseDate.time
                    val hours = durationInMillis / (60 * 60 * 1000)
                    val minutes = (durationInMillis % (60 * 60 * 1000)) / (60 * 1000)
                    val dayLength = "${hours}h${minutes}min"
                    dayLen.text = dayLength


                    val weatherJson = sourceJson.getJSONArray("weather")
                    var weatherName = "";
                    var weatherDescription = "";
                    val currentJson = JSONObject(weatherJson[0].toString())
                    weatherName = currentJson.getString("main")
                    weatherDescription = currentJson.getString("description")
                    weatherNameTextView.text = weatherName
                    weatherDescriptionTextView.text = weatherDescription
                    val iconUrl =
                        "https://openweathermap.org/img/wn/${currentJson.getString("icon")}@4x.png"
                    Picasso.get().load(iconUrl).into(firstWeatherIcon)
                    val windDegree = sourceJson.getJSONObject("wind").getDouble("deg")
                    val windDirection = getWindDirection(windDegree)
                    Picasso.get().load(windDirection).into(windDirectionIcon)
                }
            } catch (e: Exception){
                Toast.makeText(this, "Something went wrong during OpenWeather service connection!", Toast.LENGTH_SHORT).show()
            }
        }

    fun onClick(v: View) {
        GlobalScope.launch (Dispatchers.IO) {
            loadWeather()
        }
    }
}