package main.java.weather.weather_api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.util.Connector;

public class WeatherAPI {

    private static final String PATH_TO_RESOURCES = "src/main/java/weather/resources";
    private static final String PATH_TO_PROPERTY_FILE = PATH_TO_RESOURCES + "/weather.properties";
    // Free plan API
    private static final String ROOT = "http://api.openweathermap.org/";
    private static final String ICON_URL_PART_ONE = "http://openweathermap.org/img/wn/";
    private static final String ICON_URL_PART_TWO = "@4x.png";
    private static final String ONE_CALL = "data/2.5/onecall?";

    private static WeatherAPI instance;
    private Properties properties;
    private String apiKey;
    private String APPID;
    private Unit unit;

    private WeatherAPI() {
	this.unit = Unit.KELVIN;
	properties = new Properties();
	try {
	    properties.load(new FileInputStream(PATH_TO_PROPERTY_FILE));
	    apiKey = properties.getProperty("apiKey");
	    APPID = "&appid=" + apiKey;
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static WeatherAPI getInstance() {
	if (instance == null)
	    instance = new WeatherAPI();
	return instance;
    }

    public static String getPathToResources() {
	return PATH_TO_RESOURCES;
    }

    public static String getPathToProperties() {
	return PATH_TO_PROPERTY_FILE;
    }

    public Unit getUnit() {
	return unit;
    }

    public void setUnit(int number) {
	this.unit = Unit.lookUpByIndex(number);
    }

    public void setUnit(Unit unit) {
	this.unit = unit;
    }

    public Weather getWeather(double latitude, double longitude) {
	JSONObject response = Connector.getInstance()
		.openResponse(ROOT + ONE_CALL + "lat=" + latitude + "&lon=" + longitude + unit.getUnitFormat() + APPID);
	ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	try {
	    return mapper.readValue(response.toString(), Weather.class);
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public static String generateIconUrl(String iconName) {
	return ICON_URL_PART_ONE + iconName + ICON_URL_PART_TWO;
    }
}
