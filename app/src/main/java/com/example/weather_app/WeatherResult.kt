package com.example.weather_app

data class WeatherResult (
    var name: String,
    var main: MainJson,
    var weather: Array<WeatherJson>
        )

data class MainJson(
    var temp: Double,
    var feels_like: Double,
    var humidity: Int
)

data class WeatherJson(
    var id: Int,
    var icon: String
)