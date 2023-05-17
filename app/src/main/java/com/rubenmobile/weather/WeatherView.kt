package com.rubenmobile.weather

import android.content.Context
import android.view.LayoutInflater
import com.rubenmobile.weather.databinding.WeatherViewBinding

class WeatherView
{
	private lateinit var binding: WeatherViewBinding
	private var weatherList = ArrayList<WeatherListItem>()

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	fun loadView()
	{
		val inflater = GetContext.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val weatherView = inflater.inflate(R.layout.weather_view, GetContext.context.mainSubViewLayout, false )
		binding = WeatherViewBinding.bind(weatherView)

		GetContext.context.mainSubViewLayout.addView(weatherView)
		populateValues()
	}


	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private fun populateValues()
	{
		weatherList.add(WeatherListItem().createView("Salt Lake City"))
		weatherList.add(WeatherListItem().createView("New York"))
		weatherList.add(WeatherListItem().createView("San Francisco"))

		WeatherAdapter(weatherList).displayView(GetContext.context, binding.weatherRecyclerList)
	}


}