package com.rubenmobile.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rubenmobile.weather.databinding.WeatherViewBinding

class WeatherView
{
	private lateinit var binding: WeatherViewBinding
	private var weatherList = ArrayList<WeatherListItem>()
	private lateinit var adapter: Adapter
	private var cityNames = ArrayList<String>()


	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	fun loadView()
	{
		val inflater = GetContext.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val weatherView = inflater.inflate(R.layout.weather_view, GetContext.context.mainSubViewLayout, false )
		binding = WeatherViewBinding.bind(weatherView)

		val toolbar: Toolbar = GetContext.context.findViewById(R.id.toolbar)
		GetContext.context.setSupportActionBar(toolbar)
		GetContext.context.supportActionBar?.setDisplayHomeAsUpEnabled(false)

		cityNames.add("Salt Lake City")
		cityNames.add("New York")
		cityNames.add("San Francisco")

		setupListeners()
		populateList()
		GetContext.context.mainSubViewLayout.addView(weatherView)

	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private fun populateList()
	{
		for (city in cityNames)
		{
			val weatherItem = WeatherListItem(city)
			weatherList.add(weatherItem)
			GetContext.context.runOnUiThread {
				adapter.notifyItemChanged(weatherList.indexOf(weatherItem))
			}
		}
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private fun setupListeners()
	{
		adapter = Adapter()
		val rv = binding.weatherRecyclerList
		rv.adapter = adapter
		rv.layoutManager = LinearLayoutManager(GetContext.context)
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	fun refreshView()
	{
		GetContext.context.mainSubViewLayout.removeAllViews()
		loadView()
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	inner class Adapter: RecyclerView.Adapter<Adapter.WeatherViewHolder>()
	{
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder
		{
			val inflater = LayoutInflater.from(parent.context)
			val view = inflater.inflate(R.layout.weather_item_view, parent, false)

			val holder = WeatherViewHolder(view)

			holder.title = view.findViewById(R.id.list_title_view)

			return holder
		}

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		override fun getItemCount(): Int
		{
			return weatherList.size
		}

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		override fun onBindViewHolder(holder: WeatherViewHolder, position: Int)
		{
			val weatherItem = weatherList[position]

			weatherItem.createView(holder.itemView as ViewGroup, weatherItem)

			holder.title.setOnClickListener {
				WeatherDetailedView().loadData(GetContext.context, weatherItem.cityString)
			}

			holder.title.text = weatherItem.cityString
		}

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		inner class WeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
		{
			lateinit var title: TextView
			lateinit var temperature: TextView
			lateinit var iconView: ImageView
		}
	}

}