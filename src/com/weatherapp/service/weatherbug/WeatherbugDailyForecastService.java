package com.weatherapp.service.weatherbug;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.location.Location;

import com.google.gson.Gson;
import com.weatherapp.model.DailyForecast;
import com.weatherapp.model.DefaultDailyForecast;
import com.weatherapp.service.DailyForecastService;

public class WeatherbugDailyForecastService implements DailyForecastService {

	@Override
	public List<DailyForecast> getForecastByLocation(Location location) {
		return getForecast("la=" + location.getLatitude() + "&lo=" + location.getLongitude());
	}

	@Override
	public List<DailyForecast> getForecastByZipCode(String zipCode) {
		return getForecast("zip=" + zipCode);
	}

	private static List<DailyForecast> getForecast(String criteria) {

		List<DailyForecast> forecasts = new ArrayList<DailyForecast>();

		try {
			// Send request
			DefaultHttpClient httpClient = new DefaultHttpClient();
			URI uri = new URI("http://direct.weatherbug.com/DataService/GetForecast.ashx?nf=7&ih=1&" + criteria);

			HttpGet method = new HttpGet(uri);
			HttpResponse response = httpClient.execute(method);

			// Parse the response
			Reader reader = new InputStreamReader(response.getEntity().getContent());
			Gson parser = new Gson();

			// Build the forecast list
			List<WeatherBugForecast> wbForecasts = parser.fromJson(reader, WeatherBugForecastList.class).getForecastList();
			DefaultDailyForecast forecast;

			for (WeatherBugForecast currentWbForecast : wbForecasts) {

				if (currentWbForecast.hasDay()) {
					// Build daytime forecast
					forecast = new DefaultDailyForecast();

					forecast.setImageName(currentWbForecast.getDayIcon());
					forecast.setLongPrediction(currentWbForecast.getDayPred());
					forecast.setTemperature(currentWbForecast.getHigh());
					forecast.setLongDay(currentWbForecast.getDayTitle());
					forecast.setDaylight(true);
					forecasts.add(forecast);
				}

				if (currentWbForecast.hasNight()) {
					// Build night forecast
					forecast = new DefaultDailyForecast();

					forecast.setImageName(currentWbForecast.getNightIcon());
					forecast.setLongPrediction(currentWbForecast.getNightPred());
					forecast.setTemperature(currentWbForecast.getLow());
					forecast.setLongDay(currentWbForecast.getNightTitle());
					forecast.setDaylight(false);
					forecasts.add(forecast);
				}
			}

		} catch (Exception e) {
			System.out.println("Something bad happened");
			e.printStackTrace();
		}

		return forecasts;

	}

	@SuppressWarnings("unused")
	private class WeatherBugForecastList {

		private List<WeatherBugForecast> forecastList;
		private String temperatureUnits;
		private String windUnits;

		public List<WeatherBugForecast> getForecastList() {
			return forecastList;
		}

		public void setForecastList(List<WeatherBugForecast> forecastList) {
			this.forecastList = forecastList;
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
	private class WeatherBugForecast {
		String title;
		String dateTime;
		String hasDay;
		String hasNight;
		String high;
		String low;

		String dayDesc;
		String dayIcon;
		String dayPred;
		String dayTitle;

		String nightDesc;
		String nightIcon;
		String nightPred;
		String nightTitle;

		public boolean hasDay() {
			return Boolean.valueOf(hasDay);
		}

		public boolean hasNight() {
			return Boolean.valueOf(hasNight);
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDateTime() {
			return dateTime;
		}

		public void setDateTime(String dateTime) {
			this.dateTime = dateTime;
		}

		public String getHasDay() {
			return hasDay;
		}

		public void setHasDay(String hasDay) {
			this.hasDay = hasDay;
		}

		public String getHasNight() {
			return hasNight;
		}

		public void setHasNight(String hasNight) {
			this.hasNight = hasNight;
		}

		public String getHigh() {
			return high;
		}

		public void setHigh(String high) {
			this.high = high;
		}

		public String getLow() {
			return low;
		}

		public void setLow(String low) {
			this.low = low;
		}

		public String getDayDesc() {
			return dayDesc;
		}

		public void setDayDesc(String dayDesc) {
			this.dayDesc = dayDesc;
		}

		public String getDayIcon() {
			return dayIcon;
		}

		public void setDayIcon(String dayIcon) {
			this.dayIcon = dayIcon;
		}

		public String getDayPred() {
			return dayPred;
		}

		public void setDayPred(String dayPred) {
			this.dayPred = dayPred;
		}

		public String getDayTitle() {
			return dayTitle;
		}

		public void setDayTitle(String dayTitle) {
			this.dayTitle = dayTitle;
		}

		public String getNightDesc() {
			return nightDesc;
		}

		public void setNightDesc(String nightDesc) {
			this.nightDesc = nightDesc;
		}

		public String getNightIcon() {
			return nightIcon;
		}

		public void setNightIcon(String nightIcon) {
			this.nightIcon = nightIcon;
		}

		public String getNightPred() {
			return nightPred;
		}

		public void setNightPred(String nightPred) {
			this.nightPred = nightPred;
		}

		public String getNightTitle() {
			return nightTitle;
		}

		public void setNightTitle(String nightTitle) {
			this.nightTitle = nightTitle;
		}

	}
}
