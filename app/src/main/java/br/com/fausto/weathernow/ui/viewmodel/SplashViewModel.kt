package br.com.fausto.weathernow.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fausto.weathernow.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    fun getWeatherByCoordinates(latitude: String, longitude: String) {
        viewModelScope.launch {
            weatherRepository.getWeatherByCoordinates(latitude, longitude)
        }
    }
}