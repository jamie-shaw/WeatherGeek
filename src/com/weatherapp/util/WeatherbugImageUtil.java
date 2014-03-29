package com.weatherapp.util;

public class WeatherbugImageUtil {

	private static final String WB_BASE_ICON_URL = "http://img.weather.weatherbug.com/forecast/icons/localized/";

	private static final String WB_ICON_SIZE_60X50 = "60x50";
	
	public static String getWeatherbugIconUrl(String iconName) {
		return WB_BASE_ICON_URL + WB_ICON_SIZE_60X50 + "/en/trans/" + iconName + ".png";
	}
	
	public static String getWeatherbugIconUrl(String iconName, String iconSize, boolean transparent) {
		return WB_BASE_ICON_URL + iconSize + "/en/" + (transparent?"trans":"opaq") + "/" + iconName + ".png";
	}

	
}
