package com.fastmaxi.fastmaxisydney.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.fastmaxi.fastmaxisydney.R;


/*

design by rajat */
public class HelperMethods {


    public static AlertDialog alertNoCon;

    public static void showAlertDialog(final Activity context) {

        new AlertDialog.Builder(context).setMessage(R.string.secure_conn_exception).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                context.finish();
            }
        }).setCancelable(false).show();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public static void showNoConnDialog(Context context) {
        if (alertNoCon != null && alertNoCon.isShowing()) {

        } else {
            alertNoCon = new AlertDialog.Builder(context).create();
            alertNoCon.setTitle("No Connection");
            alertNoCon.setMessage("Network connection is unavailable. Please check your network connection");
            alertNoCon.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertNoCon.show();
        }


    }
}
