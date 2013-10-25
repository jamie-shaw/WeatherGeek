package com.weatherapp;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.weatherapp.service.WeatherbugCurrentConditionsService;
import com.weatherapp.service.valueobject.Observation;
import com.weatherapp.task.DownloadImageTask;

public class CurrentConditionsActivity extends BaseActivity {

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    
	public CurrentConditionsActivity() {
		super();
		timeFormat = new SimpleDateFormat("h:mm a");
		timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		dateFormat = new SimpleDateFormat("EEEE, MMMMM d");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.current_conditions);

		long startTime = System.currentTimeMillis();

		buildContent();

		System.out.println("Total time = " + (System.currentTimeMillis() - startTime));

		configureToolbar();
	}
	
	@Override
	protected void buildContent() {

		AsyncTask<String, Void, Observation> task = new ProgressTask(this);
		task.execute();
		
	}

	
	private class ProgressTask extends AsyncTask<String, Void, Observation> {

	    private ProgressDialog dialog;
	    private Activity activity;
	    
	    public ProgressTask(Activity activity) {
	        this.activity = activity;
	        dialog = new ProgressDialog(activity);
	    }

	    protected void onPreExecute() {
	        this.dialog.setMessage("Getting current conditions...");
	        this.dialog.show();
	    }

	    @Override
	    protected void onPostExecute(final Observation observation) {
	       
	    	if (dialog.isShowing()) {
	            dialog.dismiss();
	        }

	    	ImageView imageView = (ImageView) activity.findViewById(R.id.imgCurrentConditions);
			AsyncTask<ImageView, Void, Bitmap> task = new DownloadImageTask(observation.getImageName(), observation.getImageURL());
			task.execute(imageView);
				
			TextView view = (TextView) activity.findViewById(R.id.textViewDate);
			view.setText(dateFormat.format(observation.getObservationDate()));
			
			view = (TextView) activity.findViewById(R.id.textViewTime);
			view.setText(timeFormat.format(observation.getObservationDate()));
			
			view = (TextView) activity.findViewById(R.id.textViewStation);
			view.setText(observation.getLocation());

			view = (TextView) activity.findViewById(R.id.textViewCurrentConditions);
			view.setText(observation.getCurrentCondition());

			view = (TextView) activity.findViewById(R.id.textViewTemperature);
			view.setText(observation.getCurrentTemp() + '\u00B0' + "F");

			view = (TextView) activity.findViewById(R.id.textViewHumidity);
			view.setText(observation.getHumidity() + "%");

			view = (TextView) activity.findViewById(R.id.textViewHiTemp);
			view.setText(observation.getHiTemp() + '\u00B0' + "F");

			view = (TextView) activity.findViewById(R.id.textViewLoTemp);
			view.setText(observation.getLoTemp() + '\u00B0' + "F");

			view = (TextView) activity.findViewById(R.id.textViewWindChill);
			view.setText(observation.getWindChill() + '\u00B0' + "F");
			
			view = (TextView) activity.findViewById(R.id.textViewSunrise);
			view.setText(timeFormat.format(observation.getSunrise()));
			
			view = (TextView) activity.findViewById(R.id.textViewSunset);
			view.setText(timeFormat.format(observation.getSunset()));
	    
	    }

	    protected Observation doInBackground(final String... args) {

	    	System.out.println("loading current conditions");
	    	LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			return WeatherbugCurrentConditionsService.getCurrentConditionsByLocation(location);

	    }
	}
}

