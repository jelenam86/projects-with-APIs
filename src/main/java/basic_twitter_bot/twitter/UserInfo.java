package main.java.basic_twitter_bot.twitter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import twitter4j.TwitterException;
import twitter4j.User;

public class UserInfo {

	private String username;
	private String name;
	private int numberOfFollowers;
	private String url;
	private String description;
	private int numberOfFriends;
	private String dateAccCreated;
	private int numberOfTweets;
	private boolean hasAcc;
	private String currentStatus;

	public UserInfo(String username) {
		this.username = username;
		try {
			User user = Twitter4jUtil.showUser(username);
			hasAcc = true;
			name = user.getName();
			numberOfFollowers = user.getFollowersCount();
			url = user.getURL();
			description = user.getDescription();
			numberOfFriends = user.getFriendsCount();
			dateAccCreated = formatDate(
					user.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			numberOfTweets = user.getStatusesCount();
			currentStatus = user.getStatus().getText() + " @ " + formatDate(LocalDateTime.now());
		} catch (TwitterException e) {
			hasAcc = false;
		}
	}

	private String formatDate(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.US)
				.withZone(ZoneId.systemDefault());
		return formatter.format(date);
	}

	@Override
	public String toString() {
		String acc = hasAcc ? " has account on Twitter" : " doesn't have account on Twitter.";
		String info = String.format("username: %s%s. Name: %s, date created: %s", username, acc, name, dateAccCreated);
		return hasAcc ? info : username + acc;
	}
	
	public String getName() {
		return name;
	}

	public int getNumberOfFollowers() {
		return numberOfFollowers;
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}

	public int getNumberOfFriends() {
		return numberOfFriends;
	}

	public int getNumberOfTweets() {
		return numberOfTweets;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public boolean hasAcc() {
		return hasAcc;
	}
}
