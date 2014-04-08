package com.weatherapp.model;

public interface DailyForecast {

	public String getLongDay();

	public void setLongDay(String longDay);

	public String getShortDay();

	public void setShortDay(String shortDay);

	public String getShortPrediction();

	public void setShortPrediction(String shortPrediction);

	public String getLongPrediction();

	public String getTemperature();

	public String getImageName();

	public boolean isDaylight();

}