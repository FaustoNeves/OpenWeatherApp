package br.com.fausto.weathernow.data.remote

import br.com.fausto.weathernow.http.weatherapi.response.Weather
import br.com.fausto.weathernow.http.weatherapi.retrofit.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(private val weatherService: WeatherService) :
    WeatherRemoteDatasource {
    override suspend fun getWeather(cityName: String): Weather {
        return withContext(Dispatchers.IO) {
            weatherService.getCurrentWeather(cityName = cityName)
        }
    }
}