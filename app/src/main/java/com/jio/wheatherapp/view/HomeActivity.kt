package com.jio.wheatherapp.view




import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.jio.wheatherapp.BaseActivity
import com.jio.wheatherapp.R
import com.jio.wheatherapp.common.Constants.Companion.snackeBarMessage
import com.jio.wheatherapp.common.Constants.Companion.unixTimestampToTimeString
import com.jio.wheatherapp.model.dataclass.WeatherInfoResponse
import com.jio.wheatherapp.model.repository.ProgressBar.getInstance
import com.jio.wheatherapp.permissions.ApplicationClass
import com.jio.wheatherapp.permissions.LocationInterface
import com.jio.wheatherapp.permissions.PermissionCheck
import com.jio.wheatherapp.permissions.Utils
import com.jio.wheatherapp.permissions.Utils.Companion.getAddress
import com.jio.wheatherapp.viewmodel.HomeViewModel
import java.lang.String.format


class HomeActivity : BaseActivity() , LocationInterface {

    private lateinit var permissionCheck:PermissionCheck
    var homeViewModel: HomeViewModel? = null
   private lateinit var  temp_tv:TextView
    lateinit var humidity_tv:TextView
    lateinit var wind_tv:TextView
    lateinit var visiblity_tv:TextView
    lateinit var city_tv:TextView
    lateinit var sunrise_tv:TextView
    lateinit var sunset_tv:TextView
    lateinit var child_linear:LinearLayout
    lateinit var search_iv:ImageView
    lateinit var city_et:EditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationClass.mInstance.activity = this
        setContentView(R.layout.activity_main)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

//        in second sprint will change to Databinding and optimize the code
        temp_tv=findViewById(R.id.temp_tv)
        humidity_tv=findViewById(R.id.humidity_tv)
        wind_tv=findViewById(R.id.wind_tv)
        visiblity_tv=findViewById(R.id.visiblity_tv)
        city_tv=findViewById(R.id.city_tv)
        sunrise_tv=findViewById(R.id.sun_rise_tv)
        sunset_tv=findViewById(R.id.sun_set_tv)
        child_linear=findViewById(R.id.child_linear)
        search_iv=findViewById(R.id.search_iv)
        city_et=findViewById(R.id.city_et)
        callPermission()
        getInstance().progressBar(this)
        click()


    }

    override fun onStart() {
        super.onStart()
      //  callPermission()
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PermissionCheck.MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Utils.displayLocationSettingsRequest(this, this)
                    }else{
                        permissionCheck.requestLocationPermission()
                    }

                } else if (!shouldShowRequestPermissionRationale(permissions[0])) {
                    startActivity(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", this.packageName, null),
                        ),
                    )

                } else {
                    permissionCheck.requestLocationPermission()
                }
                return
            }
            PermissionCheck.MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {

                        Toast.makeText(
                            this,
                            "Granted Background Location Permission",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {

                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
                return

            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PermissionCheck.MY_PERMISSIONS_REQUEST_LOCATION) {
            if (resultCode == RESULT_OK) {

                Utils.displayLocationSettingsRequest(this, this)

            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    override fun onLocationChange(latitude: Double, longitude: Double) {
        getAddress(this,latitude,longitude)
        homeViewModel?.getWeatherbyCurrentLocationList(latitude.toString(), longitude.toString())
      //  Log.e("latitude_current",""+latitude)
          getWeatherList()
    }



    fun callPermission(){

        Handler(Looper.getMainLooper()).postDelayed({
            PermissionCheck(this).checkPermission()
        }, 1000)
    }

    private fun getWeatherList(){
        homeViewModel?.getResponseLiveData()?.observe(this
        ) { response ->
            getInstance().progressBarState(false)
            if (response != null) {
                child_linear.visibility=View.VISIBLE
                //snackeBarMessage(this!!, this!!.getString(R.string.weather_updated))
                setWeatherInfo(response)
            } else
                snackeBarMessage(this!!, this!!.getString(R.string.server_issue))
        }

        homeViewModel?.getResponseError()?.observe(this
        ) { response ->
            getInstance().progressBarState(false)

            snackeBarMessage(this!!, response.message)
        }

    }


    @SuppressLint("SetTextI18n")
    private fun setWeatherInfo(weatherData: WeatherInfoResponse) {
                 child_linear.visibility=View.VISIBLE
                 val temp=   format("%.2f", kelvintoCelcius(weatherData.main.temp))
                 temp_tv.text="${temp}${  getString(R.string.degree_celsius_symbol)}"
                 this.humidity_tv.text="${weatherData.main.humidity}%"
                 visiblity_tv.text="${weatherData.visibility}M"
                 wind_tv.text= "${weatherData.wind.speed}m/s"
                 city_tv.text="${weatherData.name}${" | "}${temp}${ getString(R.string.degree_celsius_symbol)}"
                 sunrise_tv.text = weatherData.sys.sunrise.unixTimestampToTimeString()
                 sunset_tv.text = weatherData.sys.sunset.unixTimestampToTimeString()




    }

    fun kelvintoCelcius( temp:Double):Double{
        return temp-273.15F
    }

   fun getLayoutResourceId(): Int {
        return R.layout.activity_main
    }

  fun click(){
      search_iv.setOnClickListener {

          val city=city_et.text.toString()
          if(city.length>0){
              closeKeyboard()
              getInstance().progressBar(this)
              homeViewModel?.getWeatherByCity(city_et.text.toString())
          }

      }
  }

    fun closeKeyboard(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(city_et.getWindowToken(), 0)

    }


    private val locationModeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == LocationManager.MODE_CHANGED_ACTION) {
                 callPermission()
                getInstance().progressBar(this@HomeActivity)
                // Check if location services are enabled
                val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)


                if (!isLocationEnabled) {
                    Toast.makeText(this@HomeActivity,"on location",Toast.LENGTH_SHORT).show()
                   // callPermission()
                    //getInstance().progressBar(this@HomeActivity)
                    // Location services have been disabled while the activity is running
                    // Your code here to handle this scenario
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Register the location mode receiver to receive broadcasts when the location mode changes
        registerReceiver(locationModeReceiver, IntentFilter(LocationManager.MODE_CHANGED_ACTION))
    }

    override fun onPause() {
        super.onPause()
        // Unregister the location mode receiver to avoid memory leaks
        unregisterReceiver(locationModeReceiver)
    }




}