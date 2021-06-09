package br.com.fausto.weathernow.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.fausto.weathernow.R
import br.com.fausto.weathernow.ui.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var textCity: EditText
    private lateinit var textCountry: EditText
    private lateinit var textResult: TextView
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textCity = findViewById(R.id.city)
        textCountry = findViewById(R.id.country)
        textResult = findViewById(R.id.result)

        weatherViewModel.city.observe(this, {
            textResult.text = it
        })
    }

    fun getWeather(view: View) {
        weatherViewModel.getWeather(textCity.text.toString())
    }
}