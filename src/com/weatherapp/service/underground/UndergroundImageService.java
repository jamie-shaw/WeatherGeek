package com.weatherapp.service.underground;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.weatherapp.service.BaseImageService;

public class UndergroundImageService extends BaseImageService {

	private static final String BASE_ICON_URL = "http://icons.wxug.com/i/c/k/";
	
	public String getIconUrl(String iconName) {
		return BASE_ICON_URL + iconName + ".gif";
	}

	@Override
	public Bitmap getImage(Resources resources, String iconName) {
		//return getLocalImage(resources, iconName);
		return getWebImage(iconName);
	}
	
}
