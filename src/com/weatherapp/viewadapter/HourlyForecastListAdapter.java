
package com.weatherapp.viewadapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weatherapp.R;
import com.weatherapp.model.HourlyForecast;
import com.weatherapp.task.RetrieveImageTask;

public class HourlyForecastListAdapter extends ArrayAdapter<HourlyForecast>{

    private List<HourlyForecast> forecasts;
    private SimpleDateFormat dateFormat;
    LayoutInflater inflater;
    
    public HourlyForecastListAdapter(Context context, int textViewResourceId, List<HourlyForecast> forecasts) {
            super(context, textViewResourceId, forecasts);
            
            inflater = LayoutInflater.from(context);
			dateFormat = new SimpleDateFormat("h:mm a");

            this.forecasts = forecasts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    	if (convertView == null) {
    		//Inflate and populate the forecast row
    		convertView = inflater.inflate(R.layout.hourly_forecast_row, null);
    	}
    	
	    HourlyForecast forecast = forecasts.get(position);
	    	
        TextView view = (TextView)convertView.findViewById(R.id.textViewForecastLabel); 
		view.setText(dateFormat.format(forecast.getDateTime()));
        
    	ImageView imageView = (ImageView)convertView.findViewById(R.id.imageDay);
		AsyncTask<ImageView, Void, Bitmap> task = new RetrieveImageTask(getContext().getResources(), forecast.getImageName());
		task.execute(imageView);
    	
        view = (TextView)convertView.findViewById(R.id.textViewTemp); 
        view.setText(forecast.getTemp() + '\u00B0');

        view = (TextView)convertView.findViewById(R.id.textViewWindChill); 
        view.setText(forecast.getWindChill() + '\u00B0');

        view = (TextView)convertView.findViewById(R.id.textViewForecast); 
        view.setText(forecast.getLongPrediction());
	 
        return convertView;
    }
		
}
