package com.example.tokotlin.view.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tokotlin.R
import com.example.tokotlin.databinding.FragmentMainBinding
import com.example.tokotlin.model.City
import com.example.tokotlin.model.Weather
import com.example.tokotlin.utils.BUNDLE_KEY
import com.example.tokotlin.utils.MIN_DISTANCE
import com.example.tokotlin.utils.REFRESH_PERIOD
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

    private val adapter:CitiesAdapter by lazy {
        CitiesAdapter(this)
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
            mainFragmentFABLocation.setOnClickListener {
                checkPermission()
            }
        }
    }

    private fun checkPermission(){
        context?.let {
            when{
                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED -> {
                    getLocation()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showDialogRatio()
                }
                else ->{
                    myRequestPermissions()
                }
            }
        }
    }

    private fun showAddressDialog(address:String, location:Location){
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_address_tittle))
            .setMessage(address)
            .setPositiveButton(getString(R.string.dialog_address_get_weather)){_,_ ->
                toDetails(Weather(City(address,location.latitude,location.longitude)))
            }
            .setNegativeButton(getString(R.string.dialog_reject)) {dialog, _ -> dialog.dismiss()}
            .create()
            .show()
    }

    private fun getAddress(location: Location){
        Thread{
            val geocoder = Geocoder(requireContext())
            val listAddress = geocoder.getFromLocation(location.latitude,location.longitude,1)
            requireActivity().runOnUiThread{
                showAddressDialog(listAddress[0].getAddressLine(0),location)
            }
        }.start()
    }

    private val locationListener = object : LocationListener{
        override fun onLocationChanged(location: Location) {
            getAddress(location)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }

    }

    private fun getLocation(){
        activity?.let {
            if(ContextCompat.checkSelfPermission(it,Manifest.permission.ACCESS_FINE_LOCATION)
                ==PackageManager.PERMISSION_GRANTED){
                val locationManager = it.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    val providerGps = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                    providerGps?.let {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            REFRESH_PERIOD,
                            MIN_DISTANCE,
                            locationListener)
                    }

                } else{
                    val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    lastLocation?.let {
                        getAddress(it)
                    }
                }
            }
        }
    }

    private fun showDialog(){

    }

    private val REQUEST_CODE = 991
    private fun myRequestPermissions(){
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_CODE){

            when{
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) ->{
                    getLocation()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showDialogRatio()
                }
                else -> {
//
                }
            }
        }
    }


    private fun showDialogRatio(){
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_rationale_title))
            .setMessage(getString(R.string.dialog_rationale_meaasge))
            .setPositiveButton(getString(R.string.dialog_rationale_give_access)){_,_ ->
                myRequestPermissions()
            }
            .setNegativeButton(getString(R.string.dialog_rationale_decline)) {dialog, _ -> dialog.dismiss()}
            .create()
            .show()
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
                else -> {}
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
        toDetails(weather)
    }

    private fun toDetails(weather: Weather) {
        activity?.run {
            supportFragmentManager.beginTransaction().add(
                R.id.container, DetailsFragment.newInstance(
                    Bundle().apply {
                        putParcelable(BUNDLE_KEY, weather)
                    })
            )
                .addToBackStack("").commit()
        }
    }
}