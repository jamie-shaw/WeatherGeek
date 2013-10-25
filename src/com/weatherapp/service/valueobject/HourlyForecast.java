package com.weatherapp.service.valueobject;

import java.util.Date;

public interface HourlyForecast {

	public Date getDateTime();

	public String getWindChill();

	public String getTemp();

	public String getImageName();

	public String getImageURL();

	public String getLongPrediction();

}
