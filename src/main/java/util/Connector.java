package main.java.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Connector {
    private static Connector instance;

    private Connector() {
    }

    public static Connector getInstance() {
	if (instance == null)
	    instance = new Connector();
	return instance;
    }

    private HttpURLConnection setConnection(URL url) throws IOException {
	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	connection.setRequestProperty("User-Agent", "java:null:learning:jelenam86");
	return connection;
    }

    public boolean isFound(String stringURL) throws IOException {
	URL url = new URL(stringURL);
	return setConnection(url).getResponseMessage().equals("Not Found") ? false : true;
    }

    private String openConnection(URL url) {
	InputStream inputStream;
	try {
	    inputStream = setConnection(url).getInputStream();
	    return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
	} catch (IOException e) {
	    return "There is no url named: " + url.getPath() + ".";
	}
    }

    public JSONObject openResponse(String url) {
	try {
	    return new JSONObject(openConnection(new URL(url)));
	} catch (JSONException | MalformedURLException e) {
	    System.out.println("Wrong data entered");
	    System.exit(0);
	    return null;
	}
    }
}
