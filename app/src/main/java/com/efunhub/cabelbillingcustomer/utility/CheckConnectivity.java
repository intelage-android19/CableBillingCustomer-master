package com.efunhub.cabelbillingcustomer.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;

import com.efunhub.cabelbillingcustomer.R;

public class CheckConnectivity extends BroadcastReceiver {
    AlertDialog alertDialog;

    @Override
    public void onReceive(final Context context, Intent intent) {

        if (intent.getExtras() != null) {
            NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);

            if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {

                if(alertDialog!=null&&alertDialog.isShowing()){
                    alertDialog.dismiss();
                }
            } else {
                // we're not connected
                alertDialog = new AlertDialog.Builder(context)
                        .setTitle(context.getResources().getString(R.string.app_name))
                        .setMessage("No Internet Connection !!!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Intent intent = new Intent(Intent.ACTION_MAIN);
                                //intent.setClassName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity");

                                IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                               context.registerReceiver(CheckConnectivity.this, intentFilter);
                                //qcontext.startActivity(intent);

                            }
                        }).show();
                alertDialog.setCancelable(false);
                //Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }

    }
}


