package com.weatherapp.service.weatherbug;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.Date;
import java.util.TimeZone;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.location.Location;

import com.google.gson.Gson;
import com.weatherapp.model.DefaultObservation;
import com.weatherapp.model.Observation;
import com.weatherapp.service.CurrentConditionsService;
import com.weatherapp.service.ServiceFactory;

public class WeatherbugCurrentConditionsService implements CurrentConditionsService {

	/**
	 * Get conditions for location provided by the system
	 * 
	 * @param location
	 * @return
	 */
	public Observation getCurrentConditionsByLocation(final Location location) {
		return getCurrentConditions("la=" + location.getLatitude() + "&lo=" + location.getLongitude());
	}
	
	/**
	 * Get conditions for the provided zip code
	 * 
	 * @param zipCode
	 * @return
	 */
	public Observation getCurrentConditionsByZipCode(final String zipCode) {
		return getCurrentConditions("zip=" + zipCode);
	}
	
	/**
	 * Get conditions for the provided criteria string
	 * 
	 * @param criteria 
	 * @return the current conditions for the specified criteria
	 */
	private static Observation getCurrentConditions(final String criteria) {
		
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
	private class WeatherBugObservation extends DefaultObservation {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Date getObservationDate() {
			return convertToDate(dateTime);
		}
		@Override
		public Date getSunrise() {
			return convertToDate(sunriseDateTime);
		}
		@Override
		public Date getSunset() {
			return convertToDate(sunsetDateTime);
		}
		@Override
		public String getImageURL() {
			return ServiceFactory.getImageService().getIconUrl(icon);
		}

		@Override
		public String getHumidity() {
			return humidity + "%";
		}
		
		private Date convertToDate(String dateTime) {
			TimeZone timeZone = TimeZone.getDefault();
			
			long gmtMillis = Long.parseLong(dateTime);
			long offset = timeZone.getRawOffset(); 
			long dst = timeZone.inDaylightTime(new Date()) ? timeZone.getDSTSavings() : 0;
					
			return new Date(Long.valueOf(gmtMillis - offset - dst));
		}
	}

}
