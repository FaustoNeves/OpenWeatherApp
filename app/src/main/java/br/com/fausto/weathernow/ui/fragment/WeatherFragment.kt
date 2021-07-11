package br.com.fausto.weathernow.ui.fragment

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
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
            bindingFragment.let { binding ->
                weather.weather!![0].let {
                    Log.e("weather", "weather: $it.main!!, day/night: ${it.icon}")
                    when (it.main) {
                        "Thunderstorm" -> if (checkLastCharacterForD(it.icon)!!) {
                            setAnimationResource(R.raw.heavy_rain)
                            setBackgroundColor(R.drawable.thunderstorm_background)
                            setTextColor(R.color.white)
                        }
                        "Drizzle", "Rain" -> if (checkLastCharacterForD(it.icon)!!) {
                            setAnimationResource(R.raw.day_rain)
                            setBackgroundColor(R.drawable.snow_day_background)
                            setTextColor(R.color.black)
                        } else {
                            setAnimationResource(R.raw.night_rain)
                            setBackgroundColor(R.drawable.snow_night_background)
                            setTextColor(R.color.white)
                        }
                        "Snow" -> if (checkLastCharacterForD(it.icon)!!) {
                            setAnimationResource(R.raw.day_snow)
                            setBackgroundColor(R.drawable.snow_day_background)
                            setTextColor(R.color.black)
                        } else {
                            setAnimationResource(R.raw.night_snow)
                            setBackgroundColor(R.drawable.snow_night_background)
                            setTextColor(R.color.white)
                        }
                        "Clear" -> if (checkLastCharacterForD(it.icon)!!) {
                            setAnimationResource(R.raw.day_clean)
                            setBackgroundColor(R.drawable.sunny_background)
                            setTextColor(R.color.white)
                        } else {
                            setAnimationResource(R.raw.night_clean)
                            setBackgroundColor(R.drawable.clean_night_background)
                            setTextColor(R.color.white)
                        }
                        "Clouds" -> if (checkLastCharacterForD(it.icon)!!) {
                            setAnimationResource(R.raw.day_cloud)
                            setBackgroundColor(R.drawable.cloud_day_background)
                            setTextColor(R.color.white)
                        } else {
                            setAnimationResource(R.raw.night_cloud)
                            setBackgroundColor(R.drawable.clean_night_background)
                            setTextColor(R.color.white)
                        }
                        else -> {
                            setAnimationResource(R.raw.mist)
                            setBackgroundColor(R.drawable.mist_background)
                            setTextColor(R.color.white)
                        }
                    }
                }
                regionName.text = weather.name
                weatherStatus.text = weather.weather!![0].description
                currentWeatherText.text = decimalToCelsius(weather.main?.temp)
                minWeatherText.text = decimalToCelsius(weather.main?.temp_max)
                maxWeatherText.text = decimalToCelsius(weather.main?.temp_min)
                countryAbbreviation.text = weather.sys?.country
            }.run {
                bindingFragment.weatherAnimation.playAnimation()
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

    private fun setAnimationResource(resource: Int) {
        bindingFragment.weatherAnimation.setAnimation(resource)
    }

    private fun decimalToCelsius(doubleText: Double?) =
        DecimalFormat("#").format(doubleText?.minus(273.15)).toString() + "°C"

    private fun checkLastCharacterForD(stringSequence: String?) = stringSequence?.endsWith("d")

    private fun setBackgroundColor(drawableResource: Int) {
        bindingFragment.mainLayout.background = ResourcesCompat.getDrawable(
            resources,
            drawableResource,
            null
        )
    }

    private fun setTextColor(drawableResource: Int) {
        bindingFragment.countryAbbreviation.setTextColor(
            resources.getColor(
                drawableResource,
                null
            )
        )
        bindingFragment.let {
            countryAbbreviation.setTextColor(
                resources.getColor(
                    drawableResource,
                    null
                )
            )
            regionName.setTextColor(
                resources.getColor(
                    drawableResource,
                    null
                )
            )
        }
    }
}