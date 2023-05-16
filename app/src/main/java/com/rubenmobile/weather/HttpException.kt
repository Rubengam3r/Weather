package com.rubenmobile.weather

import okhttp3.Response
import java.lang.Exception

class HttpException(response: Response, httpResponse: Int) : Exception(String.format("http response = %s -> %s", httpResponse, response.message)) {
}