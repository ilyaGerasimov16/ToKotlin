package com.example.tokotlin.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tokotlin.databinding.FragmentDetailsBinding
import com.example.tokotlin.model.Weather

const val BUNDLE_KEY = "JUST_KEY"

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding:FragmentDetailsBinding
    get(){
        return _binding!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            it.getParcelable<Weather>(BUNDLE_KEY)?.run{
                setWeatherData(this)
            }
        }
    }

    private fun setWeatherData(weather: Weather){
        binding.cityName.text = weather.city.name
        binding.cityCoordinates.text = "${weather.city.lat}${weather.city.lon}"
        binding.temperatureValue.text =  "${weather.temperature}"
        binding.feelsLikeValue.text =  "${weather.feelsLike}"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        fun newInstance(bundle: Bundle):DetailsFragment{
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}