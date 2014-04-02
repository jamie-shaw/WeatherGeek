package com.weatherapp.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.weatherapp.service.ServiceFactory;

public class DownloadImageTask extends AsyncTask<ImageView, Void, Bitmap> {

	private ImageView imageView = null;
	private String iconName;
	
	public DownloadImageTask(String iconName) {
		this.iconName = iconName;
	}
	
	@Override
	protected Bitmap doInBackground(ImageView... imageViews) {
	    this.imageView = imageViews[0];
		
	    return ServiceFactory.getImageService().getImage(iconName);		
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
	    imageView.setImageBitmap(result);
	}

}