package main.java.weather.weather_api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import main.java.util.HtmlGenerator;
import main.java.weather.city.City;
import main.java.weather.weather_api.Weather.Daily;

public class WeatherHtml {

    private static final String ROOT = WeatherAPI.getPathToResources() + "/html";
    private static final String HTML_TEMPLATE = ROOT + "/template.html";
    private HtmlGenerator generator;
    private String template;
    private City city;
    private Weather weather;
    private String token;

    public WeatherHtml(City city, Weather weather) {
	this.city = city;
	this.weather = weather;
	Properties properties = new Properties();
	generator = new HtmlGenerator(ROOT, HTML_TEMPLATE);
	try {
	    template = generator.readTemplate();
	    properties.load(new FileInputStream(WeatherAPI.getPathToProperties()));
	    token = properties.getProperty("accessToken");
	    template = template.replace("$accessToken", token);
	    template = template.replaceAll("latitude", "" + city.getLatitude());
	    template = template.replaceAll("longitude", "" + city.getLongitude());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public City getCity() {
	return city;
    }

    public void setCity(City city) {
	this.city = city;
    }

    public Weather getWeather() {
	return weather;
    }

    public void setWeather(Weather weather) {
	this.weather = weather;
    }

    public static String getHtmlTemplatePath() {
	return HTML_TEMPLATE;
    }

    public File generateHtml() throws IOException {
	insertCurrentForecast();
	insertDailyForecast();
	insertAbout();
	generator.setTemplate(template.replace("$town", city.getName() + ", " + city.getCountry()));
	return generator.generateHtml(city.getName() + " " + city.getCountry());
    }

    private void insertCurrentForecast() {
	String current = weather.createCurrentForecast();
	String day = current.substring(current.indexOf(":") + 1, current.indexOf("W"));
	template = template.replace("$currentDay", day);
	int index = current.indexOf("condition:");
	String group = current.substring(current.indexOf("Weather:") + 8, index + 10);
	template = template.replace("$currentWeatherGroup", group);
	int index2 = current.indexOf(", Temperature:");
	String condition = current.substring(index + 10, index2);
	template = template.replace("$currentWeatherCondition", condition);
	template = template.replace("$currentTemperature", "" + weather.getCurrent().getTemperature());
	String info = current.substring(current.indexOf("Sunrise"));
	template = template.replace("$currentInfo", info.replace("\n", "<br>"));
	template = template.replace("$currentIcon",
		WeatherAPI.generateIconUrl(weather.getCurrent().getInfo().getWeather().get(0).getIcon()));
    }

    private void insertDailyForecast() {
	for (Daily daily : weather.getDaily()) {
	    int index = weather.getDaily().indexOf(daily);
	    String forecast = weather.createDailyForecast(daily);
	    template = template.replace("$day" + index, forecast.substring(0, forecast.indexOf("Weather")));
	    template = template.replace("$icon" + index,
		    WeatherAPI.generateIconUrl(daily.getInfo().getWeather().get(0).getIcon()));
	    template = template.replace("$group" + index, daily.getInfo().getWeatherInfo());
	    template = template.replace("$temperature" + index,
		    daily.getTemperature().toString().substring(13).replaceAll("min", ", min"));
	    String info = forecast.substring(forecast.indexOf("Sunrise"));
	    template = template.replace("$info" + index, info.replace("\n", "<br>"));
	}
    }

    private void insertAbout() {
	template = template.replace("$unit", WeatherAPI.getInstance().getUnit().name());
	template = template.replace("$timezone", weather.getTimezone());
    }

}
