package br.com.fausto.weathernow.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import br.com.fausto.weathernow.R
import br.com.fausto.weathernow.databinding.FragmentSearchRegionBinding
import br.com.fausto.weathernow.ui.viewmodel.WeatherViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchRegionFragment : Fragment() {

    private var _bindingFragment: FragmentSearchRegionBinding? = null
    private val bindingFragment: FragmentSearchRegionBinding get() = _bindingFragment!!
    private val splashViewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingFragment = FragmentSearchRegionBinding.inflate(inflater, container, false)
        return bindingFragment.root
    }

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        splashViewModel.message.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })

        bindingFragment.getWeather.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                getWeatherByCityRegionName(
                    bindingFragment.cityRegionName.text.toString(),
                    bindingFragment.countryCode.text.toString()
                )
            }
                .invokeOnCompletion {
                    if (splashViewModel.statusRequest.value!!) {
                        navigateBack()
                    }
                }
        }
    }

    private suspend fun getWeatherByCityRegionName(
        cityNameRegionName: String?,
        countryCode: String?
    ) {
        splashViewModel.getWeatherByCityName(cityNameRegionName, countryCode)
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_searchRegionFragment_to_weatherFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingFragment = null
    }
}