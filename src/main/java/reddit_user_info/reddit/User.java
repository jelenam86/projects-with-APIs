package main.java.reddit_user_info.reddit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class User {

    private final String username;
    private int mostRecentScore;
    private boolean hasRedditAccount;
    private boolean posted;
    private ObjectMapper mapper = new ObjectMapper();
    @JsonUnwrapped
    private AboutUser about;
    private RedditUtil reddit = RedditUtil.getInstance();

    private static List<User> users = new ArrayList<User>();

    public User(final String username) throws IOException {
	this.username = username;
	this.hasRedditAccount = reddit.isRedditUser(this);
	this.posted = !reddit.hasNoPosts(this);
	setScore(reddit.getKarmaOfMostRecentPost(this));
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	about = hasRedditAccount ? mapper.readValue(reddit.aboutUser(this), AboutUser.class) : null;
	users.add(this);
	System.out.println(this);
    }

    public String getUsername() {
	return username;
    }

    public String getDisplayName() {
	return isRedditUser() ? about.name : getUsername();
    }

    public int getScore() {
	return mostRecentScore;
    }

    public void setScore(int score) {
	this.mostRecentScore = score;
    }

    public boolean isRedditUser() {
	return hasRedditAccount;
    }

    public static List<User> getUsers() {
	return users;
    }

    public boolean hasPosts() {
	return posted;
    }

    public int getLinkKarma() {
	return isRedditUser() ? about.linkKarma : 0;
    }

    public int getCommentKarma() {
	return isRedditUser() ? about.commentKarma : 0;
    }

    public int getTotalKarma() {
	return getLinkKarma() + getCommentKarma();
    }

    @Override
    public String toString() {
	String posts;
	try {
	    posts = hasPosts()
		    ? String.format("Score on recent post: %d, of which %d upvotes and %d downvotes.", getScore(),
			    reddit.getUpsOfMostRecentPost(this), reddit.getDownsOfMostRecentPost(this))
		    : "Not posted anything yet.";
	} catch (JSONException | IOException e) {
	    posts = "";
	}
	String user = isRedditUser()
		? String.format("%s is Reddit user. %s Total: %s. %s", getDisplayName(), about, getTotalKarma(), posts)
		: String.format("%s is not Reddit user.", getDisplayName());
	return user;
    }

    static class AboutUser {
	@JsonProperty("link_karma")
	private int linkKarma;
	@JsonProperty("comment_karma")
	private int commentKarma;
	@JsonProperty("name")
	private String name;

	@Override
	public String toString() {
	    return String.format("Karma on comments: %d, on links: %d.", commentKarma, linkKarma);
	}
    }
}
