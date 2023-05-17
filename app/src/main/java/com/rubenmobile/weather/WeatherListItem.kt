package com.rubenmobile.weather

import android.view.ViewGroup
import com.rubenmobile.weather.customTypes.OpenWeather
import com.rubenmobile.weather.databinding.WeatherItemViewBinding

class WeatherListItem(var cityString: String)
{
	private lateinit var binding: WeatherItemViewBinding

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	fun createView(viewGroup: ViewGroup, item: WeatherListItem)
	{
		binding = WeatherItemViewBinding.bind(viewGroup)

		val imageView = binding.listIconView
		val titleView = binding.listTitleView
		val temperatureView = binding.listTemperatureView

		imageView.setImageBitmap(null)

		OpenWeather.getGeocode(cityString){city, lat, long, error ->
			if (error == null)
			{
				titleView.text = city
			}
			else
			{
				//show an error
			}
		}

		OpenWeather.getWeather{icon, precipitation, cloudPercent, temp, windSpeed, hiTemp, lowTemp, error ->
			if (error == null)
			{
				GetContext.context.runOnUiThread{
					temperatureView.text = String.format("%s \u2109", temp)
					imageView.setImageBitmap(icon)
				}

			}
			else
			{
				//show an error
			}
		}

	}
}