package main.java.weather.weather_api;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("current")
    private Current current;
    @JsonProperty("daily")
    private List<Daily> daily = new ArrayList<Weather.Daily>();

    public String getTimezone() {
	return timezone;
    }

    public Current getCurrent() {
	return current;
    }

    public List<Daily> getDaily() {
	return daily;
    }

    @Override
    public String toString() {
	return String.format("Timezone: %s\n\n%s\n\nDaily Forecast:\n\n%s", timezone, createCurrentForecast(),
		createDailyForecast());
    }

    protected String createDailyForecast(Daily forecast) {
	String dailyForecast = "";
	String date = convertTime(forecast.getInfo().getDatetime(), timezone);
	int datum = date.indexOf(" at ");
	String sunrise = convertTime(forecast.getInfo().getSunrise(), timezone);
	String sunset = convertTime(forecast.getInfo().getSunset(), timezone);
	dailyForecast += String.format("%s\n\t%s\n\tSunrise time: %s, Sunset time: %s\n\t%s\n\n",
		date.substring(0, datum), forecast.toString(), sunrise.substring(datum + 4, sunrise.indexOf("AM") + 2),
		sunset.substring(datum + 4, sunset.indexOf("PM") + 2), forecast.getInfo().toString());
	return dailyForecast;
    }

    private String createDailyForecast() {
	StringBuilder dailyForecast = new StringBuilder();
	daily.forEach(forecast -> dailyForecast.append(createDailyForecast(forecast)));
	return dailyForecast.toString();
    }

    public List<String> getDailyForecast() {
	List<String> list = new ArrayList<String>();
	daily.forEach(forecast -> list.add(createDailyForecast(forecast)));
	return list;
    }

    public String createCurrentForecast() {
	String dateTime = convertTime(current.getInfo().getDatetime(), timezone);
	int dateExtractor = dateTime.indexOf(" at ");
	String sunriseTime = convertTime(current.getInfo().getSunrise(), timezone);
	String sunsetTime = convertTime(current.getInfo().getSunset(), timezone);
	return String.format("Current time: %s\n\t%s\n\tSunrise time: %s, Sunset time: %s\n\t%s", dateTime,
		current.toString(), sunriseTime.substring(dateExtractor + 4, sunriseTime.indexOf("AM") + 2),
		sunsetTime.substring(dateExtractor + 4, sunsetTime.indexOf("PM") + 2), current.getInfo().toString());
    }

    private String convertTime(long time, String zone) {
	// unixtime, UTC
	LocalDateTime triggerTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.of(zone));
	ZonedDateTime zonedDateTime = ZonedDateTime.of(triggerTime, ZoneId.of(zone));
	return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).format(zonedDateTime);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Info {

	@JsonProperty("dt")
	private long datetime;
	@JsonProperty("sunrise")
	private long sunrise;
	@JsonProperty("sunset")
	private long sunset;
	@JsonProperty("uvi")
	private double uvIndex;
	@JsonProperty("pressure")
	private double pressure;
	@JsonProperty("humidity")
	private double humidity;
	@JsonProperty("wind_speed")
	private double windSpeed;
	@JsonProperty("clouds")
	private double clouds;
	@JsonProperty("rain")
	private String strRain;
	private double rain;
	@JsonProperty("snow")
	private String strSnow;
	private double snow;
	@JsonProperty("weather")
	private List<WeatherInfo> weather;

	public long getDatetime() {
	    return datetime;
	}

	public long getSunrise() {
	    return sunrise;
	}

	public long getSunset() {
	    return sunset;
	}

	public double getUvIndex() {
	    return uvIndex;
	}

	public double getPressure() {
	    return pressure;
	}

	public double getHumidity() {
	    return humidity;
	}

	public double getWindSpeed() {
	    return windSpeed;
	}

	public double getClouds() {
	    return clouds;
	}

	public double getRain() {
	    return rain;
	}

	public double getSnow() {
	    return snow;
	}

	public List<WeatherInfo> getWeather() {
	    return weather;
	}

	private void setInfo(String key, Object object) {
	    String value = object.toString();
	    switch (key) {
	    case "dt":
		datetime = Long.valueOf(value);
		break;
	    case "sunrise":
		sunrise = Long.valueOf(value);
		break;
	    case "sunset":
		sunset = Long.valueOf(value);
		break;
	    case "uvi":
		uvIndex = Double.valueOf(value);
		break;
	    case "pressure":
		pressure = Double.valueOf(value);
		break;
	    case "humidity":
		humidity = Double.valueOf(value);
		break;
	    case "wind_speed":
		windSpeed = Double.valueOf(value);
		break;
	    case "clouds":
		clouds = Double.valueOf(value);
		break;
	    case "rain":
		rain = getDoubleFromString(strRain = value);
		break;
	    case "snow":
		snow = getDoubleFromString(strSnow = value);
		break;
	    case "weather":
		try {
		    ObjectMapper mapper = new ObjectMapper();
		    weather = (mapper.readValue(mapper.writeValueAsString(object),
			    new TypeReference<List<WeatherInfo>>() {
			    }));
		} catch (JsonProcessingException e) {
		    e.printStackTrace();
		}
		break;
	    }
	}

	private double getDoubleFromString(String s) {
	    try {
		return Double.valueOf(s);
	    } catch (NumberFormatException e) {
		return 0;
	    }
	}

	public String getWeatherInfo() {
	    String info = "";
	    for (WeatherInfo wi : weather)
		info += wi + "\n";
	    return info.substring(0, info.length() - 1);
	}

	@Override
	public String toString() {
	    return String.format(
		    "UV index: %s, Atmospheric pressure on the sea level: %s hPa, Humidity: %s%s\n\tWind speed: %s m/s, Cloudiness: %s%s, Rain volume: %s mm, Snow volume: %s mm",
		    uvIndex, pressure, humidity, "%", windSpeed, clouds, "%", rain, snow);
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	static class WeatherInfo {

	    @JsonProperty("main")
	    private String main;
	    @JsonProperty("description")
	    private String description;
	    @JsonProperty("icon")
	    private String icon;

	    public String getMain() {
		return main;
	    }

	    public String getDescription() {
		return description;
	    }

	    public String getIcon() {
		return icon;
	    }

	    @Override
	    public String toString() {
		return String.format("Weather group: %s, Weather condition: %s", main, description);
	    }
	}
    }

    static class Current {

	private Map<String, Object> map = new LinkedHashMap<>();
	private Info info = new Info();
	@JsonProperty("temp")
	private double temperature;

	@JsonAnySetter
	private void add(String key, Object value) {
	    map.put(key, value);
	    info.setInfo(key, value);
	}

	public Info getInfo() {
	    return info;
	}

	public double getTemperature() {
	    return temperature;
	}

	@Override
	public String toString() {
	    return String.format("Weather:\n\t\t%s, Temperature: %.1f", info.getWeatherInfo(), temperature);
	}
    }

    static class Daily {

	private Map<String, Object> map = new LinkedHashMap<>();
	private Info info = new Info();
	@JsonProperty("temp")
	private Temperature temperature;

	@JsonAnySetter
	private void add(String key, Object value) {
	    map.put(key, value);
	    info.setInfo(key, value);
	}

	public Info getInfo() {
	    return info;
	}

	public Temperature getTemperature() {
	    return temperature;
	}

	@Override
	public String toString() {
	    return String.format("Weather:\n\t\t%s\n\t%s", info.getWeatherInfo(), temperature.toString());
	}

	static class Temperature {

	    @JsonProperty("min")
	    private double min;
	    @JsonProperty("max")
	    private double max;
	    @JsonProperty("morn")
	    private double morning;
	    @JsonProperty("day")
	    private double day;
	    @JsonProperty("eve")
	    private double evening;
	    @JsonProperty("night")
	    private double night;

	    public double getMin() {
		return min;
	    }

	    public double getMax() {
		return max;
	    }

	    public double getMorning() {
		return morning;
	    }

	    public double getDay() {
		return day;
	    }

	    public double getEvening() {
		return evening;
	    }

	    public double getNight() {
		return night;
	    }

	    @Override
	    public String toString() {
		return String.format(
			"Temperature:\n\t\tmorning: %.1f, day: %.1f, evening: %.1f, night: %.1f\n\t\tminimum: %.1f, maximum: %.1f",
			morning, day, evening, night, min, max);
	    }
	}
    }
}
