package br.com.fausto.weathernow.data.remote

import br.com.fausto.weathernow.http.weatherapi.response.Weather
import br.com.fausto.weathernow.http.weatherapi.retrofit.WeatherService
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(private val weatherService: WeatherService) :
    WeatherRemoteDatasource {
    override suspend fun getWeatherByCityName(cityName: String): Weather {
        return weatherService.getCurrentWeatherByCityName(cityName = cityName)
    }

    override suspend fun getWeatherByCoordinates(latitude: String, longitude: String): Weather {
        return weatherService.getWeatherByCoordinates(latitude = latitude, longitude = longitude)
    }
}