package br.com.fausto.weathernow.http.weatherapi.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPI {
    fun createAPI(): WeatherService? {
        return Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/data/2.5/weather")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }
}