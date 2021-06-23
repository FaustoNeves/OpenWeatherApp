package br.com.fausto.weathernow.ui.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fausto.weathernow.data.repository.WeatherRepository
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

    @Bindable
    val cityName = MutableLiveData<String>()

    @Bindable
    val countryCode = MutableLiveData<String>()

    @Bindable
    val displayCityName = MutableLiveData<String>()

    @Bindable
    val displayCountryName = MutableLiveData<String>()

    @Bindable
    val displayTemperature = MutableLiveData<String>()

    @Bindable
    val minTemp = MutableLiveData<String>()

    @Bindable
    val maxTemp = MutableLiveData<String>()

    @Bindable
    val humidity = MutableLiveData<String>()

    fun getWeather() {
        if ((cityName.value == null) && (countryCode.value == null)) {
            statusMessage.value = Event("At least one parameter is required")
        } else {
            val searchValue: String? =
                if ((countryCode.value == null) && (cityName.value != null)) {
                    cityName.value
                } else if ((countryCode.value != null) && (cityName.value == null)) {
                    countryCode.value
                } else {
                    cityName.value + "," + countryCode.value
                }
            viewModelScope.launch {
                try {
                    val weatherResult = weatherRepository.getWeatherByCityName(searchValue!!)
                    displayCityName.value = weatherResult.name
                    displayCountryName.value = weatherResult.sys!!.country
                    displayTemperature.value = (weatherResult.main!!.temp!! - 273.15).toString()
                    minTemp.value = (weatherResult.main!!.temp_min!! - 273.15).toString()
                    maxTemp.value = (weatherResult.main!!.temp_max!! - 273.15).toString()
                    humidity.value =
                        "Humidity:" + (weatherResult.main!!.humidity!!).toString() + "%"
                } catch (exception: Exception) {
                    statusMessage.value = Event("Something went bad")
                }
                clearFields()
            }
        }
    }

    private fun clearFields() {
        cityName.value = ""
        countryCode.value = ""
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}