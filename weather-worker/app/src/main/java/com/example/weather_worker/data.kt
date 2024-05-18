package com.example.weather_worker

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("sys") val sys: Sys,
    @SerializedName("name") val name: String,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("wind") val wind: Wind
)

data class Sys(
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long
)

data class Weather(
    @SerializedName("description") val description: String
)

data class Wind(
    @SerializedName("speed") val speed: Double
)
