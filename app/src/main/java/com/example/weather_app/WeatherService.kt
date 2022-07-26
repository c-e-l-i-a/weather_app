package com.example.weather_app

import android.text.SpannableStringBuilder
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {

    @GET("?appid=2496b9e1c89956e925e59fd50cf92bc1&units=metric")
    fun getWeatherByCity(@Query("q")city: String): Call<WeatherResult>
}