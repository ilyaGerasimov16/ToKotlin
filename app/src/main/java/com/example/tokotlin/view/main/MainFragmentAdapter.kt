package com.example.tokotlin.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tokotlin.databinding.FragmentMainRecyclerCityItemBinding
import com.example.tokotlin.model.Weather
import com.example.tokotlin.view.details.OnItemClickListener

class MainFragmentAdapter(val listener:OnItemClickListener):RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var weatherData:List<Weather> = listOf()

    fun setWeather(data:List<Weather>){
        this.weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int
    ): MainFragmentAdapter.MainViewHolder {
        val binding: FragmentMainRecyclerCityItemBinding =
            FragmentMainRecyclerCityItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false
            )
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(this.weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class MainViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(weather: Weather){
            with(FragmentMainRecyclerCityItemBinding.bind(itemView)){
                mainFragmentRecyclerItemTextView.text = weather.city.name
                root.setOnClickListener{
                    listener.onInemClick(weather)
                }
            }
        }
    }
}