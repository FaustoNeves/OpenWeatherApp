package br.com.fausto.weathernow.data.remote

import br.com.fausto.weathernow.http.weatherapi.response.Weather

interface WeatherRemoteDatasource {
    suspend fun getWeather(cityName: String): Weather
}