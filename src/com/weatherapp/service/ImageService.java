package com.weatherapp.service;

public interface ImageService {

	public String getIconUrl(String iconName);
	
	public String getIconUrl(String icon, String dimensions, boolean transparent);
	
}