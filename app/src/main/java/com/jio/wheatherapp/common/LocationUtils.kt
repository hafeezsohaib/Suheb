//package com.jio.wheatherapp.common
//import android.content.Context
//import android.content.Intent
//import android.location.LocationManager
//import android.provider.Settings
//
//import androidx.core.content.ContextCompat.startActivity
//
//object LocationUtils {
//
//    fun isLocationEnabled(context: Context): Boolean {
//        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//    }
//
//    fun CheckGpsEnable(context:Context){
//        if (LocationUtils.isLocationEnabled(context)) {
//            // Location is enabled, do something
//        } else {
//            // Location is not enabled, prompt the user to turn it on
//            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//            startActivity(context,intent)
//        }
//    }
//}