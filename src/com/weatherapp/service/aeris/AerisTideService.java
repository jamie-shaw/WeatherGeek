package com.weatherapp.service.aeris;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.location.Location;

import com.google.gson.Gson;
import com.weatherapp.model.TidePrediction;

public class AerisTideService {

    private static final DateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");

	public static List<TidePrediction> getTidePredictions(Location location) {

		List<TidePrediction> predictions = new ArrayList<TidePrediction>();

		try {
			// Send request
			DefaultHttpClient httpClient = new DefaultHttpClient();
			String address = "http://api.aerisapi.com/tides/?p=" + location.getLatitude() + "," + location.getLongitude() +
							  "&to=+2day&client_id=uyqjxRORjLchQC1xqytKP&client_secret=9VeXOnr0Z6IV6WLfnbwK1AmiyqY1WpXDuDGE4WBE";

			HttpGet method = new HttpGet(new URI(address));
			HttpResponse response = httpClient.execute(method);

			// Parse the response
			Reader reader = new InputStreamReader(response.getEntity().getContent());
			Gson parser = new Gson();

			// Build the observation
			AerisTideResponseWrapper aerisResponseWrapper = parser.fromJson(reader, AerisTideResponseWrapper.class);
			AerisTideResponse aerisResponse = aerisResponseWrapper.getResponse().get(0);

			for (TidePrediction prediction : aerisResponse.getPeriods()) {
				predictions.add(prediction);
			}

		} catch (Exception e) {
			System.out.println("Something bad happened while getting tide predictions");
			e.printStackTrace();
		}

		return predictions;

	}

	/**
	 * Class representing a weather observation provided by WeatherBug
	 */
	@SuppressWarnings("unused")
	private class AerisTideResponseWrapper implements Serializable {
		private static final long serialVersionUID = 1L;

		private boolean success = false;
		private String error;
		private List<AerisTideResponse> response;

		public List<AerisTideResponse> getResponse() {
			return response;
		}

		public void setResponse(List<AerisTideResponse> response) {
			this.response = response;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public void setError(String error) {
			this.error = error;
		}
	}

	/**
	 * Class representing a weather observation provided by WeatherBug
	 */
	@SuppressWarnings("unused")
	private class AerisTideResponse implements Serializable {
		private static final long serialVersionUID = 1L;

		private AerisPlace place;
		private List<AerisPeriod> periods;
		
		public void setPlace(AerisPlace place) {
			this.place = place;
		}
		public void setPeriods(List<AerisPeriod> periods) {
			this.periods = periods;
		}
		public List<AerisPeriod> getPeriods() {
			return periods;
		}

	}

	@SuppressWarnings("unused")
	private class AerisPeriod implements TidePrediction, Serializable {
		private static final long serialVersionUID = 1L;

		private String dateTimeISO;
		private String type;
		private Float heightFT;

		@Override
		public Type getTideType() {
			return type.equals("h") ? TidePrediction.Type.HI : TidePrediction.Type.LO;
		}

		@Override
		public float getHeight() {
			return heightFT;
		}

		@Override
		public Date getDateTime() {
		    Date date = null;
			try {
				date = isoDateFormat.parse(dateTimeISO);
		    
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}

		public void setDateTimeISO(String dateTimeISO) {
			this.dateTimeISO = dateTimeISO;
		}

		public void setType(String type) {
			this.type = type;
		}

		public void setHeightFT(Float heightFT) {
			this.heightFT = heightFT;
		}

	}

	@SuppressWarnings("unused")
	private class AerisPlace implements Serializable {
		private static final long serialVersionUID = 1L;

		private String name;
		private String state;

		public void setName(String name) {
			this.name = name;
		}

		public void setState(String state) {
			this.state = state;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		private String country;
	}

}
