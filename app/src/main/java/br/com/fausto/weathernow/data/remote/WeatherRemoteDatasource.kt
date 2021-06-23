package br.com.fausto.weathernow.data.remote

import br.com.fausto.weathernow.http.weatherapi.response.Weather

interface WeatherRemoteDatasource {
    suspend fun getWeatherByCityName(cityName: String): Weather

    suspend fun getWeatherByCoordinates(latitude: String, longitude: String): Weather
}