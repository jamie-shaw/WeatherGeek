package com.weatherapp.viewadapter;

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
import com.weatherapp.model.DailyForecast;
import com.weatherapp.task.RetrieveImageTask;

public class DailyForecastListAdapter extends ArrayAdapter<DailyForecast> {

    private List<DailyForecast> forecasts;
    private LayoutInflater inflater;
    
    public DailyForecastListAdapter(Context context, int textViewResourceId, List<DailyForecast> forecasts) {
            super(context, textViewResourceId, forecasts);
            inflater = LayoutInflater.from(context);
            this.forecasts = forecasts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    	if (convertView == null) {
    		//Inflate and populate the forecast row
    		convertView = inflater.inflate(R.layout.daily_forecast_row, null);
    	}
    	
        int dayNumber = position + 1;

        DailyForecast forecast = forecasts.get(position);

        TextView view = (TextView)convertView.findViewById(R.id.textViewForecastLabel); 
        switch (dayNumber) {
		case -1:
			view.setText(forecast.isNight() ? "Tonight" : "Today");
			break;
		case -2:
			view.setText("Tomorrow");
			break;
		default:
			view.setText(forecast.getLongDay());
		}
        
    	ImageView imageView = (ImageView)convertView.findViewById(R.id.imageDay);
		AsyncTask<ImageView, Void, Bitmap> task = new RetrieveImageTask(getContext().getResources(), forecast.getImageName());
		task.execute(imageView);

        view = (TextView)convertView.findViewById(R.id.textViewHiTempDay); 
        view.setText(" " + forecast.getTemperature() + '\u00B0');
   
        view = (TextView)convertView.findViewById(R.id.textViewForecast); 
        view.setText(forecast.getLongPrediction() + "\n");
        
        return convertView;
    }

}
