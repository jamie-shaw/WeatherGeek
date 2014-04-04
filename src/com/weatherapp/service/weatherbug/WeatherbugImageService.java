package com.weatherapp.service.weatherbug;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.weatherapp.service.BaseImageService;

public class WeatherbugImageService extends BaseImageService {

	private static final String WB_BASE_ICON_URL = "http://img.weather.weatherbug.com/forecast/icons/localized/";

	private static final String WB_ICON_SIZE_60X50 = "60x50";
	
	public String getIconUrl(String iconName) {
		return WB_BASE_ICON_URL + WB_ICON_SIZE_60X50 + "/en/trans/" + iconName + ".png";
	}

	@Override
	public Bitmap getImage(Resources resources, String iconName) {
		return getWebImage(iconName);
	}
	
}
