package main.java.random_wiki;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import main.java.util.Connector;

public class Wikipedia {

    private static final String WIKI_API_ROOT = "https://en.wikipedia.org/w/api.php?";
    private static final String ACTION_QUERY = "action=query";
    private static final String RANDOM_QUERY = "&list=random&rnnamespace=0";
    private static final String INFO = "&prop=info&pageids=";
    private static final String INPROP = "&inprop=url";
    private static final String FORMAT = "&format=json";

    protected static JSONObject getRandomArticle() throws MalformedURLException {
	JSONObject article = Connector.getInstance().openResponse(WIKI_API_ROOT + ACTION_QUERY + RANDOM_QUERY + FORMAT);
	JSONObject query = article.getJSONObject("query");
	return query.getJSONArray("random").getJSONObject(0);
    }

    protected static long getArticleID(JSONObject article) throws JSONException, MalformedURLException {
	return article.getLong("id");
    }

    protected static String getArticleTitle(JSONObject article) {
	return article.getString("title");
    }

    protected static String getArticleURL(long id) throws MalformedURLException {
	JSONObject article = Connector.getInstance()
		.openResponse(WIKI_API_ROOT + ACTION_QUERY + INFO + id + INPROP + FORMAT);
	JSONObject query = article.getJSONObject("query").getJSONObject("pages").getJSONObject("" + id);
	return query.getString("fullurl");
    }

}
