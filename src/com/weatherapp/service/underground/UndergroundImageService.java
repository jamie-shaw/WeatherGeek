package com.weatherapp.service.underground;

import android.graphics.Bitmap;

import com.weatherapp.BaseImageService;

public class UndergroundImageService extends BaseImageService {

	private static final String BASE_ICON_URL = "http://icons.wxug.com/i/c/k/";
	
	public String getIconUrl(String iconName) {
		return BASE_ICON_URL + iconName + ".gif";
	}

	@Override
	public Bitmap getImage(String iconName) {
		return getWebImage(iconName);
	}
	
}
