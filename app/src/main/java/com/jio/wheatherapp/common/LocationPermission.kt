package com.jio.wheatherapp.common


import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.jio.wheatherapp.view.HomeActivity

class LocationPermission (appCompatActivity: HomeActivity){
   private val appCompatActivity:HomeActivity
    init {
        this.appCompatActivity=appCompatActivity
    }

    fun checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(appCompatActivity, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(appCompatActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(appCompatActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }

        else {
            ActivityCompat.requestPermissions(appCompatActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)


        }

    }


    fun statusCheck() {
        val manager = appCompatActivity.getSystemService(LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

        }
    }





}