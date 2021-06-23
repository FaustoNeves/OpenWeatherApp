package br.com.fausto.weathernow.http.weatherapi.retrofit

import br.com.fausto.weathernow.BuildConfig
import br.com.fausto.weathernow.http.weatherapi.response.Weather
import retrofit2.http.POST
import retrofit2.http.Query

interface WeatherService {
    @POST("data/2.5/weather")
    suspend fun getCurrentWeatherByCityName(
        @Query("appid") apiKey: String = BuildConfig.API_KEY,
        @Query("q") cityName: String,
    ): Weather

    @POST("data/2.5/weather")
    suspend fun getWeatherByCoordinates(
        @Query("appid") apiKey: String = BuildConfig.API_KEY,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String
    ): Weather
}