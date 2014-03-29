package com.weatherapp.util;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.util.Log;

public class ImageCache {

	private static final Map<String, Bitmap> cache = new HashMap<String, Bitmap>();
	
	public static Bitmap getImage(String name) {
		Log.d("WeatherGeek", "cache hit for " + name);
		return cache.get(name);
	}
	
	public static void putImage(String name, Bitmap image) {
		Log.d("WeatherGeek", "cache set for " + name);
		cache.put(name, image);
	}
}
