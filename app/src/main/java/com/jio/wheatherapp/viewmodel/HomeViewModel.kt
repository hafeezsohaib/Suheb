package com.jio.wheatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jio.wheatherapp.model.repository.ApiInterface
import com.jio.wheatherapp.model.repository.ApiRepository
import com.jio.wheatherapp.model.repository.RetrofitSingelton
import com.jio.wheatherapp.model.dataclass.ApiStatus
import com.jio.wheatherapp.model.dataclass.WeatherInfoResponse

class HomeViewModel() :  ViewModel(){

    private var apiRepository: ApiRepository<WeatherInfoResponse>?=null
    private var data: LiveData<WeatherInfoResponse>? = null
    private var apiRequest: ApiInterface = RetrofitSingelton.getInstance()!!
    private var dataError: LiveData<WeatherInfoResponse>?=null

    init {
        if(apiRepository==null)
            apiRepository= ApiRepository()
    }


    fun getWeatherbyCurrentLocationList(lat:String,lng:String) {
        data=apiRepository!!.callApi(apiRequest.getWeatherByLocation(lat,lng))
    }

    fun getWeatherByCity(city:String) {
        data=apiRepository!!.callApi(apiRequest.getWeatherByCity(city))
    }

    fun getResponseLiveData(): LiveData<WeatherInfoResponse>? {
        if(data!=null)
            data=apiRepository?.data
        return data
    }

    fun getResponseError(): LiveData<WeatherInfoResponse>? {
        if(dataError==null)
            dataError=apiRepository?.dataError
        return dataError
    }

}