package com.example.tokotlin.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tokotlin.R
import com.example.tokotlin.databinding.FragmentHistoryBinding
import com.example.tokotlin.model.Weather
import com.example.tokotlin.utils.BUNDLE_KEY
import com.example.tokotlin.view.details.DetailsFragment
import com.example.tokotlin.view.details.OnItemClickListener
import com.example.tokotlin.view.main.CitiesAdapter
import com.example.tokotlin.viewModel.AppState
import com.example.tokotlin.viewModel.HistoryViewModel
import com.example.tokotlin.viewModel.MainViewModel

class HistoryFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentHistoryBinding? = null
    private val binding:FragmentHistoryBinding
    get(){
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
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getAllHistory()
        binding.historyFragmentRecyclerview.adapter = adapter
    }

    private fun renderData(appState: AppState){
        with(binding){
            when(appState){

                is AppState.Error -> {
                    // TODO HW
                }
                is AppState.Loading -> {}
                is AppState.SuccessCity -> {
                    adapter.setWeather(appState.weatherDataCity)
                }
                else -> {}
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onInemClick(weather: Weather) {
        //
    }
}