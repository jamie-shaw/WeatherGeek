package com.weatherapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.weatherapp.service.ImageService;
import com.weatherapp.util.ImageCache;

public abstract class BaseImageService implements ImageService {
	
	protected Bitmap getWebImage(String imageName) {

		Bitmap bitmap = ImageCache.getImage(imageName);
		
		if (bitmap == null) {
			String url = getIconUrl(imageName);
			
			try {
				Log.d("BaseImageService", "fetching " + url);
				// pull the image down from the web
				bitmap = BitmapFactory.decodeStream((InputStream) new URL(url)
						.getContent());

				// save for later
				if (bitmap != null) {
					ImageCache.putImage(imageName, bitmap);
				}

			} catch (MalformedURLException e) {
				Log.e("DownloadImageTask", "URL: " + url);
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	protected Bitmap getLocalImage(Resources resources, String name) {

		Bitmap bitmap = ImageCache.getImage(name);

		if (bitmap == null) {
			Log.d("BaseImageService", "fetching " + name);
			// pull the image down from the web
			bitmap = BitmapFactory.decodeResource(resources, R.drawable.weathergeek);

			// save for later
			if (name != null) {
				ImageCache.putImage(name, bitmap);
			}
		}
		return bitmap;
	}

}
