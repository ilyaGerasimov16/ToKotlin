package com.example.tokotlin.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tokotlin.databinding.FragmentMainBinding
import com.example.tokotlin.viewModel.AppState
import com.example.tokotlin.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar


class MainFragment : Fragment() {

    var _binding: FragmentMainBinding? = null
    private val binding:FragmentMainBinding
    get(){
        return _binding!!
    }

    private val adapter = MainFragmentAdapter()
    private var isRussian:Boolean = true


    private lateinit var viewModel:MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
        binding.mainFragmentRecyclerView.adapter = adapter

        binding.mainFragmentFAB.setOnClickListener{
            sentRequest()
        }
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun sentRequest(){
        isRussian = !isRussian
        if(isRussian){
            viewModel.getWeatherFromLocalSourceRus()
        }else{
            viewModel.getWeatherFromLocalSourceWorld()
        }
    }

    private fun renderData(appState: AppState){
        when(appState){
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Snackbar.make(binding.root,"Error", Snackbar.LENGTH_INDEFINITE).setAction("Попробовать ещё раз")
                {
                    sentRequest()
                }.show()
            }
            is AppState.Loading ->{
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE

                adapter.setWeather(appState.weatherData)

                /*
                binding.cityName.text = appState.weatherData.city.name
                binding.cityCoordinates.text = "${appState.weatherData.city.lat} ${appState.weatherData.city.lon}"
                binding.temperatureValue.text =  "${appState.weatherData.temperature}"
                binding.feelsLikeValue.text =  "${appState.weatherData.feelsLike}"*/

                Snackbar.make(binding.root,
                    "Success", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}