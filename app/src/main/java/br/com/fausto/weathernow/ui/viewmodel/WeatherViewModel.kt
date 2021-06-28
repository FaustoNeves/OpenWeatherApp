package br.com.fausto.weathernow.ui.viewmodel

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fausto.weathernow.data.repository.WeatherRepository
import br.com.fausto.weathernow.http.weatherapi.response.Weather
import br.com.fausto.weathernow.ui.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DateFormat
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather> = _weather

    private fun getCurrentWeather(currentWeather: Weather) {
        _weather.value = currentWeather
    }

    private val calendar = Calendar.getInstance()
    private val dateInfo: String = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
    private val _currentDate = MutableLiveData<String>()
    val currentDate: LiveData<String> = _currentDate

    init {
        _currentDate.value = dateInfo
    }

    suspend fun getWeatherByCityName(cityInput: String?, stateCodeInput: String?) {
        if ((cityInput == null) && (stateCodeInput == null)) {
            statusMessage.value = Event("At least one parameter is required")
        } else {
            val searchValue: String =
                if ((stateCodeInput == null) && (cityInput != null)) {
                    cityInput
                } else if ((stateCodeInput != null) && (cityInput == null)) {
                    stateCodeInput
                } else {
                    "$cityInput,$stateCodeInput"
                }
            viewModelScope.launch {
                try {
                    val weatherResult = weatherRepository.getWeatherByCityName(searchValue)
                    getCurrentWeather(weatherResult)
                } catch (exception: Exception) {
                    statusMessage.value = Event("Something went bad")
                }
            }
        }
    }

    fun getWeatherByCoordinates(latitude: String, longitude: String) {
        viewModelScope.launch {
            val weatherResult = weatherRepository.getWeatherByCoordinates(latitude, longitude)
            getCurrentWeather(weatherResult)
        }
    }
}