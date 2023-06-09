package com.rubenmobile.weather

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import com.rubenmobile.weather.GetContext.context
import com.rubenmobile.weather.customTypes.OpenWeather
import com.rubenmobile.weather.databinding.WeatherDetailViewBinding

class WeatherDetailedView
{
	private lateinit var binding: WeatherDetailViewBinding
	private var latitude = Double.NaN
	private var longitude = Double.NaN
	private var cityName = ""

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	fun loadData(viewGroup: MainActivity, city: String)
	{
		val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val weatherDetailView = inflater.inflate(R.layout.weather_detail_view, context.mainSubViewLayout, false )
		binding = WeatherDetailViewBinding.bind(weatherDetailView)
		this.cityName = city
		viewGroup.mainSubViewLayout.removeAllViews()

		val toolbar: Toolbar = viewGroup.findViewById(R.id.toolbar)
		context.setSupportActionBar(toolbar)
		context.supportActionBar?.setDisplayHomeAsUpEnabled(true)
		toolbar.setNavigationOnClickListener {
			WeatherView().refreshView()
		}
		viewGroup.mainSubViewLayout.addView(weatherDetailView)
		populateValues()
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private fun populateValues()
	{
		OpenWeather.getGeocode(cityName) {city, latitude, longitude, error ->
			if (error == null)
			{
				updateLatLonValues(city, latitude, longitude)
			}
			else
			{
				updateLatLonValues("", Double.NaN, Double.NaN)
				//Display Error message
			}
		}

		// 40.7596198
		OpenWeather.getWeather{icon, precipitation, cloudPercent, temp, windSpeed, hiTemp, lowTemp, error ->

			if (error == null)
			{
				updateCurrentWeatherValues(icon, precipitation, cloudPercent, temp, windSpeed, hiTemp, lowTemp)
			}
			else
			{
				updateCurrentWeatherValues(icon,context.getString(R.string.ua), context.getString(R.string.ua),context.getString(R.string.ua) ,context.getString(R.string.ua), context.getString(R.string.ua), context.getString(R.string.ua))
				//Display Error message
			}
		}
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private fun updateCurrentWeatherValues(icon: Bitmap?, precipitation: String, cloudPercent: String, temp: String, windSpeed: String, hiTemp: String, lowTemp: String)
	{
		context.runOnUiThread {

			binding.currentTempValue.text = String.format("%s \u2109", temp)
			binding.highTempValue.text = String.format("%s \u2109", hiTemp)
			binding.lowTempValue.text = String.format("%s \u2109", lowTemp)
			binding.windSpeedValue.text = String.format("%s mph", windSpeed)
			binding.precipitationValue.text = precipitation
			binding.cloudPercentage.text = context.getString(R.string.percentage, cloudPercent)
			binding.weatherIcon.setImageBitmap(icon)

		}
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	fun updateLatLonValues(cityHeader: String, lat: Double, long: Double)
	{
		context.runOnUiThread {
			latitude = lat
			longitude = long
			binding.city.text = cityHeader
		}
	}
}