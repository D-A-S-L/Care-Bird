package com.dasl.android.carebird.app;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.List;

/**
 * Created by crazz_000 on 5/29/2014.
 */
public class LocationActivity extends Activity {
    private static final long PROX_ALERT_EXPIRATION = -1; // will not expire
    private static final String PROX_ALERT_INTENT = "com.dasl.android.carebird.app.ALERT_PROX";
    private LocationManager locationManager;
    private EditText addrTxt;
    private TextView resultGps;
    private SharedPreferences mPref;
    private SharedPreferences.Editor editor;
    private String latitude;
    private String longitude;
    private ToggleButton togGps;
    private Spinner spinRadius;
    private EditText wifiId;
    private TextView statusWifi;
    private Button setWifi;
    private ToggleButton toggleWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mPref = getSharedPreferences("BOOT_PREF",MODE_PRIVATE);
        editor = mPref.edit();
        latitude = mPref.getString("GPS_LATITUDE", null);
        longitude = mPref.getString("GPS_LONGITUDE", null);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        wifiId = (EditText) findViewById(R.id.wifi_ssid);
        statusWifi = (TextView) findViewById(R.id.text_wifi);
        setWifi = (Button) findViewById(R.id.button_ssid);
        toggleWifi = (ToggleButton) findViewById(R.id.button_wifi);
        resultGps = (TextView) findViewById(R.id.location_set);
        addrTxt = (EditText) findViewById(R.id.edit_address);
        togGps = (ToggleButton) findViewById(R.id.toggle_gps);
        spinRadius = (Spinner) findViewById(R.id.spinner_radius);

        if(latitude != null && longitude != null) {
            resultGps.setText("Location Set  -  Lat: " + latitude + "Long: " + longitude);
            resultGps.setTextColor(Color.GREEN);
            togGps.setEnabled(true);
        }
        else {
            resultGps.setTextColor(Color.RED);
            resultGps.setText("Location not yet set! Enter an Address and click Find.");
            togGps.setEnabled(false);
        }

        if(mPref.getString("WIFI_SSID", null) == null) {
            statusWifi.setText("No SSID currently set!");
            statusWifi.setTextColor(Color.RED);
            toggleWifi.setEnabled(false);
        }
        else {
            statusWifi.setText("Current Wifi SSID:  " + mPref.getString("WIFI_SSID", null));
            statusWifi.setTextColor(Color.GREEN);
            toggleWifi.setEnabled(true);
        }

        if(mPref.getBoolean("TOGGLE_GPS", false)) {
            togGps.setChecked(true);
        }
        else {
            togGps.setChecked(false);
        }

        if(mPref.getBoolean("WIFI_SSID_STATUS", false)) {
            toggleWifi.setChecked(true);
        }
        else {
            toggleWifi.setChecked(false);
        }

        Button addBtn = (Button) findViewById(R.id.address_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addr = addrTxt.getText().toString();
                if(addr.length() < 8) {
                    return;
                }
                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> address;

                try {
                    address = coder.getFromLocationName(addr,5);
                    if (address == null) {
                        resultGps.setTextColor(Color.RED);
                        resultGps.setText("Location not yet set! Enter an Address and click Find.");
                    }
                    else {
                        Address location = address.get(0);
                        latitude = "" + location.getLatitude();
                        longitude = "" + location.getLongitude();
                        editor.putString("GPS_LATITUDE", latitude);
                        editor.putString("GPS_LONGITUDE", longitude);
                        editor.commit();

                        String coordinates = "Location Set  -  Lat: " + latitude + "  " +
                                "Long: " + longitude;
                        resultGps.setTextColor(Color.GREEN);
                        resultGps.setText(coordinates);
                        togGps.setEnabled(true);
                    }
                } catch(IOException e) {
                    android.util.Log.i("SettingsFragment", "FAILED");
                }
            }
        });
        togGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isOn = togGps.isChecked();
                android.util.Log.i("Toggle GPS", "Clicked TOGGLE");
                if(isOn) {
                    editor.putBoolean("TOGGLE_GPS", true);
                    editor.commit();
                    int radius = Integer.parseInt(spinRadius.getSelectedItem().toString());
                    Intent intent = new Intent(PROX_ALERT_INTENT);
                    PendingIntent proximityIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                    locationManager.addProximityAlert(Double.parseDouble(latitude), Double.parseDouble(longitude),
                            radius, PROX_ALERT_EXPIRATION, proximityIntent);
                }
                else {
                    editor.putBoolean("TOGGLE_GPS", false);
                    editor.commit();
                    Intent intent = new Intent(PROX_ALERT_INTENT);
                    PendingIntent proximityIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                    locationManager.removeProximityAlert(proximityIntent);
                }
            }
        });
        setWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ssid = wifiId.getText().toString();
                if(ssid.length() > 0) {
                    editor.putString("WIFI_SSID", ssid);
                    editor.commit();
                    statusWifi.setText("Current Wifi SSID:  " + ssid);
                    statusWifi.setTextColor(Color.GREEN);
                    toggleWifi.setEnabled(true);
                }
            }
        });

        toggleWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isOn = toggleWifi.isChecked();
                if(isOn) {
                    editor.putBoolean("WIFI_SSID_STATUS", isOn);
                    editor.commit();
                }
                else {
                    editor.putBoolean("WIFI_SSID_STATUS", isOn);
                    editor.commit();
                }
            }
        });
    }
}
