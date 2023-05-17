package com.rubenmobile.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity()
{

	private lateinit var context: MainActivity
	lateinit var weatherView: WeatherView
	lateinit var mainSubViewLayout: RelativeLayout

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	override fun onCreate(savedInstanceState: Bundle?)
	{
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

		this.context = this
		GetContext.setContext(this)

		weatherView = WeatherView()
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		mainSubViewLayout = this.findViewById(R.id.mainsubviewlayout)

		weatherView.loadView()
	}
}
