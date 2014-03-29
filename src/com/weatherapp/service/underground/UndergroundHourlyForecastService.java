package com.weatherapp.service.underground;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.location.Location;

import com.weatherapp.model.DefaultHourlyForecast;
import com.weatherapp.model.HourlyForecast;
import com.weatherapp.service.HourlyForecastService;

public class UndergroundHourlyForecastService implements HourlyForecastService {

	private static final int INTERVAL = 2;
	private static final int PERIODS = 24;

	public List<HourlyForecast> getForecastByLocation(Location location) {
		return getForecast(location.getLatitude() + "," + location.getLongitude());
	}

	public List<HourlyForecast> getForecastByZipCode(String zipCode) {
		return getForecast(zipCode);
	}
	
	private static List<HourlyForecast> getForecast(String criteria) {
		
		List<HourlyForecast> forecasts = new ArrayList<HourlyForecast>();
		
		try {
			// Send request
			DefaultHttpClient httpClient = new DefaultHttpClient();
			URI uri = new URI("http://api.wunderground.com/api/2b2713188f60c01d/hourly/q/" + criteria + ".json");

			HttpGet method = new HttpGet(uri);
			HttpResponse response = httpClient.execute(method);

			// Parse the response
		    BufferedReader streamReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		    StringBuilder responseStrBuilder = new StringBuilder();

		    String inputStr;
		    while ((inputStr = streamReader.readLine()) != null) {
		        responseStrBuilder.append(inputStr);
		    }
		    
		    JSONObject base = new JSONObject(responseStrBuilder.toString());
		    JSONArray jsonForecastList = base.getJSONArray("hourly_forecast"); 
		    
			// Build the forecast list
		    int forecastNumber = 0;
			
			while (forecastNumber <= PERIODS) {
				
		    	if (forecastNumber == 0 || forecastNumber % INTERVAL == 0) { 
		    		
			    	JSONObject textForecast = jsonForecastList.getJSONObject(forecastNumber);
					UndergroundHourlyForecast forecast = new UndergroundHourlyForecast();
					
					forecast.setDesc(textForecast.getString("condition"));
					forecast.setIcon(textForecast.getString("icon"));
					forecast.setDateTime(textForecast.getJSONObject("FCTTIME").getString("epoch"));
					forecast.setTemperature(textForecast.getJSONObject("temp").getString("english"));
					forecast.setFeelsLike(textForecast.getJSONObject("feelslike").getString("english"));
					
					forecasts.add(forecast);
		    	}
		    	
		    	forecastNumber++;
			}
		    
		} catch (Exception e) {
			System.out.println("Something bad happened");
			e.printStackTrace();
		}
		
		return forecasts;

	}
	
	private static class UndergroundHourlyForecast extends DefaultHourlyForecast {

		private static final long serialVersionUID = 1L;

		@Override
		public void setDateTime(String dateTime) {
			long dateTimeSeconds = Long.parseLong(dateTime);
			this.dateTime = dateTimeSeconds * 1000L;
		}

	}
}
