package br.com.fausto.weathernow.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.app.ActivityCompat
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
    lateinit var mainLogo: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mainLogo = requireActivity().findViewById(R.id.main_logo)
        checkPermissions()
//        val buttones = requireActivity().findViewById<Button>(R.id.buttones)
//        buttones.setOnClickListener {
//            findNavController().navigate(R.id.action_splashFragment_to_weatherFragment)
//        }

    }

    private fun checkCoarseLocationPermission() =
        ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    @DelicateCoroutinesApi
    private fun checkPermissions() {
        val permissionsRequest = mutableListOf<String>()
        if (!checkCoarseLocationPermission()) {
            permissionsRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (permissionsRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionsRequest.toTypedArray(),
                0
            )
        } else if (permissionsRequest.isEmpty()) {
//            startAnimation()
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location.let {
                        Log.e("location", location.toString())
                        val resposta = splashViewModel.getWeatherByCoordinates(
                            it!!.latitude.toString(),
                            it.longitude.toString()
                        )
                        splashViewModel.salvarMensagem(resposta.toString())
                    }
                }.addOnCompleteListener {
                    GlobalScope.launch {
                        Log.e("TAG", "is complete: ")
                        delay(1500)
                        requireActivity().runOnUiThread {
//                            goToMainActivity()
                            findNavController().navigate(R.id.action_splashFragment_to_weatherFragment)
                        }
                    }
                }
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
//                    startAnimation()
                    checkPermissions()
                }
            }
        }
    }

    private fun startAnimation() {
        mainLogo.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.bottom_to_original
            )
        )
    }
}