package com.weatherapp.service;

import android.content.res.Resources;
import android.graphics.Bitmap;

public interface ImageService {

	public String getIconUrl(String iconName);

	public Bitmap getImage(Resources resources, String iconName);
	
}