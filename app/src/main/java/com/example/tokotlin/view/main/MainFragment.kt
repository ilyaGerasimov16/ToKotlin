package com.example.tokotlin.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tokotlin.R
import com.example.tokotlin.databinding.FragmentMainBinding
import com.example.tokotlin.model.Weather
import com.example.tokotlin.utils.BUNDLE_KEY
import com.example.tokotlin.view.details.DetailsFragment
import com.example.tokotlin.view.details.OnItemClickListener
import com.example.tokotlin.viewModel.AppState
import com.example.tokotlin.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding:FragmentMainBinding
    get(){
        return _binding!!
    }

    private val adapter:MainFragmentAdapter by lazy {
        MainFragmentAdapter(this)
    }

    private var isRussian:Boolean = true

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun initView() {
        with(binding){
            mainFragmentRecyclerView.adapter = adapter
            mainFragmentFAB.setOnClickListener {
                sentRequest()
            }
        }
    }

    private fun sentRequest(){
        isRussian = !isRussian
            with(binding){
                if(isRussian){
                    viewModel.getWeatherFromLocalSourceRus()
                    mainFragmentFAB.setImageResource(R.drawable.ic_russia)
                }else{
                    viewModel.getWeatherFromLocalSourceWorld()
                    mainFragmentFAB.setImageResource(R.drawable.ic_earth)
                }
            }
    }

    private fun renderData(appState: AppState){
        with(binding){
            when(appState){
                is AppState.Error -> {
                    mainFragmentLoadingLayout.visibility = View.GONE
                    root.showSnackBarWithAction(getString(R.string.error),
                        getString(R.string.try_again),
                        { sentRequest() },
                        Snackbar.LENGTH_LONG)
                }
                is AppState.Loading ->{
                    mainFragmentLoadingLayout.visibility = View.VISIBLE
                }
                is AppState.SuccessCity -> {
                    mainFragmentLoadingLayout.visibility = View.GONE
                    adapter.setWeather(appState.weatherDataCity)
                    root.showSnackBarWithoutAction("Success",Snackbar.LENGTH_SHORT)
                }
            }
        }
    }

    private fun View.showSnackBarWithoutAction(text:String,length:Int){
        Snackbar.make(this, text, length).show()
    }

    private fun View.showSnackBarWithAction(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

    override fun onInemClick(weather: Weather) {
        activity?.run {
            supportFragmentManager.beginTransaction().
            add(R.id.container, DetailsFragment.newInstance(
                Bundle().apply {
                putParcelable(BUNDLE_KEY, weather)
            }))
                .addToBackStack("").commit()
        }
    }
}