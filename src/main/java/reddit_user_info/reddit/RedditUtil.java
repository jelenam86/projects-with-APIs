package main.java.reddit_user_info.reddit;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import main.java.util.Connector;

public class RedditUtil {

    private static final String USER_ROOT = "https://www.reddit.com/user/";
    private static final String ABOUT = "/about";
    private static final String SUFIX = ".json";
    private static RedditUtil instance = null;

    private RedditUtil() {
    }

    public static RedditUtil getInstance() {
	if (instance == null)
	    instance = new RedditUtil();
	return instance;
    }

    protected boolean isRedditUser(User user) throws IOException {
	return Connector.getInstance().isFound(USER_ROOT + user.getUsername() + SUFIX);
    }

    public String aboutUser(User user) throws IOException {
	return Connector.getInstance().openResponse(USER_ROOT + user.getUsername() + ABOUT + SUFIX)
		.getJSONObject("data").toString();
    }

    private JSONObject findMostRecentPost(User user) throws IOException {
	JSONObject body = Connector.getInstance().openResponse(USER_ROOT + user.getUsername() + SUFIX);
	JSONObject data = body.getJSONObject("data");
	JSONArray children = data.getJSONArray("children");
	return children.isEmpty() ? new JSONObject() : children.getJSONObject(0);
    }

    protected boolean hasNoPosts(User user) throws JSONException, IOException {
	return isRedditUser(user) ? findMostRecentPost(user).isEmpty() : true;
    }

    public int getKarmaOfMostRecentPost(User user) throws IOException {
	return hasNoPosts(user) ? 0 : findMostRecentPost(user).getJSONObject("data").getInt("score");
    }

    public int getUpsOfMostRecentPost(User user) throws JSONException, IOException {
	return hasNoPosts(user) ? 0 : findMostRecentPost(user).getJSONObject("data").getInt("ups");
    }

    public int getDownsOfMostRecentPost(User user) throws JSONException, IOException {
	return hasNoPosts(user) ? 0 : findMostRecentPost(user).getJSONObject("data").getInt("downs");
    }

    public String getBodyOfMostRecentPost(User user) throws IOException {
	return hasNoPosts(user) ? user.toString() : findMostRecentPost(user).getJSONObject("data").getString("body");
    }
}
