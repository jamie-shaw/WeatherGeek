package com.weatherapp.service;

import android.location.Location;

import com.weatherapp.model.Observation;

public interface CurrentConditionsService {

	public Observation getCurrentConditionsByLocation(Location location);
	
	public Observation getCurrentConditionsByZipCode(String zipCode);
	
}