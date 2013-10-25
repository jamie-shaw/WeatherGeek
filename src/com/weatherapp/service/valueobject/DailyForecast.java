package com.weatherapp.service.valueobject;

import java.io.Serializable;

public class DailyForecast implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String longDay;
	private String shortDay;
	private String shortPrediction;
	private String longPrediction;
	private String lowTemp;
	private String highTemp;
	private String imageName;
	private String imageURL;
	private boolean isNight;
	
	public String getLongDay() {
		return longDay;
	}
	public void setLongDay(String longDay) {
		this.longDay = longDay;
	}
	public String getShortDay() {
		return shortDay;
	}
	public void setShortDay(String shortDay) {
		this.shortDay = shortDay;
	}
	public String getShortPrediction() {
		return shortPrediction;
	}
	public void setShortPrediction(String shortPrediction) {
		this.shortPrediction = shortPrediction;
	}
	public String getLongPrediction() {
		return longPrediction;
	}
	public void setLongPrediction(String longPrediction) {
		this.longPrediction = longPrediction;
	}
	public String getLowTemp() {
		return lowTemp;
	}
	public void setLowTemp(String lowTemp) {
		this.lowTemp = lowTemp;
	}
	public String getHighTemp() {
		return highTemp;
	}
	public void setHighTemp(String highTemp) {
		this.highTemp = highTemp;
	}
	public String getImageURL() {
		return imageURL;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public boolean isNight() {
		return isNight;
	}
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
