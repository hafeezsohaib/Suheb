package com.jio.wheatherapp.common;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissions {

    public static final int PERMISSION_LOCATION_CODE = 200;
    public static final int PERMISSION_CALENDAR_CODE = 300;


    public static final String[] PERMISSIONS_LOCATION_ARRAY = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static void requestMultiplePermission(AppCompatActivity activity, String[] permissions, int permissionCode) {
        ActivityCompat.requestPermissions(activity, permissions, permissionCode);
    }


    public static boolean isLocationPermGranted(AppCompatActivity activity) {

        int coarse = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        int fine = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (coarse != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (fine != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        String[] arr = new String[listPermissionsNeeded.size()];
        arr = listPermissionsNeeded.toArray(arr);

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, arr, PERMISSION_LOCATION_CODE);
            return false;
        }
        return true;
    }


    public static boolean checkLocationPermission(final int MY_PERMISSIONS_REQUEST_LOCATION,final AppCompatActivity appCompatActivity) {
        if (ContextCompat.checkSelfPermission(appCompatActivity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(appCompatActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(appCompatActivity)
                        .setTitle("permission_neccessary")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                ActivityCompat.requestPermissions(appCompatActivity,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(appCompatActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


}
