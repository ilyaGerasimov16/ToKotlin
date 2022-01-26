package com.example.tokotlin.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import com.example.tokotlin.databinding.FragmentDetailsBinding
import com.example.tokotlin.model.Weather
import com.example.tokotlin.utils.BUNDLE_KEY
import com.example.tokotlin.viewModel.AppState
import com.example.tokotlin.viewModel.DetailsViewModel
import com.google.android.material.snackbar.Snackbar


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
                    loadingLayout.visibility = View.GONE
                    root.showSnackBarOnError("$appState.error.message")
                }
                is AppState.Loading ->{
                    loadingLayout.visibility = View.VISIBLE
                }
                is AppState.Success -> {
                    loadingLayout.visibility = View.GONE
                    val weather = appState.weatherData[0]
                    setWeatherData(weather)
                }
            }
        }
    }
    private fun View.showSnackBarOnError(text:String){
        Snackbar.make(this, text, Snackbar.LENGTH_INDEFINITE).show()
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

            headerIcon.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")

            weatherIcon.loadUrl("https://yastatic.net/weather/i/icons/funky/dark/${weather.icon}.svg")
        }
    }

    private fun ImageView.loadUrl(url:String){
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry{ add(SvgDecoder(this@loadUrl.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
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