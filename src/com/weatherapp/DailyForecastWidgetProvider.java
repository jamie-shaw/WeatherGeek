package com.weatherapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

public class DailyForecastWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		Log.d("DailyForecastWidgetProvider","entering onUpdate");
		 
		// loop over all instances of the widget
		for (int appWidgetId : appWidgetIds) {
			
			// Set up the intent that starts the DailyForecastRemoteViewService, which will
	        // provide the views for this collection.
	        Intent intent = new Intent(context, DailyForecastRemoteViewService.class);
	        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
	        
	        // Instantiate the RemoteViews object for the app widget layout.
	        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.daily_forecast_widget);
	        
	        // Set up the RemoteViews object to use a RemoteViews adapter, which connects to
	        // a RemoteViewsService  through the specified intent to populate the data.
	        rv.setRemoteAdapter(appWidgetId, R.id.forecastList, intent);
	        
	        // The empty view is displayed when the collection has no items
	        rv.setEmptyView(R.id.forecastList, R.id.empty_view);

	        // Render the updated widget
	        appWidgetManager.updateAppWidget(appWidgetId, rv);   
		}
				
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	      
		Log.d("DailyForecastWidgetProvider","exiting onUpdate");
	}

}
