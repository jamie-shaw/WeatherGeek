package com.weatherapp.model;

import java.io.Serializable;
import java.util.Date;

import com.weatherapp.service.ServiceFactory;

public class DefaultObservation implements Observation, Serializable {

	private static final long serialVersionUID = 1L;
	
	protected String stationName;
	protected String stationId;
	protected String dateTime;
	protected String desc;
	protected String humidity;
	protected String temperature;
	protected String temperatureHigh;
	protected String temperatureLow;
	protected String feelsLike;
	protected String sunriseDateTime;
	protected String sunsetDateTime;
	protected String icon;

	@Override
	public String getLocation() {
		return stationName;
	}

	@Override
	public String getStation() {
		return stationId;
	}

	@Override
	public Date getObservationDate() {
		Date observationDate = new Date(Long.valueOf(dateTime));
		return observationDate;
	}

	@Override
	public String getCurrentCondition() {
		return desc;
	}

	@Override
	public String getHumidity() {
		return humidity;
	}

	@Override
	public String getCurrentTemp() {
		return temperature;
	}

	@Override
	public String getHiTemp() {
		return temperatureHigh;
	}

	@Override
	public String getLoTemp() {
		return temperatureLow;
	}

	@Override
	public String getWindChill() {
		return feelsLike;
	}

	@Override
	public Date getSunrise() {
		return sunriseDateTime == null ? null : new Date(Long.valueOf(sunriseDateTime));
	}

	@Override
	public Date getSunset() {
		return sunriseDateTime == null ? null : new Date(Long.valueOf(sunsetDateTime));
	}

	@Override
	public String getImageName() {
		return icon;
	}

	@Override
	public String getImageURL() {
		return ServiceFactory.getImageService().getIconUrl(icon);
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public void setTemperatureHigh(String temperatureHigh) {
		this.temperatureHigh = temperatureHigh;
	}

	public void setTemperatureLow(String temperatureLow) {
		this.temperatureLow = temperatureLow;
	}

	public void setFeelsLike(String feelsLike) {
		this.feelsLike = feelsLike;
	}

	public void setSunriseDateTime(String sunriseDateTime) {
		this.sunriseDateTime = sunriseDateTime;
	}

	public void setSunsetDateTime(String sunsetDateTime) {
		this.sunsetDateTime = sunsetDateTime;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
