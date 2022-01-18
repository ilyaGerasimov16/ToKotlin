package com.example.tokotlin.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tokotlin.databinding.FragmentDetailsBinding
import com.example.tokotlin.model.Weather
import com.example.tokotlin.model.WeatherDTO
import com.example.tokotlin.utils.BUNDLE_KEY
import com.example.tokotlin.utils.WeatherLoader


class DetailsFragment : Fragment(),WeatherLoader.OnWeatherLoaded {

    private var _binding: FragmentDetailsBinding? = null
    private val binding:FragmentDetailsBinding
    get(){
        return _binding!!
    }


    private val weatherLoader = WeatherLoader( this)
    lateinit var localWeather:Weather

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            it.getParcelable<Weather>(BUNDLE_KEY)?.let{
                localWeather = it
                weatherLoader.loadWeather(it.city.lat, it.city.lon)
            }
        }
    }

    private fun setWeatherData(weatherDTO: WeatherDTO){
        with(binding){
            cityName.text = localWeather.city.name
            cityCoordinates.text = "${localWeather.city.lat}${localWeather.city.lon}"
            temperatureValue.text =  "${weatherDTO.fact.temp}"
            feelsLikeValue.text =  "${weatherDTO.fact.feelsLike}"
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        fun newInstance(bundle: Bundle) = DetailsFragment().apply{arguments = bundle}
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onLoaded(weatherDTO: WeatherDTO?) {
        weatherDTO?.let {
            setWeatherData(weatherDTO)
        }
    }

    override fun onFailed() {
        TODO("Not yet implemented")
    }
}