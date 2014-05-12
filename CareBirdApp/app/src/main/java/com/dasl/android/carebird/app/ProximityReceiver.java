package com.dasl.android.carebird.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by crazz_000 on 5/10/2014.
 */
public class ProximityReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("ProximityReceiver", "Inside Proximity Receiver");
        String key = LocationManager.KEY_PROXIMITY_ENTERING;
        boolean entering = intent.getBooleanExtra(key, false);
        if(entering) {
            Log.i("ProximityReceiver", "Entering proximity");
        }
        else {
            //Send notification alert
            Log.i("ProximityReceiver", "Exiting proximity");
        }
    }
}
