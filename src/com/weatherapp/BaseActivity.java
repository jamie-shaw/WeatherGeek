package com.weatherapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.weatherapp.service.ServiceFactory;
import com.weatherapp.service.ServiceFactory.ServiceProvider;

public abstract class BaseActivity extends Activity {
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		Intent prefsIntent = new Intent(this.getApplicationContext(), PreferencesActivity.class);

		MenuItem preferences = menu.findItem(R.id.menu_item_refresh);
		preferences.setIntent(prefsIntent);

		return true;
	}

	protected abstract void buildContent();
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		
		switch (item.getItemId()) {
		case (R.id.menu_item_weather_provider):
			AlertDialog.Builder builder;
			final AlertDialog dialog;

			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View layout = inflater.inflate(R.layout.weather_provider_dialog, (RadioGroup) findViewById(R.id.providerRadioGroup));

			builder = new AlertDialog.Builder(this);
			builder.setView(layout);

			dialog = builder.create();
			dialog.setCancelable(true);
			dialog.setTitle("Select Weather Provider");
			dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

			// restore selected value
			ServiceProvider currentWeatherProvider = ServiceFactory.getWeatherServiceProvider();
			final int checkedButton;
			
			switch(currentWeatherProvider) {
				case WEATHERBUG:
					checkedButton = R.id.weatherbugButton;
					break;
				case UNDERGROUND:
				default:
					checkedButton = R.id.undergroundButton;
			}
			
			((RadioButton)layout.findViewById(checkedButton)).setChecked(true);
			
			dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					
					RadioGroup g = (RadioGroup) layout; 
					int selected = g.getCheckedRadioButtonId();

					if (selected != checkedButton) {
						switch(selected) {
							case R.id.weatherbugButton:
								ServiceFactory.setWeatherServiceProvider(ServiceProvider.WEATHERBUG);
								break;
							case R.id.undergroundButton:
							default:
								ServiceFactory.setWeatherServiceProvider(ServiceProvider.UNDERGROUND);
						}
						buildContent();
					}
					dialog.dismiss();

				}
				
			});

			dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});

			dialog.show();

			break;
			
		case (R.id.menu_item_refresh):
			System.out.println("Refreshing");
			buildContent();
			break;
			
		default:
			break;
		}

		return true;
	}
	
	protected void configureToolbar() {
		
		if (this instanceof DailyForecastActivity) {
			activateButton(R.id.btnHourlyForecast, HourlyForecastActivity.class);
			activateButton(R.id.btnCurrentConditions, CurrentConditionsActivity.class);
			activateButton(R.id.btnTides, TidePredictionActivity.class);
		}

		if (this instanceof HourlyForecastActivity) {
			activateButton(R.id.btnDailyForecast, DailyForecastActivity.class);
			activateButton(R.id.btnCurrentConditions, CurrentConditionsActivity.class);
			activateButton(R.id.btnTides, TidePredictionActivity.class);
		}
		
		if (this instanceof CurrentConditionsActivity) {
			activateButton(R.id.btnDailyForecast, DailyForecastActivity.class);
			activateButton(R.id.btnHourlyForecast, HourlyForecastActivity.class);
			activateButton(R.id.btnTides, TidePredictionActivity.class);
		}	
		
		if (this instanceof TidePredictionActivity) {
			activateButton(R.id.btnDailyForecast, DailyForecastActivity.class);
			activateButton(R.id.btnHourlyForecast, HourlyForecastActivity.class);
			activateButton(R.id.btnCurrentConditions, CurrentConditionsActivity.class);
		}	

	}
	
	private void activateButton(final int buttonId, final Class<? extends BaseActivity> activity) {
		
		Button button = (Button) findViewById(buttonId);
		
		button.setBackgroundDrawable(getResources().getDrawable(R.drawable.active_buttons));
		
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), activity);
				startActivity(myIntent);
			}

		});
	}
	
	
	@Override
	protected void onPause() {
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		super.onDestroy();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}

