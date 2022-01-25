package com.example.tokotlin.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.bumptech.glide.Glide
import com.example.tokotlin.databinding.FragmentDetailsBinding
import com.example.tokotlin.model.Weather
import com.example.tokotlin.utils.BUNDLE_KEY
import com.example.tokotlin.viewModel.AppState
import com.example.tokotlin.viewModel.DetailsViewModel
import com.squareup.picasso.Picasso


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding:FragmentDetailsBinding
    get(){
        return _binding!!
    }

    private val viewModel:DetailsViewModel by lazy{
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    private fun renderData(appState: AppState){
        with(binding){
            when(appState){
                is AppState.Error -> {
                    //
                }
                is AppState.Loading ->{
                    //
                }
                is AppState.Success -> {
                    val weather = appState.weatherData[0]
                    setWeatherData(weather)
                }
            }
        }
    }


    private lateinit var localWeather:Weather

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
        arguments?.let{
            it.getParcelable<Weather>(BUNDLE_KEY)?.let {
                localWeather = it
                viewModel.getWeatherFromRemoteServer(localWeather.city.lat, localWeather.city.lon )
            }
        }
    }

    private fun setWeatherData(weather: Weather){
        with(binding){
            cityName.text = localWeather.city.name
            cityCoordinates.text = "${localWeather.city.lat}${localWeather.city.lon}"
            temperatureValue.text =  "${weather.temperature}"
            feelsLikeValue.text =  "${weather.feelsLike}"

            /*Glide.with(headerIcon.context).load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
                .into(headerIcon)

            Picasso.get().load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
                .into(headerIcon)*/

            headerIcon.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
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
}