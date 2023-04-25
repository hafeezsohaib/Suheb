package com.jio.wheatherapp.permissions

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class ApplicationClass : Application() {

    lateinit var activity: AppCompatActivity

    override fun onCreate() {
        super.onCreate()
        mInstance = this

    }

    companion object {
        lateinit var mInstance: ApplicationClass




        private operator fun get(context: Context): ApplicationClass {
            return context.applicationContext as ApplicationClass
        }




    }



}