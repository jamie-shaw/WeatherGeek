package com.weatherapp.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class WeatherbugLocationService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
		    System.out.println("Sending request");
			
		    // Construct data
		    String data = URLEncoder.encode("ACode", "UTF-8") + "=" + URLEncoder.encode("A6466575693", "UTF-8");
		    data += "&" + URLEncoder.encode("SearchString", "UTF-8") + "=" + URLEncoder.encode("Portland", "UTF-8");
		    
		    // Send data
		    URL url = new URL("http://A1111111111.api.wxbug.net/getLocationsXML.aspx?" + data);
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.flush();
		     
		    // Get the response
		    System.out.println("Processing response");
		    
		     BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String line;
		    while ((line = rd.readLine()) != null) {
		        System.out.println(line);
		    }
		    wr.close();
		    rd.close();
		    
		} catch (Exception e) {
			System.out.println("Something bad happened");
			e.printStackTrace();
		}

	}

}
