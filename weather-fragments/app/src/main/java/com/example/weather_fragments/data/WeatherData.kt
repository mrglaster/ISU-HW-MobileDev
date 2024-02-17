package com.example.weather_fragments.data
import java.util.Date

data class WeatherData(
    val realTemperature: String,
    val temperatureFeelsLike: String,
    val sunriseDate: Date,
    val sunsetDate: Date,
    val dayLength: String,
    val weatherName: String,
    val weatherDescription: String,
    val iconUrl: String,
    val windDirection: String
)