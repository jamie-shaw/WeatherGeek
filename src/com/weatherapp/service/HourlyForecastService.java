package com.weatherapp.service;

import java.util.List;

import android.location.Location;

import com.weatherapp.model.HourlyForecast;

public interface HourlyForecastService {

	public abstract List<HourlyForecast> getForecastByLocation(Location location);

	public abstract List<HourlyForecast> getForecastByZipCode(String zipCode);

}