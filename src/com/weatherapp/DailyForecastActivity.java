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

import com.weatherapp.model.DailyForecast;
import com.weatherapp.service.DailyForecastService;
import com.weatherapp.service.ServiceFactory;
import com.weatherapp.util.Preferences;
import com.weatherapp.viewadapter.DailyForecastListAdapter;

public class DailyForecastActivity extends BaseActivity {

	private List<DailyForecast> forecasts;
	private int nextLoadHour = 0;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.daily_forecast);

		if (savedInstanceState != null) {
			Log.d("WeatherGeek", "restoring daily forecasts");
			forecasts = (List<DailyForecast>)savedInstanceState.getSerializable("forecasts");
			nextLoadHour = savedInstanceState.getInt("nextLoadHour");
		} else {
			Log.d("WeatherGeek", "creating daily forecasts");
		}
		
		ServiceFactory.setWeatherServiceProvider(Preferences.getWeatherProvider(getApplicationContext()));
		
		buildContent();
		
		configureToolbar();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	  savedInstanceState.putSerializable("forecasts", (Serializable) forecasts);
	  savedInstanceState.putInt("nextLoadHour", nextLoadHour);
	  super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	protected void buildContent() {
		AsyncTask<String, Void, List<DailyForecast >> task = new ProgressTask(this);
		task.execute();
	}
	
	private class ProgressTask extends AsyncTask<String, Void, List<DailyForecast>> {

	    private ProgressDialog dialog;
	    private Activity activity;
	    
	    public ProgressTask(Activity activity) {
	        this.activity = activity;
	        dialog = new ProgressDialog(activity);
	    }

	    protected void onPreExecute() {
	        this.dialog.setMessage("Getting forecast...");
	        this.dialog.show();
	    }

	    @Override
	    protected void onPostExecute(final List<DailyForecast> forecasts) {
	        if (dialog.isShowing()) {
	            dialog.dismiss();
	        }

    		// Assign adapter to ListView
			ListView forecastList = (ListView) activity.findViewById(R.id.forecastList);
			forecastList.setAdapter(new DailyForecastListAdapter(activity, R.layout.daily_forecast, forecasts));
	    }

	    protected List<DailyForecast> doInBackground(final String... args) {
		    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			
			DailyForecastService forecastService = ServiceFactory.getDailyForecastService();
			forecasts = forecastService.getForecastByLocation(location);
	    	
			return forecasts;
	    }
	}

}