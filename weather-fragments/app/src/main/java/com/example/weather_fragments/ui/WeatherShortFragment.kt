package com.example.weather_fragments.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.weather_fragments.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL


class WeatherShortFragment : Fragment() {

    private lateinit var temperatureTextView:TextView
    private lateinit var temperatureFeelsLikeTextView: TextView
    private lateinit var weatherIcon:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_weather_short, container, false)
    }

    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val API_KEY = getString(R.string.token)

        var realTemperature = ""
        var temperatureFeelsLike = ""
        var iconUrl = ""

        GlobalScope.launch(Dispatchers.IO) {
            val weatherURL =
                "https://api.openweathermap.org/data/2.5/weather?q=Irkutsk&appid=$API_KEY&units=metric"
            val stream = URL(weatherURL).openStream()
            val data = stream.bufferedReader().use { it.readText() }
            val sourceJson = JSONObject(data)
            realTemperature =
                sourceJson.getJSONObject("main").getDouble("temp").toString()
            temperatureFeelsLike =
                sourceJson.getJSONObject("main").getDouble("feels_like").toString()
            val weatherJson = sourceJson.getJSONArray("weather")
            val currentJson = JSONObject(weatherJson[0].toString())

            iconUrl =
                "https://openweathermap.org/img/wn/${currentJson.getString("icon")}@4x.png"
        }.invokeOnCompletion { throwable ->
            GlobalScope.launch(Dispatchers.Main){
                temperatureTextView = view.findViewById(R.id.temperatureText)
                temperatureFeelsLikeTextView = view.findViewById(R.id.temperatureFeelsLike)
                weatherIcon = view.findViewById(R.id.weatherIcon)

                temperatureTextView.text = "Temperature: $realTemperature"
                temperatureFeelsLikeTextView.text = "Feels like: $temperatureFeelsLike"
                Picasso.get().load(iconUrl).into(weatherIcon)
            }}
    }
}