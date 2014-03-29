package com.weatherapp;

import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.weatherapp.model.DailyForecast;
import com.weatherapp.service.WeatherbugDailyForecastService;

public class DailyForecastRemoteViewService extends RemoteViewsService {
	 
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
    	Log.d("DailyForecastWidget ","returning view factory");
        return new DailyForecastRemoteViewFactory(this.getApplicationContext(), intent);
    }

}
class DailyForecastRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

	private Context context;
    private List<DailyForecast> forecasts;
    int appWidgetId;
    
	public DailyForecastRemoteViewFactory(Context applicationContext, Intent intent) {
		 this.context = applicationContext;
		 appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
	}

	@Override
	public int getCount() {
		Log.d("DailyForecastWidget", "Getting count");
		return forecasts != null ? forecasts.size() : 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public RemoteViews getLoadingView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoteViews getViewAt(int position) {

		Log.d("DailyForecastWidget", "Inflating row " + position);
		
		// inflate label view
		RemoteViews label = new RemoteViews(context.getPackageName(), R.layout.daily_forecast_label);
		label.setTextViewText(R.id.textViewForecastLabel, forecasts.get(position).getLongDay());

		
		// inflate the icon/temp layout
		
		// inflate the icon view
		
		// inflate the temperature view
		
		// inflate the forecast text view
		RemoteViews text = new RemoteViews(context.getPackageName(), R.layout.daily_forecast_text);
		text.setTextViewText(R.id.textViewForecast, forecasts.get(position).getLongPrediction());
		
		// inflate row view
		RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.daily_forecast_row);
		row.removeAllViews(R.id.dailyForecastRow);
		row.addView(R.id.dailyForecastRow, label);
		row.addView(R.id.dailyForecastRow, text);
		
		return row;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public void onCreate() {
		AsyncTask<String, Void, List<DailyForecast >> task = new DownloadForecastsTask();
		task.execute();
		
	}

	@Override
	public void onDataSetChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	class DownloadForecastsTask extends AsyncTask<String, Void, List<DailyForecast>> {

	    public DownloadForecastsTask() {}

	    protected List<DailyForecast> doInBackground(final String... args) {

	    	Log.d("DailyForecastWidget", "loading hourly forecasts");
			
		    LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
			Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			
			forecasts = WeatherbugDailyForecastService.getForecastByLocation(location);
	    	
			return forecasts;
	    }
	    
	    @Override
	    protected void onPostExecute(final List<DailyForecast> forecasts) {
	    	
	    	Log.d("DailyForecastWidget", "loaded " + forecasts.size() + " hourly forecasts");
	    	AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId, R.id.forecastList);
	    	
	    }
	    
	}
}

