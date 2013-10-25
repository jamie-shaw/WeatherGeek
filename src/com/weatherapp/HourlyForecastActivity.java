package com.weatherapp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.weatherapp.service.WeatherbugHourlyForecastService;
import com.weatherapp.service.valueobject.HourlyForecast;
import com.weatherapp.util.Preferences;
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
			System.out.println("restoring hourly forecasts");
			forecasts = (List<HourlyForecast>)savedInstanceState.getSerializable("forecasts");
			nextLoadHour = savedInstanceState.getInt("nextLoadHour");;
		} else {
			System.out.println("creating hourly forecasts");
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
	    private boolean needToLoad;
	    
	    public ProgressTask(Activity activity) {
	        this.activity = activity;
	        dialog = new ProgressDialog(activity);
			needToLoad = (forecasts == null || new Date().getHours() > nextLoadHour); 
	    }

	    protected void onPreExecute() {
	    	if (needToLoad) {
		        this.dialog.setMessage("Getting forecast...");
		        this.dialog.show();
	    	}
	    }

	    @Override
	    protected void onPostExecute(final List<HourlyForecast>forecasts) {
	        if (dialog.isShowing()) {
	            dialog.dismiss();
	        }

    		// Assign adapter to ListView
			ListView forecastList = (ListView) activity.findViewById(R.id.forecastList);
			forecastList.setAdapter(new HourlyForecastListAdapter(activity, R.layout.daily_forecast, forecasts));
	    
	    }

	    protected List<HourlyForecast> doInBackground(final String... args) {

	    	if (needToLoad) {
				System.out.println("loading hourly forecasts");
				String zipCode = Preferences.getZipCode(activity.getBaseContext());
				forecasts = WeatherbugHourlyForecastService.getForecastsFromJSON(zipCode);
				nextLoadHour = new Date().getHours();
			}
			return forecasts;
	    }
	}

}