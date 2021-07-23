package br.com.fausto.weathernow.repository

import br.com.fausto.weathernow.data.remote.WeatherRemoteDatasource
import br.com.fausto.weathernow.http.weatherapi.response.Weather
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteDatasource: WeatherRemoteDatasource
) : WeatherRepository {
    override suspend fun getWeatherByCityName(cityName: String): Weather {
        return weatherRemoteDatasource.getWeatherByCityName(cityName = cityName)
    }

    override suspend fun getWeatherByCoordinates(latitude: String, longitude: String): Weather {
        return weatherRemoteDatasource.getWeatherByCoordinates(
            latitude = latitude,
            longitude = longitude
        )
    }
}