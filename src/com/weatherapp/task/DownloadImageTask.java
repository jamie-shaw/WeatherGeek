package com.weatherapp.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.weatherapp.util.ImageCache;

public class DownloadImageTask extends AsyncTask<ImageView, Void, Bitmap> {

	private ImageView imageView = null;
	private String name;
	private String url;
	
	public DownloadImageTask(String name, String url) {
		this.name = name;
		this.url = url;
	}
	
	@Override
	protected Bitmap doInBackground(ImageView... imageViews) {
	    this.imageView = imageViews[0];
		
	    Bitmap bitmap = ImageCache.getImage(name);
		
		if (bitmap == null) {
			try {
				System.out.println("fetching " + url);
				//Pull the image down from the web
				bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
				
				//Save for later
				if (name != null) {
					ImageCache.putImage(name, bitmap);
				}
			
			} catch (MalformedURLException e) {
				System.out.println("URL: " + url);
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return bitmap;
		
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
	    imageView.setImageBitmap(result);
	}

}