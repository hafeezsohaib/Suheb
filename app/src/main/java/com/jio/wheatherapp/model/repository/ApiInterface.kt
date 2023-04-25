package com.jio.wheatherapp.model.repository;

import com.jio.wheatherapp.model.dataclass.ApiStatus
import com.jio.wheatherapp.model.dataclass.WeatherInfoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {

    @GET("feedbacks")
    fun getReviewList(@Header("Authorization") token: String?): Call<ApiStatus>

    @GET("weather")
    fun getWeatherByLocation(@Query("lat") latitude:String, @Query("lon") longitude:String):
            Call<WeatherInfoResponse>

    @GET("weather")
    fun getWeatherByCity(@Query("q") cityId: String):
            Call<WeatherInfoResponse>
}