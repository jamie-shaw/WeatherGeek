package com.weatherapp.service.weatherbug;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.location.Location;

import com.google.gson.Gson;
import com.weatherapp.model.HourlyForecast;
import com.weatherapp.service.HourlyForecastService;
import com.weatherapp.service.ServiceFactory;

public class WeatherbugHourlyForecastService implements HourlyForecastService {

	private static final int INTERVAL = 2;
	private static final int PERIODS = 24;
	
	@Override
	public List<HourlyForecast> getForecastByLocation(Location location) {
		return getForecast("la=" + location.getLatitude() + "&lo=" + location.getLongitude());
	}

	@Override
	public List<HourlyForecast> getForecastByZipCode(String zipCode) {
		return getForecast("zip=" + zipCode);
	}
	
	private List<HourlyForecast> getForecast(String criteria) {
		
		List<HourlyForecast> forecasts = new ArrayList<HourlyForecast>();
		
		try {
			// Send request
			DefaultHttpClient httpClient = new DefaultHttpClient();
			URI uri = new URI("http://direct.Weatherbug.com/DataService/GetForecastHourly.ashx?ht=t&ht=i&ht=d&ht=fl&" + criteria);

			HttpGet method = new HttpGet(uri);
			HttpResponse response = httpClient.execute(method);

			// Parse the response
			Reader reader = new InputStreamReader(response.getEntity().getContent());
			Gson parser = new Gson();
		    
			// Build the forecast list
			List<WeatherbugHourlyForecast> wbForecasts = parser.fromJson(reader, WeatherbugHourlyForecastList.class).getForecastHourlyList();

			int forecastNumber = 0;
			
			while (forecastNumber <= PERIODS) {

				if (forecastNumber == 0 || forecastNumber % INTERVAL == 0) { 
					forecasts.add(wbForecasts.get(forecastNumber));
				}
				
		    	forecastNumber++;
		    }
		    
		} catch (Exception e) {
			System.out.println("Something bad happened");
			e.printStackTrace();
		}
		
		return forecasts;

	}
	
	@SuppressWarnings("unused")
	private class WeatherbugHourlyForecastList {
		
		private List<WeatherbugHourlyForecast> forecastHourlyList;
		private String temperatureUnits;
		private String windUnits;
		
		public List<WeatherbugHourlyForecast> getForecastHourlyList() {
			return forecastHourlyList;
		}

		public void setForecastHourlyList(List<WeatherbugHourlyForecast> forecastHourlyList) {
			this.forecastHourlyList = forecastHourlyList;
		}
		public String getTemperatureUnits() {
			return temperatureUnits;
		}
		public void setTemperatureUnits(String temperatureUnits) {
			this.temperatureUnits = temperatureUnits;
		}
		public String getWindUnits() {
			return windUnits;
		}
		public void setWindUnits(String windUnits) {
			this.windUnits = windUnits;
		}
	}
	
	@SuppressWarnings("unused")
	private class WeatherbugHourlyForecast implements HourlyForecast, Serializable {

		private static final long serialVersionUID = 1L;
		private String chancePrecip;
		private String dateTime;
		private String desc;
		private String dewPoint;
		private String feelsLike;
		private String feelsLikeLabel;
		private String humidity;
		private String icon;
		private String skyCover;
		private String temperature;
		private String windDir;
		private String windSpeed;
		
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
		public void setChancePrecip(String chancePrecip) {
			this.chancePrecip = chancePrecip;
		}
		public Date getDateTime() {
			long offset = TimeZone.getDefault().getRawOffset(); 
			long dst =  TimeZone.getDefault().getDSTSavings();
			long millis = Long.parseLong(dateTime);
			
			return new Date(Long.valueOf(millis - offset - dst));
		}
		public void setDateTime(String dateTime) {
			this.dateTime = dateTime;
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

	}

}
