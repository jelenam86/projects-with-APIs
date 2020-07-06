package main.java.basic_twitter_bot.reddit;

import java.io.IOException;
import java.net.MalformedURLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class TILUtil {

    private static final String ROOT = "todayilearned";
    private String description;
    private String accCreated;
    private String title;
    private int numberOfSubscribers;
    private static TILUtil instance;
    private SubredditConnector connector;

    private TILUtil() throws IOException {
	connector = new SubredditConnector(ROOT);
	title = connector.getTitle();
	description = connector.getPublicDescription();
	accCreated = connector.getCakeDate();
	numberOfSubscribers = connector.getNumberOfSubcribers();
    }

    public static TILUtil getInstance() throws IOException {
	if (instance == null)
	    instance = new TILUtil();
	return instance;
    }

    @Override
    public String toString() {
	return String.format("%s\nCreated: %s\nNumber of subscribers: %d\nDescription: %s", title, accCreated,
		numberOfSubscribers, description);
    }

    protected Post getNewPost() throws JsonMappingException, MalformedURLException, JsonProcessingException {
	return connector.getPost("new");
    }

    protected Post getTopPost() throws JsonMappingException, MalformedURLException, JsonProcessingException {
	return connector.getPost("top");
    }

    protected Post getHotPost() throws JsonMappingException, MalformedURLException, JsonProcessingException {
	return connector.getPost("hot");
    }
}
