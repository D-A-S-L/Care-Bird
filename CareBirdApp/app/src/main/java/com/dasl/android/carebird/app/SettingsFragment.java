package com.dasl.android.carebird.app;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.app.PendingIntent;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.app.AlarmManager;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.io.IOException;
import java.util.List;
/**
 * Created by crazz_000 on 5/4/2014.
 */
public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

    /*
     * For testing purposes
     */
    private AlarmManager keeperOfAlarms;
	public static final String KEY_PREF_HOME_ADDRESS= "pref_home_address";
    public static final String KEY_PREF_LOCATION_NOTIFY = "pref_location_notify";
    private static final long POINT_RADIUS = 150; // in Meters
    private static final long PROX_ALERT_EXPIRATION = -1; // will not expire
    private static final String PROX_ALERT_INTENT = "com.dasl.android.carebird.app.ALERT_PROX";
    private LocationManager locationManager;
    double latitude = 34.146201, longitude = -117.495614;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.mainpreferences);

        // everything below here is just for testing use of Schedules
        // can be removed later
        ReminderSchedule a,b;
        a = new ReminderSchedule(21, 2, "penicillin");
        b = new ReminderSchedule(20, 4, "glucose");

        keeperOfAlarms = (AlarmManager) this.getActivity().getSystemService(Context.ALARM_SERVICE);
        exampleSchedulerMethod(a);
        exampleSchedulerMethod(b);
    }

    public void exampleSchedulerMethod(ReminderSchedule exampleSchedule) {
        int pseudoRand = (int) (System.currentTimeMillis() / 1000 + System.currentTimeMillis()
                        % 1000);

        Intent toReminder = new Intent(this.getActivity(), ReminderActivity.class);
        toReminder.putExtra("REMINDER_NAME", exampleSchedule.getName());
        toReminder.putExtra("YES_NO", exampleSchedule.getMessage());

        // Calendar to get simple time of day
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, exampleSchedule.getHour());
        cal.set(Calendar.MINUTE, exampleSchedule.getMinute());

        PendingIntent temp = PendingIntent.getActivity(this.getActivity(), pseudoRand,
                toReminder, PendingIntent.FLAG_UPDATE_CURRENT);
        keeperOfAlarms.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, temp);

        return;

    }
	
	@Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.i("SettingsFragment", "inside onSharedPrefernce");
        if (s.equals(findPreference(KEY_PREF_HOME_ADDRESS).getKey())) {
            Geocoder coder = new Geocoder(getActivity().getApplicationContext());
            List<Address> address;
            String strAddress = sharedPreferences.getString(KEY_PREF_HOME_ADDRESS, null);
            Preference add = findPreference(KEY_PREF_HOME_ADDRESS);


            try {
                address = coder.getFromLocationName(strAddress,5);
                if (address == null) {
                    add.setSummary("GPS Coordinates not set");
                    return;
                }
                Address location = address.get(0);
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                String coordinates = "Lat: " + lat + "  " + "Long: " + lon;
                add.setSummary("Found - " + coordinates);

            } catch(IOException e) {
                Log.i("SettingsFragment", "FAILED");
            }
        }
        else if(s.equals(findPreference(KEY_PREF_LOCATION_NOTIFY).getKey())) {
            CheckBoxPreference check = (CheckBoxPreference)findPreference(KEY_PREF_LOCATION_NOTIFY);
            if(check.isChecked()) {
                Log.i("SettingsFragment", "Adding proximity alert");
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                Intent intent = new Intent(PROX_ALERT_INTENT);
                PendingIntent proximityIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 0, intent, 0);
                locationManager.addProximityAlert(latitude, longitude, POINT_RADIUS, PROX_ALERT_EXPIRATION, proximityIntent);
            }
            else {
                Log.i("SettingsFragment", "Removing proximity alert");
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                Intent intent = new Intent(PROX_ALERT_INTENT);
                PendingIntent proximityIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 0, intent, 0);
                locationManager.removeProximityAlert(proximityIntent);
            }
        }
    }
	
	@Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}
