package br.com.fausto.weathernow.ui.fragment

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import br.com.fausto.weathernow.R
import br.com.fausto.weathernow.ui.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class SplashFragment : Fragment() {

    private val splashViewModel: WeatherViewModel by activityViewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var mainLogoP1: TextView
    lateinit var mainLogoP2: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainLogoP1 = requireActivity().findViewById(R.id.main_logo1)
        mainLogoP2 = requireActivity().findViewById(R.id.main_logo2)
        displayAnimation()
        validatePermissions()
    }

    @SuppressLint("MissingPermission")
    fun getWeather() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location.let {
                    Log.e("location", location.toString())
                    splashViewModel.getWeatherByCoordinates(
                        it!!.latitude.toString(),
                        it.longitude.toString()
                    )
                }
            }.addOnCompleteListener {
                GlobalScope.launch {
                    delay(1500)
                    requireActivity().runOnUiThread {
                        findNavController().navigate(R.id.action_splashFragment_to_weatherFragment)
                    }
                }
            }
    }

    private fun validatePermissions() {
        if (requireContext().let {
                ContextCompat.checkSelfPermission(
                    it,
                    ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED) {
            requestMultiplePermissions.launch(
                arrayOf(ACCESS_FINE_LOCATION)
            )
        } else {
            getWeather()
        }
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
            }
            if (permissions[ACCESS_FINE_LOCATION] == true) {
                getWeather()
            } else {
                Toast.makeText(
                    requireContext(),
                    "The app needs location permission to work",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    private fun displayAnimation() {
        mainLogoP1.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.slide_in_right
            )
        )
        mainLogoP2.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.bottom_to_original
            )
        )
    }
}