package main.java.weather.city;

import com.fasterxml.jackson.annotation.JsonProperty;

public class City {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("country")
    private String country;
    @JsonProperty("state")
    private String state;
    @JsonProperty("coord")
    private Coordinates coordinates;

    public City() {
    }

    public City(int id, String name, String country, String state, Coordinates coordinate) {
	this.id = id;
	this.name = name;
	this.country = country;
	this.state = state;
	this.coordinates = coordinate;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public Coordinates getCoordinates() {
	return coordinates;
    }

    public void setCoordinates(Coordinates coordinate) {
	this.coordinates = coordinate;
    }

    public void setCoordinates(double latitude, double longitude) {
	this.coordinates.setLatitude(latitude);
	this.coordinates.setLongitude(longitude);
    }

    public double getLatitude() {
	return getCoordinates().getLatitude();
    }

    public double getLongitude() {
	return getCoordinates().getLongitude();
    }

    @Override
    public String toString() {
	String nameOfState = getState().length() == 0 ? "  " : getState();
	return String.format("ID: %d, name: %s, state: %s, country: %s, %s", getId(), getName(), nameOfState,
		getCountry(), getCoordinates());
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == this)
	    return true;
	if (!(obj instanceof City))
	    return false;
	City city = (City) obj;
	return this.coordinates.equals(city.getCoordinates());
    }

    @Override
    public int hashCode() {
	int hash = 19;
	hash = 31 * hash + (name == null ? 0 : name.hashCode());
	hash = 31 * hash + (state == null ? 0 : state.hashCode());
	hash = 31 * hash + (country == null ? 0 : country.hashCode());
	hash = 31 * hash + (coordinates == null ? 0 : coordinates.hashCode());
	return hash;
    }

    static class Coordinates {
	@JsonProperty("lon")
	private double longitude;
	@JsonProperty("lat")
	private double latitude;

	Coordinates() {
	}

	Coordinates(double longitude, double latitude) {
	    this.longitude = longitude;
	    this.latitude = latitude;
	}

	double getLongitude() {
	    return longitude;
	}

	void setLongitude(double longitude) {
	    this.longitude = longitude;
	}

	double getLatitude() {
	    return latitude;
	}

	void setLatitude(double latitude) {
	    this.latitude = latitude;
	}

	@Override
	public String toString() {
	    return "Latitude: " + getLatitude() + ", Longitude: " + getLongitude();
	}

	@Override
	public boolean equals(Object obj) {
	    if (obj == this)
		return true;
	    if (!(obj instanceof Coordinates))
		return false;
	    Coordinates coordinate = (Coordinates) obj;
	    return Double.compare(latitude, coordinate.getLatitude()) == 0
		    & Double.compare(longitude, coordinate.getLongitude()) == 0;
	}

	@Override
	public int hashCode() {
	    int hash = 7;
	    hash = 53 * hash + Double.valueOf(latitude).hashCode();
	    hash = 53 * hash + Double.valueOf(longitude).hashCode();
	    return hash;
	}
    }

}
