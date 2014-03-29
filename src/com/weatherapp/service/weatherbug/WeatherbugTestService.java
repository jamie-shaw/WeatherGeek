package com.weatherapp.service.weatherbug;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class WeatherbugTestService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
		    System.out.println("Sending request");
			
		    // Send data
		    URL url = new URL("http://direct.weatherbug.com/DataService/GetForecast.ashx?nf=7&ih=1&zip=04103");
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.flush();
		     
		    // Get the response		    System.out.println("Processing response");
		    
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String line;
		    while ((line = rd.readLine()) != null) {
		        System.out.println(line);
		    }
		    wr.close();
		    rd.close();
		    
		    System.out.println("Response processed");

		} catch (Exception e) {
			System.out.println("Something bad happened");
			e.printStackTrace();
		}

	}

}
