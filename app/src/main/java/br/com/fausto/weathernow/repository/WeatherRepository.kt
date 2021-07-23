package br.com.fausto.weathernow.repository

import br.com.fausto.weathernow.http.weatherapi.response.Weather

interface WeatherRepository {
    suspend fun getWeatherByCityName(cityName: String): Weather

    suspend fun getWeatherByCoordinates(latitude: String, longitude: String): Weather
}