package br.com.fausto.weathernow.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import br.com.fausto.weathernow.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    private val weatherViewModel: WeatherViewModel by viewModels()
//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
//        val navController = navHostFragment.navController

//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding.viewModel = weatherViewModel
//        binding.lifecycleOwner = this
//
//        weatherViewModel.message.observe(this, {
//            it.getContentIfNotHandled()?.let { message ->
//                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//            }
//        })
    }
}