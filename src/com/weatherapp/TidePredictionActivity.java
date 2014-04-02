package com.weatherapp;

import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weatherapp.model.TidePrediction;
import com.weatherapp.service.ServiceFactory;

public class TidePredictionActivity extends BaseActivity {

    private SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
    private SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
    
	public TidePredictionActivity() {
		super();

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tide_prediction);

		long startTime = System.currentTimeMillis();

		buildContent();

		System.out.println("Total time = " + (System.currentTimeMillis() - startTime));

		configureToolbar();
	}
	
	@Override
	protected void buildContent() {
		ViewGroup predictionListView = (ViewGroup)findViewById(R.id.listViewTidePredictions);
		predictionListView.removeAllViews();
		
		//IOS
		new ProgressTask(this).execute(new String[]{"Isle of Springs", "43.86", "-69.6867"});

		//Portland
		new ProgressTask(this).execute(new String[]{"Portland", "43.6567", "-70.2467"});

	}

	
	private class ProgressTask extends AsyncTask<String, Void, List<TidePrediction>> {
		
	    private ProgressDialog dialog;
	    private Activity activity;
	    private String locationLabel;
	    
	    public ProgressTask(Activity activity) {
	        this.activity = activity;
	        dialog = new ProgressDialog(activity);
	    }

	    protected void onPreExecute() {
	        this.dialog.setMessage("Getting tide predictions...");
	        this.dialog.show();
	    }

	    @Override
	    protected void onPostExecute(final List<TidePrediction> tidePredictionList) {

			View row;
			TextView view;
			String currentDay = "";
			String tempDay;
			
	    	if (dialog.isShowing()) {
	            dialog.dismiss();
	        }

    		// Grab the scrollview and a LayoutInflater
			ViewGroup predictionListView = (ViewGroup)activity.findViewById(R.id.listViewTidePredictions);
			
			LayoutInflater inflater = (LayoutInflater) activity.getLayoutInflater();

			// Add a header for the location
			row = inflater.inflate(R.layout.tide_prediction_location_header_row, null);
			view = (TextView) row.findViewById(R.id.textViewTidePredictionLocation);
			view.setText(locationLabel);
			
			predictionListView.addView(row);

	        //Inflate and populate the tide prediction rows
			for (TidePrediction prediction : tidePredictionList) {
				tempDay = dayFormat.format(prediction.getDateTime());
				
				if (!tempDay.equals(currentDay)) {
					currentDay = tempDay;
					
					row = inflater.inflate(R.layout.tide_prediction_header_row, null);
					
					view = (TextView) row.findViewById(R.id.textViewTidePredictionDayOfWeek);
					view.setText(currentDay);
					
					predictionListView.addView(row);
				}
					
				// inflate and populate a new detail row
				row = inflater.inflate(R.layout.tide_prediction_detail_row, null);

				view = (TextView) row.findViewById(R.id.textViewTidePredictionTime);
				view.setText(timeFormat.format(prediction.getDateTime()));
	
				view = (TextView) row.findViewById(R.id.textViewTidePredictionType);
				view.setText(prediction.getTideType().toString());
		        
				view = (TextView) row.findViewById(R.id.textViewTidePredictionHeight);
				view.setText(Float.toString(prediction.getHeight()) + " feet");
				
				predictionListView.addView(row);
	    	}
			
	    }

	    protected List<TidePrediction> doInBackground(final String... args) {

	    	locationLabel = args[0];
	    	String lat = args[1];
	    	String lon = args[2];
	    	
	    	Location location = new Location("WeatherGeek");
			location.setLatitude(Location.convert(lat));
			location.setLongitude(Location.convert(lon));
			
			return ServiceFactory.getTidePredictionService().getTidePredictions(location);

	    }
	}
}

