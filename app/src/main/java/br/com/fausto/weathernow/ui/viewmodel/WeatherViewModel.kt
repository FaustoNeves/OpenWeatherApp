package br.com.fausto.weathernow.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fausto.weathernow.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val _city = MutableLiveData<String>()
    val city: LiveData<String> = _city

    private val _country = MutableLiveData<String>()
    val country: LiveData<String> = _country

    fun getWeather(cityName: String) {
        viewModelScope.launch {
            try {
                val weatherResult = weatherRepository.getWeather(cityName)
                _city.value = weatherResult.name!!
                _country.value = weatherResult.sys!!.country!!
            } catch (exception: Exception) {
                //Error
            }
        }
    }
}