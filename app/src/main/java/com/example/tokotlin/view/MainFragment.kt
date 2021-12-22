package com.example.tokotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tokotlin.R
import com.example.tokotlin.databinding.ActivityMainBinding
import com.example.tokotlin.databinding.FragmentMainBinding
import com.example.tokotlin.viewModel.AppState
import com.example.tokotlin.viewModel.MainViewModel


class MainFragment : Fragment() {

    var _binding: FragmentMainBinding? = null
    private val binding:FragmentMainBinding
    get(){
        return _binding!!
    }


    private lateinit var viewModel:MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
        viewModel.getWeatherFromServer()
    }

    fun renderData(appState: AppState){
        when(appState){
            is AppState.Error -> Toast.makeText(requireContext(), appState.error.message, Toast.LENGTH_SHORT).show()
            is AppState.Loading -> Toast.makeText(requireContext(), "${appState.progress}", Toast.LENGTH_SHORT).show()
            is AppState.Success -> Toast.makeText(requireContext(), appState.weatherData, Toast.LENGTH_SHORT).show()
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