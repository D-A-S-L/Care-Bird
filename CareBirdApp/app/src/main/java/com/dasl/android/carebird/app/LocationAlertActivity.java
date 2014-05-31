package com.dasl.android.carebird.app;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Alec on 5/30/2014.
 */
public class LocationAlertActivity extends Activity {

    private double homeLat, homeLon;
    private double lat, lon;
    private String userName;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_alert);

        float dist;

        homeLat = getIntent().getExtras().getDouble("HOME_LATITUDE");
        homeLon = getIntent().getExtras().getDouble("HOME_LONGITUDE");
        lat = getIntent().getExtras().getDouble("CURRENT_LATITUDE");
        lon = getIntent().getExtras().getDouble("CURRENT_LONGITUDE");
        userName = getIntent().getExtras().getString("NAME");

        Location loc1 = new Location("");
        loc1.setLatitude(lat);
        loc1.setLongitude(lon);

        Location loc2 = new Location("");
        loc2.setLatitude(homeLat);
        loc2.setLongitude(homeLon);

        dist = loc1.distanceTo(loc2);

        TextView nText = (TextView) findViewById(R.id.notifyText);
        TextView coordText = (TextView)findViewById(R.id.coords);

        nText.setText(userName + " is currently " + dist + " meters from home, with coordinates:");
        coordText.setText("(" + lat + ", " + lon + ")");
        coordText.setLinkTextColor(Color.BLUE);

        Button ok = (Button) findViewById(R.id.button);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
