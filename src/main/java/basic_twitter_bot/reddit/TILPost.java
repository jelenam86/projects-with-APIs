package main.java.basic_twitter_bot.reddit;

import java.io.IOException;

import main.java.util.TextHelper;

public class TILPost {

    private static final String[] PREFIX = { "TIL", ":", " ", "that ", "about ", "of ", "- " };
    private static final String FILE_PATH = "src/main/java/basic_twitter_bot/resources/facts.txt";
    private String fact;
    private Post post;

    public TILPost() {
	this.fact = "";
    }

    public boolean repeatedPost(String post) {
	return fact.equals(post);
    }

    public void setFact(String text) {
	this.fact = TextHelper.cleanUpText(PREFIX, text);
    }

    public String getFact() {
	return fact;
    }

    public Post getPost() {
	return post;
    }

    public void writeFactToTheFile() throws IOException {
	TextHelper.writeToFile(fact, FILE_PATH);
    }

    public String getNewPost() throws IOException {
	post = TILUtil.getInstance().getNewPost();
	return post.toString();
    }

    public String getHotPost() throws IOException {
	post = TILUtil.getInstance().getHotPost();
	return post.toString();
    }

    public String getTopPost() throws IOException {
	post = TILUtil.getInstance().getTopPost();
	return post.toString();
    }

    public String getLongestFactOf(int maxLength) {
	return TextHelper.isLonger(fact, maxLength) ? fact.substring(0, maxLength) : fact;
    }
}
