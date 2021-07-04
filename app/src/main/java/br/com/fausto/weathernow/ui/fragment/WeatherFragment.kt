package br.com.fausto.weathernow.ui.fragment

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import br.com.fausto.weathernow.R
import br.com.fausto.weathernow.databinding.FragmentWeatherBinding
import br.com.fausto.weathernow.ui.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_weather.*

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

//        splashViewModel.currentDate.observe(viewLifecycleOwner, {
//            bindingFragment.currentDate.text = it
//        })

        splashViewModel.weather.observe(viewLifecycleOwner, { weather ->
            bindingFragment.let {
                regionName.text = weather.name
                weatherStatus.text = weather.weather!![0].description
                currentWeatherText.text = decimalToCelsius(weather.main?.temp)
                minWeatherText.text = decimalToCelsius(weather.main?.temp_max)
                maxWeatherText.text = decimalToCelsius(weather.main?.temp_min)
                countryAbbreviation.text = weather.sys?.country
            }
        })

        bindingFragment.searchLayout.setOnClickListener {
            navigateToSearchFragment()
        }
    }

    private fun navigateToSearchFragment() {
        findNavController().navigate(R.id.action_weatherFragment_to_searchRegionFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingFragment = null
    }

    private fun decimalToCelsius(doubleText: Double?): String {
        val df = DecimalFormat("#")
        return df.format(doubleText?.minus(273.15)).toString() + "°C"
    }
}