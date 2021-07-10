package br.com.fausto.weathernow.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fausto.weathernow.data.repository.WeatherRepository
import br.com.fausto.weathernow.http.weatherapi.response.Weather
import br.com.fausto.weathernow.ui.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = statusMessage

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather> = _weather

    private fun getCurrentWeather(currentWeather: Weather) {
        _weather.value = currentWeather
    }

    private val _statusRequest = MutableLiveData(false)
    val statusRequest: LiveData<Boolean> = _statusRequest

//    private val calendar = Calendar.getInstance()
//    private val dateInfo: String = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
//    private val _currentDate = MutableLiveData<String>()
//    val currentDate: LiveData<String> = _currentDate
//    init {
//        _currentDate.value = dateInfo
//    }

    suspend fun getWeatherByCityName(cityInput: String?, stateCodeInput: String?) {
        if (cityInput!!.isEmpty() && stateCodeInput!!.isEmpty()) {
            statusMessage.value = Event("At least one parameter is required")
        } else {
            val searchValue: String =
                if ((stateCodeInput?.isEmpty()!!) && (cityInput.isNotEmpty())) {
                    cityInput
                } else if ((stateCodeInput.isNotEmpty()) && (cityInput.isEmpty())) {
                    stateCodeInput
                } else {
                    "$cityInput,$stateCodeInput"
                }
            try {
                val weatherResult = weatherRepository.getWeatherByCityName(searchValue)
                getCurrentWeather(weatherResult)
                _statusRequest.value = true
            } catch (exception: Exception) {
                statusMessage.value = Event("No results found")
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