package com.jio.wheatherapp.permissions

interface LocationInterface {
    abstract fun onLocationChange(latitude: Double, longitude: Double)
}