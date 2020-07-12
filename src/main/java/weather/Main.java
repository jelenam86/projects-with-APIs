package main.java.weather;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import main.java.util.GZipDecompress;
import main.java.util.TextHelper;
import main.java.weather.city.City;
import main.java.weather.city.CityUtil;
import main.java.weather.weather_api.Unit;
import main.java.weather.weather_api.Weather;
import main.java.weather.weather_api.WeatherAPI;
import main.java.weather.weather_api.WeatherHtml;

public class Main {

    private static final String RESOURCE_FILE = WeatherAPI.getPathToResources() + "/city/city.list.json.gz";
    private static final String TEXT_FILE = WeatherAPI.getPathToResources() + "/weather.txt";
    private static final String JSON_FILE = "city.json";

    public static void main(String[] args) {

	Scanner scanner = new Scanner(System.in);

	GZipDecompress gzip = new GZipDecompress(JSON_FILE, RESOURCE_FILE);
	gzip.decompress();

	System.out.println(
		"It is possible to get temperature in Kelvin, Celsius or Fahrenheit.\nDefault value is Kelvin. To change it, please enter 2 or 3. Anything else will mark Kelvin as unit.");
	Unit.displaySelection();
	if (scanner.hasNextInt()) {
	    int unit = scanner.nextInt();
	    WeatherAPI.getInstance().setUnit(unit);
	}
	scanner.nextLine();

	City city = new CityUtil(gzip.getPathToOutput()).getCityByName(scanner);
	Weather weather = WeatherAPI.getInstance().getWeather(city.getLatitude(), city.getLongitude());

	System.out.println("\n" + city + "\n" + weather);
	try {
	    TextHelper.writeToFile(city.toString() + "\nUnit for temperature: "
		    + WeatherAPI.getInstance().getUnit().name() + "\n" + weather.toString(), TEXT_FILE);
	    File htmlFile = new WeatherHtml(city, weather).generateHtml();
	    System.out.println("Do you want to open a HTML version of your forecast?");
	    if (scanner.next().equalsIgnoreCase("yes") && Desktop.isDesktopSupported()
		    && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
		Desktop.getDesktop().open(htmlFile);
	} catch (IOException e) {
	    e.printStackTrace();
	    System.out.println("An error occured.");
	}

	scanner.close();
    }

}