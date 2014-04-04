package com.weatherapp.task;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.weatherapp.service.ServiceFactory;

public class RetrieveImageTask extends AsyncTask<ImageView, Void, Bitmap> {

	private ImageView imageView = null;
	private String iconName;
	private Resources resources;
	
	public RetrieveImageTask(Resources resources, String iconName) {
		this.iconName = iconName;
		this.resources = resources;
	}
	
	@Override
	protected Bitmap doInBackground(ImageView... imageViews) {
	    this.imageView = imageViews[0];
		
	    return ServiceFactory.getImageService().getImage(resources, iconName);		
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
	    imageView.setImageBitmap(result);
	}

}