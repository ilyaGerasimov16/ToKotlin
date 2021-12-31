package com.example.tokotlin.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tokotlin.R
import com.example.tokotlin.databinding.MainRecyclerItemBinding
import com.example.tokotlin.model.Weather
import com.example.tokotlin.view.details.OnItemClickListener

class MainFragmentAdapter(val listener:OnItemClickListener):RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var weatherData:List<Weather> = listOf()

    fun setWeather(data:List<Weather>){
        this.weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentAdapter.MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_recycler_item,parent,false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(this.weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class MainViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(weather: Weather){
            with(MainRecyclerItemBinding.bind(itemView)){
                mainFragmentRecyclerItemTextView.text = weather.city.name
                root.setOnClickListener{
                    listener.onInemClick(weather)
                }
            }
        }
    }
}