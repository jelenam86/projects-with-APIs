package main.java.basic_twitter_bot.reddit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.util.Connector;

public class SubredditConnector {

    private static final String ROOT_URL = "https://www.reddit.com/r/";
    private static final String INFO = "/about";
    private static final String SUFIX = ".json";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
	    .withLocale(Locale.US).withZone(ZoneId.systemDefault());
    private boolean enabled;
    private String subredditName;

    public SubredditConnector(String subredditName) throws IOException {
	this.subredditName = subredditName;
	this.enabled = Connector.getInstance().isFound(ROOT_URL + subredditName);
    }

    public boolean isEnabled() {
	return enabled;
    }

    private JSONObject getInfoData() throws IOException {
	JSONObject allData = Connector.getInstance().openResponse(ROOT_URL + subredditName + INFO + SUFIX);
	return allData.getJSONObject("data");
    }

    protected String getTitle() throws JSONException, IOException {
	return isEnabled() ? getInfoData().getString("title") : null;
    }

    protected int getNumberOfSubcribers() throws JSONException, IOException {
	return isEnabled() ? getInfoData().getInt("subscribers") : -1;
    }

    protected String getPublicDescription() throws IOException {
	return isEnabled() ? getInfoData().getString("public_description") : null;
    }

    protected String getCakeDate() throws IOException {
	String cakeDate = "US Local time: ";
	return isEnabled() ? cakeDate + formatter.format(Instant.ofEpochSecond(getInfoData().getLong("created")))
		: null;
    }

    protected Post getPost(String hotNewTop)
	    throws MalformedURLException, JsonMappingException, JsonProcessingException {
	Post lastPost;
	JSONObject allData = Connector.getInstance().openResponse(ROOT_URL + subredditName + "/" + hotNewTop + SUFIX);
	JSONObject data = allData.getJSONObject("data");
	JSONArray children = data.getJSONArray("children");
	JSONObject index = children.getJSONObject(0);
	JSONObject dataAboutPost = index.getJSONObject("data");
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	lastPost = isEnabled() ? mapper.readValue(dataAboutPost.toString(), Post.class) : null;
	return lastPost;
    }

}
