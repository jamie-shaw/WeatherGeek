package com.weatherapp.service;

import com.weatherapp.service.aeris.AerisTidePredictionService;
import com.weatherapp.service.underground.UndergroundCurrentConditionsService;
import com.weatherapp.service.underground.UndergroundDailyForecastService;
import com.weatherapp.service.underground.UndergroundHourlyForecastService;
import com.weatherapp.service.underground.UndergroundImageService;
import com.weatherapp.service.weatherbug.WeatherbugCurrentConditionsService;
import com.weatherapp.service.weatherbug.WeatherbugDailyForecastService;
import com.weatherapp.service.weatherbug.WeatherbugHourlyForecastService;
import com.weatherapp.service.weatherbug.WeatherbugImageService;

public class ServiceFactory {

	private static ServiceProvider currentServiceProvider = ServiceProvider.UNDERGROUND;
	
	private enum ServiceProvider {
		WEATHERBUG, UNDERGROUND, AERIS;
	}

	public static DailyForecastService getDailyForecastService() {

		switch (currentServiceProvider) {
			case WEATHERBUG:
				return new WeatherbugDailyForecastService();
	
			case UNDERGROUND:
			default:
				return new UndergroundDailyForecastService();
		}

	}

	public static HourlyForecastService getHourlyForecastService() {
		
		switch (currentServiceProvider) {
			case WEATHERBUG:
				return new WeatherbugHourlyForecastService();
	
			case UNDERGROUND:
			default:
				return new UndergroundHourlyForecastService();
		}
	}


	public static CurrentConditionsService getCurrentConditionsService() {
		
		switch (currentServiceProvider) {
			case WEATHERBUG:
				return new WeatherbugCurrentConditionsService();
	
			case UNDERGROUND:
			default:
				return new UndergroundCurrentConditionsService();
		}
	}
	
	public static ImageService getImageService() {
		switch (currentServiceProvider) {
			case WEATHERBUG:
				return new WeatherbugImageService();
	
			case UNDERGROUND:
			default:
				return new UndergroundImageService();
		}
	}

	public static TidePredictionService getTidePredictionService() {
		return new AerisTidePredictionService();
	}
}
