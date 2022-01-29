package com.example.tokotlin.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.tokotlin.databinding.FragmentHistoryRecyclerCityItemBinding
import com.example.tokotlin.model.Weather
import com.example.tokotlin.view.details.OnItemClickListener

class CitiesHistoryAdapter(val listener: OnCityItemClickListener):RecyclerView.Adapter<CitiesHistoryAdapter.HistoryViewHolder>() {
    private var weatherData:List<Weather> = listOf()

    fun setWeather(data:List<Weather>){
        this.weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int
    ): CitiesHistoryAdapter.HistoryViewHolder {
        val binding: FragmentHistoryRecyclerCityItemBinding =
            FragmentHistoryRecyclerCityItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false
            )
        return HistoryViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(this.weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class HistoryViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(weather: Weather){
            with(FragmentHistoryRecyclerCityItemBinding.bind(itemView)){
                cityName.text = weather.city.name
                temperature.text = "${weather.temperature}"
                feelsLike.text = "${weather.feelsLike}"
                icon.loadUrl("https://yastatic.net/weather/i/icons/funky/dark/${weather.icon}.svg")

                root.setOnClickListener{
                    listener.onInemClick(weather)
                }
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
    }
}