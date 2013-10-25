package com.weatherapp.util;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

public class ImageCache {

	private static final Map<String, Bitmap> cache = new HashMap<String, Bitmap>();
	
	public static Bitmap getImage(String name) {
		System.out.println("cache hit for " + name);
		return cache.get(name);
	}
	
	public static void putImage(String name, Bitmap image) {
		System.out.println("cache set for " + name);
		cache.put(name, image);
	}
}
