package br.com.fausto.weathernow.data.repository

import br.com.fausto.weathernow.http.weatherapi.response.Weather

interface WeatherRepository {
    suspend fun getWeather(cityName: String): Weather
}