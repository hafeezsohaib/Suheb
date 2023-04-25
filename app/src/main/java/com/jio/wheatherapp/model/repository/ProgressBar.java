package com.jio.wheatherapp.model.repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;


import com.jio.wheatherapp.R;


/*
Class that display a progress bar when an operation is performed
*/
public class ProgressBar {
    private static ProgressBar progressBar;
    private ProgressDialog progressDialog;

    public static ProgressBar getInstance() {
        if (progressBar == null)
            progressBar = new ProgressBar();
        return progressBar;
    }

    public void progressBar(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.getWindow().setBackgroundDrawable(new

                ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_bar);
    }

    public void progressBarState(boolean progressbarState) {
        if (progressbarState)
            progressDialog.show();
        else
            try {
                progressDialog.dismiss();
            } catch (Exception ignored) {

            }

    }
}
