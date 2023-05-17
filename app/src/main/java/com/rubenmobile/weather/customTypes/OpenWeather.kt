package com.rubenmobile.weather.customTypes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import com.rubenmobile.weather.GetContext
import com.rubenmobile.weather.HttpService
import com.rubenmobile.weather.R
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.StringBuilder
import java.util.Locale

object OpenWeather
{
	private const val openWeatherURL = "https://api.openweathermap.org/data/2.5/weather?"
	private const val openWeatherApiKey = "8ecdeb18396af64d9cc27576ece9d705"

	private var currentTemperature = 0
	private var hiTemperature = 0
	private var lowTemperature = 0
	private var cloudPercent = 0
	private var precipitation = ""
	private var icon: Bitmap? = null
	private var windSpeed = 0
	private var city = ""
	private var lat = Double.NaN
	private var long = Double.NaN
	private val http = HttpService()

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	fun getGeocode(cityName: String, geocodeCallback: (featureName: String, latitude: Double, longitude: Double, error: String?) -> Unit)
	{
		val gc = Geocoder(GetContext.context, Locale.US)
		val addresses = gc.getFromLocationName(cityName,2)
		val address = addresses!![0]

		lat = address.latitude
		long = address.longitude
		city = String.format("%s, %s", address.featureName, address.adminArea)

		geocodeCallback(city, lat, long, null)
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	fun getWeather(weatherResultsCallback: (icon: Bitmap?, precipitation: String, cloudPercent: String, temp: String, windSpeed: String, hiTemp: String, lowTemp: String,  error: String?) -> Unit)
	{
		val httpUrl = openWeatherURL.toHttpUrl()

		Thread {
			val url = httpUrl.newBuilder()
				.addQueryParameter("lat", lat.toString())
				.addQueryParameter("lon", long.toString())
				.addQueryParameter("appid", openWeatherApiKey)
				.addQueryParameter("units", "imperial")
				.build()

			try {
				val result = http.executeJsonRequest(Request.Builder().url(url))

				if (result.response.isSuccessful) {
					currentTemperature = (result.json.get("main") as JSONObject).getInt("temp")
					lowTemperature = (result.json.get("main") as JSONObject).getInt("temp_min")
					hiTemperature = (result.json.get("main") as JSONObject).getInt("temp_max")

					windSpeed = (result.json.get("wind") as JSONObject).getInt("speed")

					val weatherConditionCode =
						(((result.json.get("weather") as JSONArray)).get(0) as JSONObject).getInt("id")
					precipitation = convertConditionToPrecipitation(weatherConditionCode)

					cloudPercent = (result.json.get("clouds") as JSONObject).getInt("all")

					val weatherIconCode =
						(((result.json.get("weather") as JSONArray)).get(0) as JSONObject).get("icon") as String
					icon = convertWeatherIcon(weatherIconCode)


					weatherResultsCallback(
						icon,
						precipitation,
						cloudPercent.toString(),
						currentTemperature.toString(),
						windSpeed.toString(),
						hiTemperature.toString(),
						lowTemperature.toString(),
						null
					)

				} else {
					weatherResultsCallback(
						null,
						"",
						"",
						"",
						"",
						"",
						"",
						result.json.getString("message")
					)
				}
			}
			catch (e: IOException) {
				weatherResultsCallback(null, "", "", "", "", "", "", e.toString())
			}
			catch (e: JSONException)
			{
				weatherResultsCallback(null, "", "", "", "", "", "", e.toString())
			}
		}.start()
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private fun convertConditionToPrecipitation(weatherConditionCode: Int): String
	{
		when (weatherConditionCode)
		{
			800 -> return GetContext.context.getString(R.string.sunny)
			711, in 731 .. 781, in 803 .. 804 -> return GetContext.context.getString(R.string.cloudy)
			701, 721, in 801 .. 802 -> return GetContext.context.getString(R.string.partly_cloudy)
			in 200 .. 531 -> return GetContext.context.getString(R.string.rainy)
			in 600 .. 622 -> return GetContext.context.getString(R.string.snowy)

		}
		return GetContext.context.getString(R.string.unknown)
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private fun convertWeatherIcon(weatherIconCode: String): Bitmap {
		val imageUrl = "https://openweathermap.org/img/wn/"

		val urlBuilder = StringBuilder()
		urlBuilder.append(imageUrl)
		urlBuilder.append(weatherIconCode)
		urlBuilder.append("@2x.png")

		val image = java.net.URL(urlBuilder.toString()).openStream()

		return BitmapFactory.decodeStream(image)

	}

}