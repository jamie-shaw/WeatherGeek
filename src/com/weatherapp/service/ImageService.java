package com.weatherapp.service;

import android.graphics.Bitmap;

public interface ImageService {

	public String getIconUrl(String iconName);

	public Bitmap getImage(String iconName);
	
}