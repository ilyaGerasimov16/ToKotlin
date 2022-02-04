package com.example.tokotlin.lesson10

import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tokotlin.R
import com.example.tokotlin.databinding.FragmentGoogleMapsMainBinding
import com.example.tokotlin.databinding.FragmentMainBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsFragment : Fragment() {

    private var _binding: FragmentGoogleMapsMainBinding? = null
    private val binding: FragmentGoogleMapsMainBinding
        get(){
            return _binding!!
        }

    private lateinit var map:GoogleMap
    private val markers = arrayListOf<Marker>()

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val moscow = LatLng(55.755826, 37.6172999)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(moscow))
        googleMap.setOnMapLongClickListener {
            getAddress(it)
            addMarker(it)
            drawLine()
        }

        googleMap.isMyLocationEnabled = true // TODO проверить разрешение еа геолокацию
        googleMap.uiSettings.isZoomControlsEnabled = true

    }

    private fun drawLine(){
        val last = markers.size
        if(last>1){
            map.addPolyline(PolylineOptions().add(markers[last-1].position,markers[last-2].position)
                .color(Color.RED).width(5f))
        }
    }

    private fun addMarker(location: LatLng){
        val marker = map.addMarker(MarkerOptions().position(location)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker)))
        markers.add(marker)
    }

    private fun getAddress(location: LatLng){
        Thread{
            val geocoder = Geocoder(requireContext())
            val listAddress = geocoder.getFromLocation(location.latitude,location.longitude,1)
            requireActivity().runOnUiThread{
                binding.textAddress.text = listAddress[0].getAddressLine(0)
            }
        }.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoogleMapsMainBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.buttonSearch.setOnClickListener{
            search()
        }
    }

    private fun search(){
        Thread{
            val geocoder = Geocoder(requireContext())
            val listAddress = geocoder.getFromLocationName(binding.searchAddress.text.toString(),1)
            requireActivity().runOnUiThread{
                map.moveCamera(CameraUpdateFactory
                    .newLatLngZoom(LatLng(listAddress[0].latitude,listAddress[0].longitude),15f))
                map.addMarker(MarkerOptions()
                    .position(LatLng(listAddress[0].latitude,listAddress[0].longitude)).title(""))
            }
        }.start()
    }
}