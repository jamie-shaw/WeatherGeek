package com.weatherapp.service;

import java.util.List;

import android.location.Location;

import com.weatherapp.model.TidePrediction;

public interface TidePredictionService {

	public abstract List<TidePrediction> getTidePredictions(Location location);

}