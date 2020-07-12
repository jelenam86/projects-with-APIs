package main.java.weather.city;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CityUtil {

    private String source;

    public CityUtil(String source) {
	this.source = source;
    }

    public String getSource() {
	return source;
    }

    public void setSource(String source) {
	this.source = source;
    }

    private List<City> getListFromSource() {
	ObjectMapper mapper = new ObjectMapper();
	Path path = Paths.get(this.source);
	List<City> cityList = null;
	try {
	    cityList = (List<City>) mapper.readValue(Files.newBufferedReader(path, StandardCharsets.UTF_8),
		    new TypeReference<List<City>>() {
		    });
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return cityList;
    }

    private List<City> removeDuplicates(List<City> list) {
	DecimalFormat df = new DecimalFormat("0.00");
	for (City city : list)
	    city.setCoordinates(Double.valueOf(df.format(city.getCoordinates().getLatitude())),
		    Double.valueOf(df.format(city.getCoordinates().getLongitude())));
	return list.stream().distinct().collect(Collectors.toList());
    }

    private List<City> getCitiesByName(String name) {
	List<City> cities = getListFromSource().stream().filter(city -> city.getName().equalsIgnoreCase(name))
		.collect(Collectors.toList());
	List<City> list = removeDuplicates(cities);
	Comparator<City> compareByState = Comparator.comparing(City::getState).thenComparing(City::getCountry);
	Collections.sort(list, compareByState);
	return list;
    }

    private City chooseCity(Scanner scanner, List<City> cities) {
	System.out.println("There are the following cities with the same name:");
	cities.forEach(System.out::println);
	City city = null;
	do {
	    System.out.println("Please enter valid ID of the city whose data you want to see:");
	    if (scanner.hasNextInt()) {
		int id = scanner.nextInt();
		city = cities.stream().filter(c -> id == c.getId()).findAny().orElse(null);
	    } else
		scanner.nextLine();
	} while (city == null);
	return city;
    }

    public City getCityByName(Scanner scanner) {
	System.out.println("Enter the name of the city for which you want to pull out information about the weather:");
	String name = scanner.nextLine();
	List<City> cities = getCitiesByName(name);
	while (cities.size() < 1) {
	    System.out.println("No data for the " + name + ". Try to enter name of some other city: ");
	    name = scanner.nextLine();
	    cities = getCitiesByName(name);
	}
	return cities.size() == 1 ? cities.get(0) : chooseCity(scanner, cities);
    }
}
