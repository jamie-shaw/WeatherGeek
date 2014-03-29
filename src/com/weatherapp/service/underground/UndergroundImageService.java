package com.weatherapp.service.underground;

import com.weatherapp.service.ImageService;

public class UndergroundImageService implements ImageService {

	private static final String BASE_ICON_URL = "http://icons.wxug.com/i/c/k/";
	
	public String getIconUrl(String iconName) {
		return BASE_ICON_URL + iconName + ".gif";
	}

	@Override
	public String getIconUrl(String icon, String dimensions, boolean transparent) {
		return null;
	}
	
}
