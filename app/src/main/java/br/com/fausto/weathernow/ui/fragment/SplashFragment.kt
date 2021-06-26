package br.com.fausto.weathernow.ui.fragment

import android.Manifest.permission.ACCESS_COARSE_LOCATION
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
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

    private fun checkCoarseLocationPermission() =
        ActivityCompat.checkSelfPermission(
            requireContext(),
            ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    @DelicateCoroutinesApi
    private fun checkPermissions() {
        val permissionsRequest = mutableListOf<String>()
        if (!checkCoarseLocationPermission()) {
            permissionsRequest.add(ACCESS_COARSE_LOCATION)
        }
        if (permissionsRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionsRequest.toTypedArray(),
                0
            )
        } else if (permissionsRequest.isEmpty()) {
//            displayAnimation()
            getWeather()
        }
    }

    @DelicateCoroutinesApi
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                    displayAnimation()
                }
            }
        }
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
                    Log.e("TAG", "is complete: ")
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
                    ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED) {
            Log.e("TAG", "Request Permissions")
            requestMultiplePermissions.launch(
                arrayOf(ACCESS_COARSE_LOCATION)
            )
        } else {
            Log.e("TAG", "Permission Already Granted")
//            displayAnimation()
            getWeather()
        }
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.e("TAG", "${it.key} = ${it.value}")
            }
            if (permissions[ACCESS_COARSE_LOCATION] == true) {
                getWeather()
//                displayAnimation()
                Log.e("TAG", "Permission granted")
            } else {
//                displayAnimation()
                Log.e("TAG", "Permission not granted")
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