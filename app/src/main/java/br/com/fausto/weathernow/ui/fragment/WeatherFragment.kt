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

        splashViewModel.weather.observe(viewLifecycleOwner, { weather ->
            bindingFragment.let {
                regionName.text = weather.name
                weatherStatus.text = weather.weather!![0].toString()
                currentWeather.text = weather.main!!.temp.toString()
                minWeather.text = weather.main!!.temp_min.toString()
                maxWeather.text = weather.main!!.temp_max.toString()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingFragment = null
    }
}