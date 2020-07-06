package main.java.reddit_user_info;

import java.io.IOException;

import main.java.reddit_user_info.reddit.RedditUtil;
import main.java.reddit_user_info.reddit.User;
import main.java.reddit_user_info.reddit.UserHelper;

public class SubgoalsMain {

    public static void main(String[] args) throws IOException {

	User u1 = new User("jelenamat86"); // is not user
	User u2 = new User("jelenam86"); // me on Reddit
	User u3 = new User("fenface"); // random user from Reddit
	User u4 = new User("umikaloo"); // random user from Reddit
	User u5 = new User("sean_ornery"); // random user from Reddit

	UserHelper helper = new UserHelper();
	User user = helper.getRedditUserWithMostKarma();

	System.out.println("User with most Karma on recent post: " + user.getDisplayName());
	System.out.println("His/her recent post is: \n" + RedditUtil.getInstance().getBodyOfMostRecentPost(user));

    }

}
