package com.jio.wheatherapp.common;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;


import com.jio.wheatherapp.R;


public class GpsHandle {


    public static void gpsDialogueOpen(final AppCompatActivity appCompatActivity, final int code){


        AlertDialog.Builder builder = new AlertDialog.Builder(appCompatActivity);
        builder.setMessage(R.string.turn_on);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                appCompatActivity.startActivityForResult(
                        new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), code);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked No button
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false); // prevent dialog from closing on outside touch
        dialog.show();














    }
}
