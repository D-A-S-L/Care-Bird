package com.dasl.android.carebird.app;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by crazz_000 on 5/4/2014.
 */
public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
