package br.com.fausto.weathernow.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.fausto.weathernow.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}