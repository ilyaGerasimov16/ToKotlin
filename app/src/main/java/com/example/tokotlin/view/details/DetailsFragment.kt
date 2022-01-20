package com.example.tokotlin.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.tokotlin.databinding.FragmentDetailsBinding
import com.example.tokotlin.model.Weather
import com.example.tokotlin.model.WeatherDTO
import com.example.tokotlin.utils.*



class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding:FragmentDetailsBinding
    get(){
        return _binding!!
    }
    private val receiver:BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let{
                it.getParcelableExtra<WeatherDTO>(BUNDLE_KEY_WEATHER)?.let {
                    setWeatherData(it)
                }
            }
        }
    }


    lateinit var localWeather:Weather

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{
            it.getParcelable<Weather>(BUNDLE_KEY)?.let{
                localWeather = it
                requireActivity().startService(Intent(requireActivity(),DetailsService::class.java).apply {
                    putExtra(BUNDLE_KEY_LAT,it.city.lat)
                    putExtra(BUNDLE_KEY_LON,it.city.lon)
                })
            }
        }

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, IntentFilter(BROADCAST_ACTION))
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
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)

    }

}