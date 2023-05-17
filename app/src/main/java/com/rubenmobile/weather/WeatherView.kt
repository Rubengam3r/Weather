package com.rubenmobile.weather

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
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

		val toolbar: Toolbar = GetContext.context.findViewById(R.id.toolbar)
		GetContext.context.setSupportActionBar(toolbar)
		GetContext.context.supportActionBar?.setDisplayHomeAsUpEnabled(false)

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

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	fun refreshView()
	{
		GetContext.context.mainSubViewLayout.removeAllViews()
		loadView()
	}

}