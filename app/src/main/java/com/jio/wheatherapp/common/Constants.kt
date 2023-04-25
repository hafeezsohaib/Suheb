package com.jio.wheatherapp.common

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.jio.wheatherapp.R
import java.text.SimpleDateFormat
import java.util.*

class Constants {

    companion object {
        var  latlongTime: Long = 300
        const val GPS_REQUEST = 101
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val APP_ID="c1fac2c531c4c592942693d386edc753"

        fun snackeBarMessage(activity: AppCompatActivity, msg: String) {

            Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show()

//            val snackbar = Snackbar
//                .make(activity.findViewById<View>(android.R.id.content), msg, Snackbar.LENGTH_LONG)
//            val snackBarView = snackbar.view
//            val textView = snackBarView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
//            textView.setTextColor(ContextCompat.getColor(activity, R.color.black))
//            snackbar.show()
        }


        fun Int.unixTimestampToTimeString() : String {

            try {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = this*1000.toLong()

                val outputDateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                outputDateFormat.timeZone = TimeZone.getDefault()
                return outputDateFormat.format(calendar.time)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return this.toString()
        }




    }

    fun Double.kelvinToCelsius() : Int {

        return  (this - 273.15).toInt()
    }
}