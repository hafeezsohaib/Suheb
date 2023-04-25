package com.jio.wheatherapp.common;

import android.app.Service;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;


import com.jio.wheatherapp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class GpsTracker implements LocationListener {
    private static GpsTracker gpsTracker;
    public double getLat;
    public double getLang;
    public String locationName;
    private LocationManager locationManager;
    private Context context;


    public static GpsTracker getInstance() {
        if (gpsTracker == null)
            gpsTracker = new GpsTracker();
        return gpsTracker;
    }

    public void GpsRequest(Context context) {
        this.context = context;
        try {
            locationManager = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);
            assert locationManager != null;
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Constants.Companion.getLatlongTime(), Constants.Companion.getLatlongTime(), this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public boolean checkGpsOpenrClosed() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void gettingLocation(Context context) {
//        CommonClass.snackeBarMessage((HomeScreenActivity)context,context.getString(R.string.location_msg_text));
        Toast.makeText(context, context.getString(R.string.location_msg_text), Toast.LENGTH_SHORT).show();
    }

    public void removeLocationUpdate() {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        getLang = location.getLongitude();
        getLat = location.getLatitude();
        Geocoder geoCoder=new Geocoder(context, Locale.getDefault());
        List<Address> list = null;
        try {
            list = geoCoder.getFromLocation(location
                    .getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list != null & list.size() > 0) {
            Address address = list.get(0);
            locationName=address.getFeatureName()+" "+ address.getLocality();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
     System.out.print("tag");
    }

    @Override
    public void onProviderEnabled(String s) {
        System.out.print("tag");
    }

    @Override
    public void onProviderDisabled(String s) {
        System.out.print("tag");
    }



}
