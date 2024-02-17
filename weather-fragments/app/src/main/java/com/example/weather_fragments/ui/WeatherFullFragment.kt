package com.example.weather_fragments.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.weather_fragments.R
import com.example.weather_fragments.data.WeatherData
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL
import java.util.Date


class WeatherFullFragment : Fragment() {
    private lateinit var weatherIconF: ImageView
    private lateinit var windIconF: ImageView
    private lateinit var weatherValueTV: TextView
    private lateinit var weatherDescription: TextView
    private lateinit var temperatureValueTV: TextView
    private lateinit var temperatureFeelsLikeTV: TextView
    private lateinit var dayLenTV: TextView
    private lateinit var result: WeatherData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_full, container, false)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val API_KEY = getString(R.string.token)

        GlobalScope.launch(Dispatchers.IO) {
            val weatherURL =
                "https://api.openweathermap.org/data/2.5/weather?q=Irkutsk&appid=$API_KEY&units=metric"
            val stream = URL(weatherURL).openStream()
            val data = stream.bufferedReader().use { it.readText() }
            val sourceJson = JSONObject(data)
            val realTemperature =
                sourceJson.getJSONObject("main").getDouble("temp").toString()
            val temperatureFeelsLike =
                sourceJson.getJSONObject("main").getDouble("feels_like").toString()

            val sunriseTimestamp = sourceJson.getJSONObject("sys").getLong("sunrise")
            val sunsetTimestamp = sourceJson.getJSONObject("sys").getLong("sunset")
            val sunriseDate = Date(sunriseTimestamp * 1000)
            val sunsetDate = Date(sunsetTimestamp * 1000)
            val durationInMillis = sunsetDate.time - sunriseDate.time
            val hours = durationInMillis / (60 * 60 * 1000)
            val minutes = (durationInMillis % (60 * 60 * 1000)) / (60 * 1000)

            val dayLength = "${hours}h${minutes}min"

            val weatherJson = sourceJson.getJSONArray("weather")
            var weatherName = "";
            var weatherDescription = "";
            val currentJson = JSONObject(weatherJson[0].toString())
            weatherName = currentJson.getString("main")
            weatherDescription = currentJson.getString("description")
            val iconUrl =
                "https://openweathermap.org/img/wn/${currentJson.getString("icon")}@4x.png"
            val windDegree = sourceJson.getJSONObject("wind").getDouble("deg")
            val windDirection = getWindDirection(windDegree)

            result = WeatherData(
                realTemperature,
                temperatureFeelsLike,
                sunriseDate,
                sunsetDate,
                dayLength,
                weatherName,
                weatherDescription,
                iconUrl,
                windDirection
            )
    }.invokeOnCompletion { throwable ->
            GlobalScope.launch(Dispatchers.Main){
                weatherIconF = view.findViewById(R.id.weatherIconF)
                windIconF = view.findViewById(R.id.windIconF)
                weatherValueTV = view.findViewById(R.id.weatherValue)
                weatherDescription = view.findViewById(R.id.weatherDescription)
                temperatureValueTV = view.findViewById(R.id.tempreatureValue)
                temperatureFeelsLikeTV = view.findViewById(R.id.FeelsLikeValue)
                dayLenTV = view.findViewById(R.id.DayLenValue)

                weatherValueTV.text = result.weatherName
                weatherDescription.text = result.weatherDescription
                temperatureValueTV.text = result.realTemperature.toString()
                temperatureFeelsLikeTV.text = result.temperatureFeelsLike
                dayLenTV.text = result.dayLength
                Picasso.get().load(result.iconUrl).into(weatherIconF)
                Picasso.get().load(result.windDirection).into(windIconF)




            }}
    }
    private fun getWindDirection(degree: Double): String {
        val directions = listOf("https://i.ibb.co/zs05XRT/n.png", "https://i.ibb.co/7zzYpvQ/ne.png", "https://i.ibb.co/7zzYpvQ/ne.png", "https://i.ibb.co/7zzYpvQ/ne.png", "https://i.ibb.co/WtKzyZG/e.png", "ESE", "https://i.ibb.co/ZSDPmXn/se.png", "https://i.ibb.co/ZSDPmXn/se.png", "https://i.ibb.co/687wx8k/s.png", "https://i.ibb.co/687wx8k/s.png", "https://i.ibb.co/GMnWW6y/sw.png", "https://i.ibb.co/GMnWW6y/sw.png", "https://i.ibb.co/CVNMsnX/w.png", "https://i.ibb.co/CVNMsnX/w.png", "https://i.ibb.co/3c5X7YH/nw.png", "https://i.ibb.co/3c5X7YH/nw.png", "https://i.ibb.co/zs05XRT/n.png")
        val index = ((degree + 11.25) / 22.5).toInt() % 16
        return directions[index]
    }
}