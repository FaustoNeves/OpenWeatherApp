package br.com.fausto.weathernow.ui.fragment

import android.icu.text.DecimalFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
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

        splashViewModel.weather.observe(viewLifecycleOwner, { weather ->
            bindingFragment.let {
                weather.weather!![0].let {
                    Log.e("weather", "weather: $it.main!!, day/night: ${it.icon}")
                    when (it.main) {
                        "Thunderstorm" -> if (checkLastCharacterForD(it.icon)!!) {
                            setAnimationResource(R.raw.heavy_rain)
                            setBackgroundColor(R.drawable.thunderstorm_background)
                        }
                        "Drizzle", "Rain" -> if (checkLastCharacterForD(it.icon)!!) {
                            setAnimationResource(R.raw.day_rain)
                            setBackgroundColor(R.drawable.snow_day_background)
                        } else {
                            setAnimationResource(R.raw.night_rain)
                            setBackgroundColor(R.drawable.snow_night_background)
                        }
                        "Snow" -> if (checkLastCharacterForD(it.icon)!!) {
                            setAnimationResource(R.raw.day_snow)
                            setBackgroundColor(R.drawable.snow_day_background)
                        } else {
                            setAnimationResource(R.raw.night_snow)
                            setBackgroundColor(R.drawable.snow_night_background)
                        }
                        "Clear" -> if (checkLastCharacterForD(it.icon)!!) {
                            setAnimationResource(R.raw.day_clean)
                            setBackgroundColor(R.drawable.sunny_background)
                        } else {
                            setAnimationResource(R.raw.night_clean)
                            setBackgroundColor(R.drawable.clean_night_background)
                        }
                        "Clouds" -> if (checkLastCharacterForD(it.icon)!!) {
                            setAnimationResource(R.raw.day_cloud)
                            setBackgroundColor(R.drawable.cloud_day_background)
                        } else {
                            setAnimationResource(R.raw.night_cloud)
                            setBackgroundColor(R.drawable.clean_night_background)
                        }
                        else -> {
                            setAnimationResource(R.raw.mist)
                            setBackgroundColor(R.drawable.mist_background)
                        }
                    }
                }
                regionName.text = weather.name
                weatherStatus.text = weather.weather!![0].description
                currentWeatherText.text = decimalToCelsius(weather.main?.temp)
                minWeatherText.text = decimalToCelsius(weather.main?.temp_min)
                maxWeatherText.text = decimalToCelsius(weather.main?.temp_max)
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

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.setDecorFitsSystemWindows(false)
            requireActivity().window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            requireActivity().window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }
}