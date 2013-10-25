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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.weatherapp.util.Preferences;

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
		case (R.id.menu_item_mylocation):
			AlertDialog.Builder builder;
			final AlertDialog dialog;

			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View layout = inflater.inflate(R.layout.zip_code_dialog, (ViewGroup) findViewById(R.id.dialog_root));

			builder = new AlertDialog.Builder(this);
			builder.setView(layout);

			dialog = builder.create();
			dialog.setCancelable(true);
			dialog.setTitle("Zip Code");
			dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			
			final Context context = getApplicationContext();
			String zipCode = Preferences.getZipCode(context);
			EditText editTextZipCode = (EditText) layout.findViewById(R.id.editTextZipcode);
			editTextZipCode.setText(zipCode);

			dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					EditText editTextZipCode = (EditText) layout.findViewById(R.id.editTextZipcode);
					Preferences.setZipCode(context, editTextZipCode.getText().toString());
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
		// TODO Auto-generated method stub
		super.onDestroy();
		//System.out.println("paused " + this.getClass().getName());
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//System.out.println("resumed " + this.getClass().getName());
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//System.out.println("destroyed " + this.getClass().getName());
	}
}

