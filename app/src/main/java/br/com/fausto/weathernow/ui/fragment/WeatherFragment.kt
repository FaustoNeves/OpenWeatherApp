package br.com.fausto.weathernow.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import br.com.fausto.weathernow.R
import br.com.fausto.weathernow.ui.viewmodel.WeatherViewModel

class WeatherFragment : Fragment() {

    private val splashViewModel: WeatherViewModel by activityViewModels()
    private lateinit var cityName: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityName = requireActivity().findViewById(R.id.city)
        splashViewModel.mensagemTeste.observe(viewLifecycleOwner, {
            cityName.setText(it)
        })
    }
}