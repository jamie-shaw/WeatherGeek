package com.weatherapp.model;

import java.io.Serializable;
import java.util.Date;

import com.weatherapp.service.ServiceFactory;

public class DefaultHourlyForecast implements HourlyForecast, Serializable {

	protected static final long serialVersionUID = 1L;

	protected String chancePrecip;
	protected long dateTime;
	protected String desc;
	protected String dewPoint;
	protected String feelsLike;
	protected String feelsLikeLabel;
	protected String humidity;
	protected String icon;
	protected String skyCover;
	protected String temperature;
	protected String windDir;
	protected String windSpeed;
	protected boolean daylight;

	@Override
	public String getWindChill() {
		return feelsLike;
	}

	@Override
	public String getTemp() {
		return temperature;
	}

	@Override
	public String getImageName() {
		return icon;
	}

	@Override
	public String getImageURL() {
		return ServiceFactory.getImageService().getIconUrl(icon);
	}

	@Override
	public String getLongPrediction() {
		return desc;
	}

	@Override
	public Date getDateTime() {
		return new Date(Long.valueOf(dateTime));
	}

	@Override
	public boolean isDaylight() {
		return daylight;
	}
	
	public void setChancePrecip(String chancePrecip) {
		this.chancePrecip = chancePrecip;
	}
	
	public void setDateTime(String dateTime) {
		long dateTimeSeconds = Long.parseLong(dateTime);
		this.dateTime = dateTimeSeconds * 1000L;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setDewPoint(String dewPoint) {
		this.dewPoint = dewPoint;
	}

	public void setFeelsLike(String feelsLike) {
		this.feelsLike = feelsLike;
	}

	public void setFeelsLikeLabel(String feelsLikeLabel) {
		this.feelsLikeLabel = feelsLikeLabel;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setSkyCover(String skyCover) {
		this.skyCover = skyCover;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public void setWindDir(String windDir) {
		this.windDir = windDir;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}
	public void setDaylight(String daylight) {
		this.daylight = Boolean.parseBoolean(daylight);
	}
}
