package com.weatherapp.model;

import java.io.Serializable;

public class DefaultDailyForecast implements Serializable, DailyForecast {
	
	private static final long serialVersionUID = 1L;
	
	private String longDay;
	private String shortDay;
	private String shortPrediction;
	private String longPrediction;
	private String temperature;
	private String imageName;
	private String imageURL;
	private boolean isNight;
	
	@Override
	public String getLongDay() {
		return longDay;
	}

	@Override
	public void setLongDay(String longDay) {
		this.longDay = longDay;
	}

	@Override
	public String getShortDay() {
		return shortDay;
	}

	@Override
	public void setShortDay(String shortDay) {
		this.shortDay = shortDay;
	}

	@Override
	public String getShortPrediction() {
		return shortPrediction;
	}

	@Override
	public void setShortPrediction(String shortPrediction) {
		this.shortPrediction = shortPrediction;
	}

	@Override
	public String getLongPrediction() {
		return longPrediction;
	}

	@Override
	public void setLongPrediction(String longPrediction) {
		this.longPrediction = longPrediction;
	}

	@Override
	public String getTemperature() {
		return temperature;
	}

	@Override
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	@Override
	public String getImageURL() {
		return imageURL;
	}

	@Override
	public String getImageName() {
		return imageName;
	}

	@Override
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	@Override
	public boolean isNight() {
		return isNight;
	}

	@Override
	public void setNight(boolean isNight) {
		this.isNight = isNight;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("day:" + longDay);
		return builder.toString();
	}

	
}
