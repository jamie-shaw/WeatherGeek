package com.weatherapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.weatherapp.R;

public class Preferences {
	
	public final static String PREFS_NAME = "weathergeek_prefs";

	public static String getZipCode(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		return prefs.getString(context.getString(R.string.pref_key_zip_code), "04103");
	}

	public static void setZipCode(Context context, String newValue) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		Editor prefsEditor = prefs.edit();
		prefsEditor.putString(context.getString(R.string.pref_key_zip_code), newValue);
		prefsEditor.commit();
	}
}