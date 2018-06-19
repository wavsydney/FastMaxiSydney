package com.fastmaxi.fastmaxisydney.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.fastmaxi.fastmaxisydney.Helper.HelperMethods;
import com.fastmaxi.fastmaxisydney.R;

import io.fabric.sdk.android.Fabric;


public class Splash extends AppCompatActivity {

    //permission's

    private final static int PERMISSION_ALL = 1;
    private static int SPLASH_TIMER = 2000;
    String[] PERMISSIONS = {Manifest.permission.INTERNET, Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_NETWORK_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Build.VERSION.SDK_INT >= 15) {

                    // Here, thisActivity is the current activity
                    if (!HelperMethods.hasPermissions(Splash.this, PERMISSIONS)) {

                        ActivityCompat.requestPermissions(Splash.this, PERMISSIONS, PERMISSION_ALL);
                    } else {
                        Intent intent = new Intent(Splash.this, Dashboard.class);
                        startActivity(intent);
                        finish();

                    }
                }
            }

        }, SPLASH_TIMER);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL: {
                // If request is cancelled, the result arrays are empty.

                if (grantResults.length > 0
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
//                    getLastLocation();

                    Intent intent = new Intent(Splash.this, Dashboard.class);
                    startActivity(intent);
                    finish();

                } else {

                    new AlertDialog.Builder(Splash.this).setTitle("Warning!!").setMessage("Kindly Allow Permission to use FastMaxi Taxi.\n\nif you deny the permission then you will not able to use FastMaxi Taxi application.\n\nif you denied permission by mistake then uninstall FastMaxi Taxi and install it again").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    // Here, thisActivity is the current activity
                                    if (!HelperMethods.hasPermissions(Splash.this, PERMISSIONS)) {


                                        ActivityCompat.requestPermissions(Splash.this, PERMISSIONS, PERMISSION_ALL);
                                    }
                                }
                            }

                    ).

                            show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
