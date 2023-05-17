package com.rubenmobile.weather

import android.content.Context
import android.view.LayoutInflater
import com.rubenmobile.weather.customTypes.OpenWeather
import com.rubenmobile.weather.databinding.WeatherItemViewBinding

class WeatherListItem
{
	private lateinit var binding: WeatherItemViewBinding
	var cityString = ""

	fun createView(cityString: String): WeatherListItem
	{
		this.cityString = cityString
		val inflater = GetContext.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val listItemView = inflater.inflate(R.layout.weather_item_view, GetContext.context.mainSubViewLayout, false)
		binding = WeatherItemViewBinding.bind(listItemView)

		populateValues()

		return this
	}

	fun populateValues()
	{
		OpenWeather.getGeocode(cityString){city, lat, long, error ->
			if (error == null)
			{
				binding.listTitleView.text = city

			}
			else
			{
				//show an error
			}
		}

		OpenWeather.getWeather{icon, precipitation, cloudPercent, temp, windSpeed, hiTemp, lowTemp, error ->
			if (error == null)
			{
				binding.listIconView.setImageBitmap(icon)
			}
			else
			{
				//show an error
				binding.listTemperatureView.text = "UA"
			}
		}


	}
}