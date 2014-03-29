package com.weatherapp.service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.URI;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.location.Location;

import com.google.gson.Gson;
import com.weatherapp.model.Observation;
import com.weatherapp.util.WeatherbugImageUtil;

public class WeatherbugCurrentConditionsService {

	/**
	 * Get conditions for location provided by the system
	 * 
	 * @param location
	 * @return
	 */
	public static Observation getCurrentConditionsByLocation(final Location location) {
		return getCurrentConditions("la=" + location.getLatitude() + "&lo=" + location.getLongitude());
	}
	
	/**
	 * Get conditions for the provided zip code
	 * 
	 * @param zipCode
	 * @return
	 */
	public static Observation getCurrentConditionsByZipCode(String zipCode) {
		return getCurrentConditions("zip=" + zipCode);
	}
	
	/**
	 * Get conditions for the provided criteria string
	 * 
	 * @param criteria 
	 * @return the current conditions for the specified criteria
	 */
	public static Observation getCurrentConditions(final String criteria) {
		
		Observation observation = null;
		
		try {
			// Send request
			DefaultHttpClient httpClient = new DefaultHttpClient();
			URI uri = new URI("http://direct.weatherbug.com/DataService/GetObs.ashx?ic=1&" + criteria);

			HttpGet method = new HttpGet(uri);
			HttpResponse response = httpClient.execute(method);

			// Parse the response
			Reader reader = new InputStreamReader(response.getEntity().getContent());
			Gson parser = new Gson();
		    
			// Build the observation
			observation = parser.fromJson(reader, WeatherBugObservation.class);
			
		} catch (Exception e) {
			System.out.println("Something bad happened");
			e.printStackTrace();
		}
		
		return observation;

	}
	
	/**
	 * Class representing a weather observation provided by WeatherBug
	 */
	@SuppressWarnings("unused")
	private class WeatherBugObservation implements Observation, Serializable {
		private static final long serialVersionUID = 1L;
		private String stationName;
		private String stationId;
		private String dateTime;
		private String desc;
		private String humidity;
		private String temperature;
		private String temperatureHigh;
		private String temperatureLow;
		private String feelsLike;
		private String sunriseDateTime;
		private String sunsetDateTime;
		private String icon;
		
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
			return new Date(Long.valueOf(dateTime));
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
			return new Date(Long.valueOf(sunriseDateTime));
		}
		@Override
		public Date getSunset() {
			return new Date(Long.valueOf(sunsetDateTime));
		}
		@Override
		public String getImageName() {
			return icon;
		}
		@Override
		public String getImageURL() {
			return WeatherbugImageUtil.getWeatherbugIconUrl(icon, "90x76", false);
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

//	avgWindDeg
//	avgWindDirection
//	avgWindSpeed
//	dateTime
//	desc
//	dewpoint
//	feelsLike
//	feelsLikeLabel
//	gustDeg
//	gustDirection
//	gustSpeed
//	hasData
//	humidity
//	humidityHigh
//	humidityLow"
//	humidityRate
//	humidityUnits
//	icon
//	press
//	pressHigh
//	pressLow
//	pressRate
//	pressUnits
//	rainDaily"
//	rainMonthly
//	rainRate
//	rainUnits
//	rainYearly
//	rateUnits
//	stationId
//	stationName
//	sunriseDateTime
//	sunsetDateTime
//	temperature
//	temperatureHigh
//	temperatureLow
//	temperatureRate
//	temperatureUnits
//	windDeg
//	windDirection
//	windHist
//	windSpeed
//	windUnits

}
