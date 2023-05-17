package com.rubenmobile.weather

import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)

class OpenWeatherTest
{
    private lateinit var okHttpClient: OkHttpClient
    private val openWeatherApiKey = "8ecdeb18396af64d9cc27576ece9d705"

    @Before
    fun setup()
    {
        okHttpClient = OkHttpClient()
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Test
    fun testApiResponse()
    {
        val request = Request.Builder()
            .url("https://api.openweathermap.org/data/2.5/weather?lat=40.2252&lon=-111.6607&appid=$openWeatherApiKey")
            .build()

        val response = okHttpClient.newCall(request).execute()
        assertTrue(response.isSuccessful)

        assertEquals(200, response.code)
        assertNotNull(response.body)

        response.close()
    }
}
