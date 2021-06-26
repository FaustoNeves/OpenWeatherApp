package br.com.fausto.weathernow.ui.viewmodel

import androidx.databinding.Observable
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
    ViewModel(), Observable {

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather> = _weather

    fun getCurrentWeather(currentWeather: Weather) {
        _weather.value = currentWeather
    }

    val cityName = MutableLiveData<String>()

    val countryCode = MutableLiveData<String>()

    val displayCityName = MutableLiveData<String>()

    val displayCountryName = MutableLiveData<String>()

    val displayTemperature = MutableLiveData<String>()

    val minTemp = MutableLiveData<String>()

    val maxTemp = MutableLiveData<String>()

    val humidity = MutableLiveData<String>()

//    fun getWeatherByCityName() {
//        if ((cityName.value == null) && (countryCode.value == null)) {
//            statusMessage.value = Event("At least one parameter is required")
//        } else {
//            val searchValue: String? =
//                if ((countryCode.value == null) && (cityName.value != null)) {
//                    cityName.value
//                } else if ((countryCode.value != null) && (cityName.value == null)) {
//                    countryCode.value
//                } else {
//                    cityName.value + "," + countryCode.value
//                }
//            viewModelScope.launch {
//                try {
//                    val weatherResult = weatherRepository.getWeatherByCityName(searchValue!!)
//                    displayCityName.value = weatherResult.name
//                    displayCountryName.value = weatherResult.sys!!.country
//                    displayTemperature.value = (weatherResult.main!!.temp!! - 273.15).toString()
//                    minTemp.value = (weatherResult.main!!.temp_min!! - 273.15).toString()
//                    maxTemp.value = (weatherResult.main!!.temp_max!! - 273.15).toString()
//                    humidity.value =
//                        "Humidity:" + (weatherResult.main!!.humidity!!).toString() + "%"
//                } catch (exception: Exception) {
//                    statusMessage.value = Event("Something went bad")
//                }
//                clearFields()
//            }
//        }
//    }

    fun getWeatherByCoordinates(latitude: String, longitude: String) {
        viewModelScope.launch {
            val weatherResult = weatherRepository.getWeatherByCoordinates(latitude, longitude)
            getCurrentWeather(weatherResult)
//            displayCityName.value = weatherResult.name!!
//            displayCountryName.value = weatherResult.sys!!.country!!
//            displayTemperature.value = (weatherResult.main!!.temp!! - 273.15).toString()
//            minTemp.value = (weatherResult.main!!.temp_min!! - 273.15).toString()
//            maxTemp.value = (weatherResult.main!!.temp_max!! - 273.15).toString()
//            humidity.value =
//                "Humidity:" + (weatherResult.main!!.humidity!!).toString() + "%"
        }
    }

//    fun getWeatherByCoordinates(latitude: String, longitude: String): Weather {
//        return runBlocking {
//            weatherRepository.getWeatherByCoordinates(latitude, longitude)
//        }
//    }

    private fun clearFields() {
        cityName.value = ""
        countryCode.value = ""
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}