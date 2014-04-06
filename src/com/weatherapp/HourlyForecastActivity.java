package com.weatherapp;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.weatherapp.model.HourlyForecast;
import com.weatherapp.service.HourlyForecastService;
import com.weatherapp.service.ServiceFactory;
import com.weatherapp.viewadapter.HourlyForecastListAdapter;

public class HourlyForecastActivity extends BaseActivity {

	private List<HourlyForecast> forecasts;
	private int nextLoadHour;

	/** Called when the activity is first created. */
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null) {
			Log.d("WeatherGeek", "restoring hourly forecasts");
			forecasts = (List<HourlyForecast>)savedInstanceState.getSerializable("forecasts");
			nextLoadHour = savedInstanceState.getInt("nextLoadHour");;
		} else {
			Log.d("WeatherGeek", "creating hourly forecasts");
		}
		setContentView(R.layout.hourly_forecast);

		buildContent();

		configureToolbar();
	}

	@Override
	protected void buildContent() {
		AsyncTask<String, Void, List<HourlyForecast>> task = new ProgressTask(this);
		task.execute();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	  savedInstanceState.putSerializable("forecasts", (Serializable) forecasts);
	  savedInstanceState.putInt("nextLoadHour", nextLoadHour);
	  super.onSaveInstanceState(savedInstanceState);
	}

	
	private class ProgressTask extends AsyncTask<String, Void, List<HourlyForecast>> {

	    private ProgressDialog dialog;
	    private Activity activity;
	    
	    public ProgressTask(Activity activity) {
	        this.activity = activity;
	        dialog = new ProgressDialog(activity);
	    }

	    protected void onPreExecute() {
		        this.dialog.setMessage("Getting forecast...");
	    }

	    @Override
	    protected void onPostExecute(final List<HourlyForecast>forecasts) {
	        if (dialog.isShowing()) {
	            dialog.dismiss();
	        }

    		// Assign adapter to ListView
			ListView forecastList = (ListView) activity.findViewById(R.id.forecastList);
			forecastList.setAdapter(new HourlyForecastListAdapter(activity, R.layout.hourly_forecast, forecasts));
	    
	    }

	    protected List<HourlyForecast> doInBackground(final String... args) {

    		Log.d("WeatherGeek", "loading hourly forecasts");
			
		    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			
			HourlyForecastService forecastService = ServiceFactory.getHourlyForecastService();
			forecasts = forecastService.getForecastByLocation(location);
				
			return forecasts;
	    }
	}

}