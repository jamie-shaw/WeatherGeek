package com.weatherapp.service.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.weatherapp.service.valueobject.HourlyForecast;

public class UndergroundHourlyForecastSaxHandler extends DefaultHandler {

//	<response>
//	  <version>0.1</version>
//	  <termsofService>http://www.wunderground.com/weather/api/d/terms.html</termsofService>
//	  <features>
//	    <feature>hourly</feature>
//	  </features>
//	  <hourly_forecast>
//	    <forecast>
//	      <FCTTIME>
//	        <hour>10</hour>
//	        <hour_padded>10</hour_padded>
//	        <min>00</min>
//	        <sec>0</sec>
//	        <year>2012</year>
//	        <mon>5</mon>
//	        <mon_padded>05</mon_padded>
//	        <mon_abbrev>May</mon_abbrev>
//	        <mday>7</mday>
//	        <mday_padded>07</mday_padded>
//	        <yday>127</yday>
//	        <isdst>1</isdst>
//	        <epoch>1336410000</epoch>
//	        <pretty>10:00 AM PDT on May 07, 2012</pretty>
//	        <civil>10:00 AM</civil>
//	        <month_name>May</month_name>
//	        <month_name_abbrev>May</month_name_abbrev>
//	        <weekday_name>Monday</weekday_name>
//	        <weekday_name_night>Monday Night</weekday_name_night>
//	        <weekday_name_abbrev>Mon</weekday_name_abbrev>
//	        <weekday_name_unlang>Monday</weekday_name_unlang>
//	        <weekday_name_night_unlang>Monday Night</weekday_name_night_unlang>
//	        <ampm>AM</ampm>
//	        <tz />
//	        <age />
//	      </FCTTIME>
//	      <temp>
//	        <english>66</english>
//	        <metric>19</metric>
//	      </temp>
//	      <dewpoint>
//	        <english>47</english>
//	        <metric>9</metric>
//	      </dewpoint>
//	      <condition>Clear</condition>
//	      <icon>clear</icon>
//	      <icon_url>http://icons-ak.wxug.com/i/c/k/clear.gif</icon_url>
//	      <fctcode>1</fctcode>
//	      <sky>0</sky>
//	      <wspd>
//	        <english>3</english>
//	        <metric>5</metric>
//	      </wspd>
//	      <wdir>
//	        <dir>North</dir>
//	        <degrees>0</degrees>
//	      </wdir>
//	      <wx />
//	      <uvi>1</uvi>
//	      <humidity>54</humidity>
//	      <windchill>
//	        <english>-9998</english>
//	        <metric>-9998</metric>
//	      </windchill>
//	      <heatindex>
//	        <english>-9998</english>
//	        <metric>-9998</metric>
//	      </heatindex>
//	      <feelslike>
//	        <english>66</english>
//	        <metric>19</metric>
//	      </feelslike>
//	      <qpf>
//	        <english />
//	        <metric />
//	      </qpf>
//	      <snow>
//	        <english />
//	        <metric />
//	      </snow>
//	      <pop>0</pop>
//	      <mslp>
//	        <english>30.03</english>
//	        <metric>1016</metric>
//	      </mslp>
//	    </forecast>
//	  </hourly_forecast>
//	</response>
	    
	private static final String FORECAST = "forecast";
	private static final String ENGLISH = "english";
	private static final String CIVIL = "civil";
	private static final String ICON = "icon";
	private static final String TEMP = "temp";
	//private static final String DEWPOINT = "dewpoint";
	//private static final String WINDSPEED = "wspd";
	//private static final String WINDCHILL = "windchill";
	//private static final String HEAT_INDEX = "heatindex";
	private static final String FEELS_LIKE = "feelslike";
	//private static final String SNOW = "snow";
	private static final String LONG_PREDICTION = "wx";
	
	private static final String BASE_ICON_URL = "http://icons.wxug.com/i/c/";
	private static final String DEFAULT_ICON_SET = "a/";
	
	private static final int INTERVAL = 3;

	private static int forecastNumber = INTERVAL;
	
	private List<HourlyForecast> forecasts;
	private UndergroundHourlyForecast currentForecast;
	private StringBuilder builder;
	private Stack<String> elements = new Stack<String>();
	
	public List<HourlyForecast> getForecasts(){
		return this.forecasts;
	}
	 
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		builder.append(ch, start, length);
	}


    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        forecasts = new ArrayList<HourlyForecast>();
        builder = new StringBuilder();
        forecastNumber = INTERVAL;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
    	super.startElement(uri, localName, name, attributes);
    			
        if (localName.equals(FORECAST)) {
        	if (forecastNumber % INTERVAL == 0) {
        		currentForecast = new UndergroundHourlyForecast();
        		forecasts.add(currentForecast);
        	}
        } 
        elements.push(localName);
        builder.setLength(0);  
    }

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		super.endElement(uri, localName, name);

		elements.pop();
		
    	if (localName.equals(FORECAST)) {
            forecastNumber++;
            
    	} else if (forecastNumber % INTERVAL == 0) {
			if (localName.equals(ICON)) {
				currentForecast.setImageName(builder.toString() + ".gif");
				currentForecast.setImageURL(BASE_ICON_URL + DEFAULT_ICON_SET + builder.toString() + ".gif");
			} else if (localName.equals(LONG_PREDICTION)) {
				currentForecast.setLongPrediction(builder.toString().trim());			
			} else if (localName.equals(ENGLISH)) {
				String parent = elements.peek();
				if (parent == TEMP) {
					currentForecast.setTemp(builder.toString());
				} else if (parent == FEELS_LIKE) {
					currentForecast.setWindChill(builder.toString());
				}
			}
    	}
	}
	
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        
        builder = null;
        elements = null;
    }
    

    private class UndergroundHourlyForecast implements HourlyForecast {

    	private String windChill;
    	private String temp;
    	private String imageName;
    	private String imageURL;
    	private String longPrediction;
    	
    	public String getWindChill() {
    		return windChill;
    	}
    	public void setWindChill(String windChill) {
    		this.windChill = windChill;
    	}
    	public String getTemp() {
    		return temp;
    	}
    	public void setTemp(String temp) {
    		this.temp = temp;
    	}
    	public String getImageURL() {
    		return imageURL;
    	}
    	public void setImageURL(String imageURL) {
    		this.imageURL = imageURL;
    	}
    	public Date getDateTime() {
    		return new Date();
    	}
    	public String getLongPrediction() {
    		return longPrediction;
    	}
    	public void setLongPrediction(String longPrediction) {
    		this.longPrediction = longPrediction;
    	}
    	public String getImageName() {
    		return imageName;
    	}
    	public void setImageName(String imageName) {
    		this.imageName = imageName;
    	}
    	
    }

}
