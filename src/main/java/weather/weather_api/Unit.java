package main.java.weather.weather_api;

import java.util.HashMap;
import java.util.Map;

public enum Unit {

    KELVIN(1, "default"), CELSIUS(2, "&units=metric"), FAHRENHEIT(3, "&units=imperial");

    private final String unit;
    private final int ordinal;
    private static final Map<Integer, Unit> indexMap = new HashMap<>(Unit.values().length);

    static {
	for (Unit unit : Unit.values())
	    indexMap.put(unit.ordinal(), unit);
    }

    private Unit(int ordinal, String unit) {
	this.ordinal = ordinal;
	this.unit = unit;
    }

    public String getUnitFormat() {
	return unit.equals(KELVIN.unit) ? "" : unit;
    }

    @Override
    public String toString() {
	return this.ordinal + " " + this.name();
    }

    public static Unit lookUpByIndex(int ordinal) {
	return indexMap.get(ordinal - 1) == null ? KELVIN : indexMap.get(ordinal - 1);
    }

    public static void displaySelection() {
	String selection = indexMap.values().toString();
	System.out.println(selection.substring(1, selection.length() - 1));
    }
}
