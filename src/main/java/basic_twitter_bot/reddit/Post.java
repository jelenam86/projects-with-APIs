package main.java.basic_twitter_bot.reddit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Post {

	@JsonProperty("title")
	private String title;
	@JsonProperty("url")
	private String url;
	@JsonProperty("num_comments")
	private int numberOfComments;
	@JsonProperty("created")
	private long created;
	@JsonProperty("ups")
	private int upvotes;
	@JsonProperty("downs")
	private int downvotes;
	@JsonProperty("author")
	private String author;
	@JsonProperty("permalink")
	private String redditLink;

	@Override
	public String toString() {
		boolean reddit = getUrl().equals(getRedditLink());
		return reddit ? getTitle() + "\n" + getUrl() : getTitle() + "\n" + getUrl() + "\n" + getRedditLink();
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public int getNumberOfComments() {
		return numberOfComments;
	}

	public long getCreated() {
		return created;
	}

	public int getUpvotes() {
		return upvotes;
	}

	public int getDownvotes() {
		return downvotes;
	}

	public String getAuthor() {
		return author;
	}

	public String getRedditLink() {
		return "https://reddit.com" + redditLink;
	}

}
