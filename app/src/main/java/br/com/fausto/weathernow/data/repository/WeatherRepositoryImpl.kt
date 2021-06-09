package br.com.fausto.weathernow.data.repository

import br.com.fausto.weathernow.data.remote.WeatherRemoteDatasource
import br.com.fausto.weathernow.http.weatherapi.response.Weather
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteDatasource: WeatherRemoteDatasource
) : WeatherRepository {
    override suspend fun getWeather(cityName: String): Weather {
        return weatherRemoteDatasource.getWeather(cityName = cityName)
    }
}