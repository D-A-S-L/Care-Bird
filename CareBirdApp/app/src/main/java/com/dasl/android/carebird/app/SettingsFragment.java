package com.dasl.android.carebird.app;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by crazz_000 on 5/4/2014.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.mainpreferences);
    }
}
