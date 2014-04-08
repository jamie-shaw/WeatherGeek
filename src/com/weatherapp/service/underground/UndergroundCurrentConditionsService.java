package com.weatherapp.service.underground;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.location.Location;

import com.weatherapp.model.DefaultObservation;
import com.weatherapp.model.Observation;
import com.weatherapp.service.CurrentConditionsService;

public class UndergroundCurrentConditionsService implements
		CurrentConditionsService {

	/**
	 * Get conditions for the specified location
	 * 
	 * @param location
	 * @return the current conditions for the specified location
	 */
	public Observation getCurrentConditionsByLocation(final Location location) {
		return getCurrentConditions(location.getLatitude() + "," + location.getLongitude());
	}

	/**
	 * Get conditions for the specified zip code
	 * 
	 * @param zipCode
	 * @return the current conditions for the specified zip code
	 */
	public Observation getCurrentConditionsByZipCode(final String zipCode) {
		return getCurrentConditions("zip=" + zipCode);
	}

	/**
	 * Get conditions for the specified criteria
	 * 
	 * @param criteria
	 * @return the current conditions for the specified criteria
	 */
	private static Observation getCurrentConditions(final String criteria) {

		UndergroundObservation observation = null;

		try {
			// Send request
			DefaultHttpClient httpClient = new DefaultHttpClient();
			URI uri = new URI("http://api.wunderground.com/api/2b2713188f60c01d/conditions/astronomy/q/" + criteria + ".json");

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
			JSONObject jsonObservation = base.getJSONObject("current_observation");

			// Build the observation
			observation = new UndergroundObservation();

			observation.setDateTime(jsonObservation.getString("local_epoch") + "000");
			observation.setDesc(jsonObservation.getString("weather"));
			observation.setFeelsLike(jsonObservation.getString("feelslike_f"));
			observation.setHumidity(jsonObservation.getString("relative_humidity"));
			observation.setIcon(jsonObservation.getString("icon"));
			observation.setTemperature(jsonObservation.getString("temp_f"));
			observation.setTemperatureHigh(jsonObservation.getString("temp_f"));
			observation.setTemperatureLow(jsonObservation.getString("temp_f"));
			observation.setStationName(jsonObservation.getJSONObject("observation_location").getString("city"));
			observation.setStationId(jsonObservation.getString("station_id"));

			JSONObject jsonSunPhase = base.getJSONObject("sun_phase");
			JSONObject jsonSunrise = jsonSunPhase.getJSONObject("sunrise");
			JSONObject jsonSunset = jsonSunPhase.getJSONObject("sunset");
		
			observation.setSunriseDateTime(jsonSunrise.getString("hour") + ":" + jsonSunrise.getString("minute"));
			observation.setSunsetDateTime(jsonSunset.getString("hour") + ":"+ jsonSunset.getString("minute"));
			
		} catch (Exception e) {
			System.out.println("Something bad happened");
			e.printStackTrace();
		}

		return observation;

	}

	private static class UndergroundObservation extends DefaultObservation {

		private static final long serialVersionUID = 1L;

		@Override
		public Date getSunrise() {
			if (sunriseDateTime != null) {
				return convertToDate(sunriseDateTime);

			} else {
				return null;
			}
		}
		
		@Override
		public Date getSunset() {
			if (sunsetDateTime != null) {
				return convertToDate(sunsetDateTime);

			} else {
				return null;
			}
		}
		
		private static Date convertToDate(String sunphaseTime) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
			
			Date time = null;
			
			try {
				time = dateFormat.parse(sunphaseTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return time;
		}
	}


}
