package com.example.weather_app

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var editCityName: EditText
    lateinit var btnSearch: Button
    lateinit var imageWeather: ImageView
    lateinit var tvTemperature: TextView
    lateinit var tvCityName: TextView
    lateinit var linearLayoutWeather: LinearLayout
    lateinit var tvFeelsLike: TextView
    lateinit var tvHumidity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editCityName = findViewById(R.id.edit_text_city_name)
        btnSearch = findViewById(R.id.search_btn)
        imageWeather = findViewById(R.id.weather_img)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvCityName = findViewById(R.id.tv_city_name)
        linearLayoutWeather = findViewById(R.id.layout_weather)
        tvFeelsLike = findViewById(R.id.feels_like)
        tvHumidity= findViewById(R.id.humidity)
        btnSearch.setTextColor(Color.parseColor("#ffffff"))

        btnSearch.setOnClickListener{
            val city = editCityName.text.toString()
            if (city.isEmpty()){
                Toast.makeText(this, "You need to enter City name",  Toast.LENGTH_LONG).show()
            }else{
                getWeatherByCityName(city)
            }

        }
    }


    fun getWeatherByCityName(city: String){
        // TODO1: create retrofit instance
        val retrofit = Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/weather/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherService = retrofit.create(WeatherService::class.java)

        // TODO2: call weather API
        val result = weatherService.getWeatherByCity(city)

        result.enqueue(object : Callback<WeatherResult>{
            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                // GET TEMP + LOCATION
                if(response.isSuccessful){
                    val result = response.body()

                    tvTemperature.text = "${result?.main?.temp} °C"
                    tvCityName.text = result?.name
                    tvHumidity.text = "Humidity : ${result?.main?.humidity} %"
                    tvFeelsLike.text = "Feels like : ${result?.main?.feels_like}"

                    // GET IMAGE
                    Picasso.get().load("https://api.openweathermap.org/img/w/${result?.weather?.get(0)?.icon}.png").into(imageWeather)

                    // SHOW LAYOUT AFTER SUCCESS LOADING
                    linearLayoutWeather.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                Toast.makeText(applicationContext, "Error server", Toast.LENGTH_LONG).show()
            }

        })
    }
}