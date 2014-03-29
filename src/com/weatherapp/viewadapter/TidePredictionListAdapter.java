package com.weatherapp.viewadapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weatherapp.R;
import com.weatherapp.model.TidePrediction;

public class TidePredictionListAdapter extends ArrayAdapter<TidePrediction> {

    private List<TidePrediction> tidePredictions;
    private LayoutInflater inflater;
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("EEEE, h:mm a");
    
    public TidePredictionListAdapter(Context context, int textViewResourceId, List<TidePrediction> tidePredictions) {
            super(context, textViewResourceId, tidePredictions);
            inflater = LayoutInflater.from(context);
            this.tidePredictions = tidePredictions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Inflate and populate the tide prediction row
    	View row = inflater.inflate(R.layout.tide_prediction_detail_row, null);
               
        TidePrediction prediction = tidePredictions.get(position);

		TextView view = (TextView) row.findViewById(R.id.textViewTidePredictionTime);
		view.setText(dateTimeFormat.format(prediction.getDateTime()));

		view = (TextView) row.findViewById(R.id.textViewTidePredictionType);
		view.setText(prediction.getTideType().toString());
        
		view = (TextView) row.findViewById(R.id.textViewTidePredictionHeight);
		view.setText(Float.toString(prediction.getHeight()) + " feet");
		
        return row;
    }

}
