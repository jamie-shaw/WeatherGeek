package com.weatherapp.model;

public interface DailyForecast {

	public abstract String getLongDay();

	public abstract void setLongDay(String longDay);

	public abstract String getShortDay();

	public abstract void setShortDay(String shortDay);

	public abstract String getShortPrediction();

	public abstract void setShortPrediction(String shortPrediction);

	public abstract String getLongPrediction();

	public abstract void setLongPrediction(String longPrediction);

	public abstract String getTemperature();

	public abstract void setTemperature(String temperature);

	public abstract String getImageName();

	public abstract void setImageName(String imageName);

	public abstract boolean isNight();

	public abstract void setNight(boolean isNight);

	public abstract String toString();

}