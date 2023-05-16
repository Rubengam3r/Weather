package com.rubenmobile.weather

import android.renderscript.ScriptGroup.Input
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

open class HttpService
{
	class JsonResult(val json: JSONObject, val response: okhttp3.Response)

	protected fun buildRequest(requestBuilder: okhttp3.Request.Builder): okhttp3.Request
	{
		return requestBuilder
			.build()
	}

	fun readStream(input: InputStream): String
	{
		return InputStreamReader(input, StandardCharsets.UTF_8).use { br ->
			val stringBuilder = java.lang.StringBuilder()
			val buffer = CharArray(4096)
			var len: Int
			while (br.read(buffer).also { len = it } > 0)
				{
					stringBuilder.append(buffer, 0 , len)
				}
			stringBuilder.toString()
		}
	}

	fun executeJsonRequest(requestBuilder: Request.Builder, ignoreContentType: Boolean = false): JsonResult
	{
		val request = buildRequest(requestBuilder)
		val client = OkHttpClient()
		client.newCall(request).execute()
			.use { response ->
				response.body!!.byteStream()
					.use { bodyBytes ->
						val body_str = readStream(bodyBytes)
						val content_type = response.header("Content-Type")?.split(";")?.map { it.trim() }
						if (!ignoreContentType && content_type?.isNotEmpty() == true && content_type[0] != "application/json")
						{
							if (response.isSuccessful)
							{
							}
							else
							{
								throw HttpException(response, response.code)
							}
						}

						try
						{
							return JsonResult(JSONObject(body_str), response)
						}
						catch (e: JSONException)
						{
							if (ignoreContentType && !response.isSuccessful)
							{
								throw HttpException(response, response.code)
							}
							throw e
						}

					}
			}
	}
}
