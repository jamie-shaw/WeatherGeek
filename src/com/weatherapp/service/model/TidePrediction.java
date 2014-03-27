package com.weatherapp.service.model;

import java.util.Date;

public interface TidePrediction {

	public Date getDateTime();

	public TidePrediction.Type getTideType();

	public float getHeight();

	public enum Type {
		HI, LO;
	
		public String toString() {
			if (this == HI) {
				return "High";
			} else {
				return "Low";
			}
		}
	}
	
}
