package br.com.fausto.weathernow.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import br.com.fausto.weathernow.R
import br.com.fausto.weathernow.ui.viewmodel.SplashViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var mainLogo: TextView
    lateinit var mainImage: ImageView

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mainLogo = findViewById(R.id.main_logo)
//        mainImage = findViewById(R.id.main_image)

        checkPermissions()
    }

    private fun checkCoarseLocationPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED


    @DelicateCoroutinesApi
    private fun checkPermissions() {
        val permissionsRequest = mutableListOf<String>()
        if (!checkCoarseLocationPermission()) {
            permissionsRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (permissionsRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsRequest.toTypedArray(), 0)
        } else if (permissionsRequest.isEmpty()) {
            startAnimation()
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location.let {
                        Log.e("location", location.toString())
                        splashViewModel.getWeather(
                            it!!.latitude.toString(),
                            it.longitude.toString()
                        )
                    }
                }.addOnCompleteListener {
                    GlobalScope.launch {
                        Log.e("TAG", "is complete: ")
                        delay(1500)
                        runOnUiThread {
                            goToMainActivity()
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
                    startAnimation()
                    checkPermissions()
                }
            }
        }
    }

    private fun startAnimation() {
        mainLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bottom_to_original))
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}