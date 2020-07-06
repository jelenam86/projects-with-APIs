package main.java.basic_twitter_bot;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import main.java.basic_twitter_bot.reddit.TILPost;
import main.java.basic_twitter_bot.reddit.TILUtil;

public class NoTwitterMain implements Runnable {

    private static final int REDDIT_PAUSE = 30;
    private String fact;
    private TILPost tilPost = new TILPost();

    @Override
    public void run() {
	try {
	    tilPost.setFact(tilPost.getNewPost());
	    if (!tilPost.repeatedPost(fact)) {
		tilPost.writeFactToTheFile();
		fact = tilPost.getFact();
		System.out.println(fact);
	    }
	} catch (IOException e) {
	    System.out.println("Error while processing...");
	} finally {
	    System.out.println("Getting new fact...");
	}
    }

    public static void main(String[] args) throws IOException {

	System.out.println(TILUtil.getInstance().toString());

	ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	scheduler.scheduleAtFixedRate(new NoTwitterMain(), 0, REDDIT_PAUSE, TimeUnit.SECONDS);
    }

}
