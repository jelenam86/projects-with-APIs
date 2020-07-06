package main.java.basic_twitter_bot;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import main.java.basic_twitter_bot.reddit.Post;
import main.java.basic_twitter_bot.reddit.TILPost;
import main.java.basic_twitter_bot.twitter.Twitter4jUtil;
import main.java.basic_twitter_bot.twitter.UserInfo;
import main.java.util.TextHelper;
import twitter4j.TwitterException;

public class Main implements Runnable {

    private static final int MAX_LENGHT_TWEET = 280;
    private static final int REDDIT_PAUSE = 30;
    private static final int MAX_TWEET_NUMBER_PER_HOUR = 100;
    private static int counter = 0;
    private Post post;
    private UserInfo user;
    private String fact = "";
    private TILPost tilPost = new TILPost();

    @Override
    public void run() {
	if (counter >= MAX_TWEET_NUMBER_PER_HOUR)
	    System.exit(0);
	try {
	    tilPost.setFact(tilPost.getNewPost());
	    if (!tilPost.repeatedPost(fact)) {
		tilPost.writeFactToTheFile();
		fact = tilPost.getFact();
		Twitter4jUtil.createTweet(twitterString());
		System.out.println(counter++ + ". " + fact);
		System.out.println(isAuthorOnTwitter() ? "From " + user + "\n" + user.getUrl() : "");
	    }
	} catch (TwitterException | IOException e) {
	    System.out.println("Error while proccesing...");
	} finally {
	    System.out.println("Getting new fact...");
	}
    }

    public static void main(String[] args) {
	ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	scheduler.scheduleAtFixedRate(new Main(), 0, REDDIT_PAUSE, TimeUnit.SECONDS);
    }

    private boolean isAuthorOnTwitter() {
	user = new UserInfo(post.getAuthor());
	return user.hasAcc();
    }

    private String twitterString() {
	post = tilPost.getPost();
	String toTweet = post.getTitle() + "\n" + post.getUrl();
	tilPost.setFact(toTweet);
	String tweet = TextHelper.isLonger(tilPost.getFact(), MAX_LENGHT_TWEET) ? post.getTitle() : tilPost.getFact();
	tilPost.setFact(tweet);
	return TextHelper.isLonger(tilPost.getFact(), MAX_LENGHT_TWEET) ? post.getUrl() : tilPost.getFact();
    }
}
