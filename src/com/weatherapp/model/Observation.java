package com.weatherapp.model;

import java.util.Date;

public interface Observation {
	
	public String getLocation();
	
	public String getStation();
	
	public Date getObservationDate();
	
	public String getCurrentCondition();
	
	public String getHumidity();
	
	public String getCurrentTemp();
	
	public String getHiTemp();
	
	public String getLoTemp();
	
	public String getWindChill();
	
	public Date getSunrise();
	
	public Date getSunset();
	
	public String getImageName();
	
	public String getImageURL();

}
