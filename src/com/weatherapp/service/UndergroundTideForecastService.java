package com.weatherapp.service;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.weatherapp.service.parser.UndergroundHourlyForecastSaxHandler;
import com.weatherapp.service.valueobject.HourlyForecast;

public class UndergroundTideForecastService {

	public static List<HourlyForecast> getForecastsFromXML(String zipCode) {
		
		List<HourlyForecast> forecasts = null;
		
		try {
	        //Get an XMLReader
	        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
	        parserFactory.setNamespaceAware(true);
	        SAXParser parser = parserFactory.newSAXParser();
	        XMLReader xmlReader = parser.getXMLReader();
	        
	        //Create a new ContentHandler and apply it to the XML-Reader
	        UndergroundHourlyForecastSaxHandler forecastHandler = new UndergroundHourlyForecastSaxHandler();
	        xmlReader.setContentHandler(forecastHandler);
		    	
		    // Send request
		    URL url = new URL("http://api.wunderground.com/api/2b2713188f60c01d/tide/q/ME/Portland.xml");
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.flush();
		     
		    // Parse the response
		    InputSource inputSource = new InputSource();
	        inputSource.setEncoding("UTF-8");
	        inputSource.setCharacterStream(new InputStreamReader(conn.getInputStream()));
	        xmlReader.parse(inputSource);
	        
	        forecasts = forecastHandler.getForecasts();
	        
		    wr.close();
		    
		} catch (Exception e) {
			System.out.println("Something bad happened");
			e.printStackTrace();
		}
		
		return forecasts;

	}
	
}
