package com.weatherapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.weatherapp.R;
import com.weatherapp.service.ServiceFactory.ServiceProvider;

public class Preferences {
	
	public final static String PREFS_NAME = "weathergeek_prefs";

	public static ServiceProvider getWeatherProvider(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		String serviceProviderString = prefs.getString(context.getString(R.string.pref_key_weather_provider), ServiceProvider.UNDERGROUND.toString());

		return ServiceProvider.valueOf(serviceProviderString);
	}

	public static void setWeatherProvider(Context context, ServiceProvider newValue) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		Editor prefsEditor = prefs.edit();
		prefsEditor.putString(context.getString(R.string.pref_key_weather_provider), newValue.toString());
		prefsEditor.commit();
	}
}