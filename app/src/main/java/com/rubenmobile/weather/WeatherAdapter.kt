package com.rubenmobile.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WeatherAdapter(private val weatherData: ArrayList<WeatherListItem>): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>()
{
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder
	{
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.weather_item_view, parent, false)
		return WeatherViewHolder(view)
	}

	override fun getItemCount(): Int
	{
		return weatherData.size
	}

	override fun onBindViewHolder(holder: WeatherViewHolder, position: Int)
	{
		val item = weatherData[position]
		holder.bind(item)
	}

	inner class WeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
	{
		var titleView = itemView.findViewById<TextView>(R.id.list_title_view)

		fun bind(item: WeatherListItem)
		{
			titleView.text = item.cityString

			titleView.setOnClickListener {
				WeatherDetailedView().loadData(GetContext.context, item.cityString)
			}
		}
	}

	fun displayView(context: Context, recyclerView: RecyclerView, )
	{
		val adapter = WeatherAdapter(weatherData)
		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(context)
	}
}