package com.dasl.android.carebird.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;

/**
 * Created by crazz_000 on 5/9/2014.
 */
public class WifiReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences mPref = context.getSharedPreferences("BOOT_PREF", context.MODE_PRIVATE);
        if(mPref.getBoolean("WIFI_SSID_STATUS", false)) {
            String locationSSID = "\"" + mPref.getString("WIFI_SSID", null) + "\"";

            ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            String currentSSID = connectionInfo.getSSID();

            if (netInfo.isConnected()) {
                if (connectionInfo != null && !currentSSID.equals("")) {
                    String ssid = connectionInfo.getSSID();
                    if (!ssid.equals(locationSSID)) {
                        Intent i = new Intent(context, ErrorActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }
            } else if (netInfo.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) {
                Intent i = new Intent(context, ErrorActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }
}
