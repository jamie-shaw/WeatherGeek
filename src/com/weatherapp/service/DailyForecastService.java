package com.weatherapp.service;

import java.util.List;

import android.location.Location;

import com.weatherapp.model.DailyForecast;

public interface DailyForecastService {

	public abstract List<DailyForecast> getForecastByLocation(Location location);

	public abstract List<DailyForecast> getForecastByZipCode(String zipCode);

}