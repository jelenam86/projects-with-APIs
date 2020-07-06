package main.java.basic_twitter_bot.twitter;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class Twitter4jUtil {

	public static Twitter getTwitterInstance() {
		Twitter twitter = TwitterFactory.getSingleton();
		return twitter;
	}

	public static String createTweet(String tweet) throws TwitterException {
		Twitter twitter = getTwitterInstance();
		Status status = twitter.updateStatus(tweet);
		return status.getText();
	}

	protected static User showUser(String username) throws TwitterException {
		return getTwitterInstance().showUser(username);
	}
}
