package com.weatherapp;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.weatherapp.util.Preferences;

public class PreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
		getPreferenceManager().setSharedPreferencesName(Preferences.PREFS_NAME);
        addPreferencesFromResource(R.xml.preferences);
	}

}
