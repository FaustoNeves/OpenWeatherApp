package br.com.fausto.weathernow.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import br.com.fausto.weathernow.databinding.FragmentWeatherBinding
import br.com.fausto.weathernow.ui.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_weather.*
import java.text.DecimalFormat

class WeatherFragment : Fragment() {

    private var _bindingFragment: FragmentWeatherBinding? = null
    private val bindingFragment: FragmentWeatherBinding get() = _bindingFragment!!
    private val splashViewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingFragment = FragmentWeatherBinding.inflate(inflater, container, false)
        return bindingFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        splashViewModel.currentDate.observe(viewLifecycleOwner, {
            bindingFragment.currentDate.text = it
        })

        splashViewModel.weather.observe(viewLifecycleOwner, { weather ->
            bindingFragment.let {
                regionName.text = weather.name
                weatherStatus.text = weather.weather!![0].description
                val df = DecimalFormat("#")
                val currentWeather =
                    df.format(weather.main!!.temp!! - 273.15).toString() + "°C"
                currentWeatherText.text = currentWeather
                val minWeather =
                    df.format(weather.main!!.temp_min!! - 273.15).toString() + "°C"
                minWeatherText.text = minWeather
                val maxWeather =
                    df.format(weather.main!!.temp_max!! - 273.15).toString() + "°C"
                maxWeatherText.text =
                    maxWeather
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingFragment = null
    }
}