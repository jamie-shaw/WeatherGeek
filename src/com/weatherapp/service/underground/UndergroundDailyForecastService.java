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

import com.weatherapp.model.DailyForecast;
import com.weatherapp.model.DefaultDailyForecast;
import com.weatherapp.service.DailyForecastService;

public class UndergroundDailyForecastService implements DailyForecastService {

	/* (non-Javadoc)
	 * @see com.weatherapp.service.DailyForecastService#getForecastByLocation(android.location.Location)
	 */
	@Override
	public List<DailyForecast> getForecastByLocation(Location location) {
		return getForecast(location.getLatitude() + "," + location.getLongitude());
	}

	/* (non-Javadoc)
	 * @see com.weatherapp.service.DailyForecastService#getForecastByZipCode(java.lang.String)
	 */
	@Override
	public List<DailyForecast> getForecastByZipCode(String zipCode) {
		return getForecast(zipCode);
	}

	private List<DailyForecast> getForecast(String criteria) {

		List<DailyForecast> forecasts = new ArrayList<DailyForecast>();

		try {
			// Send the request
			DefaultHttpClient httpClient = new DefaultHttpClient();
			URI uri = new URI("http://api.wunderground.com/api/2b2713188f60c01d/forecast10day/q/" + criteria + ".json");

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
		    JSONArray textForecastList = base.getJSONObject("forecast").getJSONObject("txt_forecast").getJSONArray("forecastday"); 
		    JSONArray simpleForecastList = base.getJSONObject("forecast").getJSONObject("simpleforecast").getJSONArray("forecastday"); 
		    
			// Build the forecast list
			for (int i=0; i<textForecastList.length(); i++) {
				JSONObject textForecast = textForecastList.getJSONObject(i);
				DefaultDailyForecast forecast = new DefaultDailyForecast();
				
				forecast.setLongDay(textForecast.getString("title"));
				forecast.setLongPrediction(textForecast.getString("fcttext"));
	
				int j = (int) (i * 0.5);

				if (i % 2 == 0) {
					// day
					forecast.setImageName(textForecast.getString("icon"));
					forecast.setTemperature(simpleForecastList.getJSONObject(j).getJSONObject("high").getString("fahrenheit"));
					forecast.setDaylight(true);
				} else {
					// night
					forecast.setImageName("nt_" + textForecast.getString("icon"));
					forecast.setTemperature(simpleForecastList.getJSONObject(j).getJSONObject("low").getString("fahrenheit"));	
					forecast.setDaylight(false);
				}
				
				forecasts.add(forecast);
			}

		} catch (Exception e) {
			System.out.println("Something bad happened");
			e.printStackTrace();
		}

		return forecasts;

	}

}
