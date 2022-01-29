package com.example.tokotlin.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tokotlin.databinding.FragmentHistoryBinding
import com.example.tokotlin.model.Weather
import com.example.tokotlin.view.main.OnCityItemClickListener
import com.example.tokotlin.viewModel.AppState
import com.example.tokotlin.viewModel.HistoryViewModel


class HistoryFragment : Fragment(), OnCityItemClickListener {

    private var _binding: FragmentHistoryBinding? = null
    private val binding: FragmentHistoryBinding
        get() {
            return _binding!!
        }

    private val adapter: CitiesHistoryAdapter by lazy {
        CitiesHistoryAdapter(this)
    }

    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // обращаем внимание
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
        viewModel.getAllHistory()
        binding.historyFragmentRecyclerview.adapter = adapter
    }



    private fun renderData(appState: AppState) {
        with(binding) {
            when (appState) {
                is AppState.Error -> {
                    // TODO HW
                }
                is AppState.Loading -> {}
                is AppState.SuccessCity -> {
                    adapter.setWeather(appState.weatherDataCity)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun onItemClick(weather: Weather) {
        //
    }
}