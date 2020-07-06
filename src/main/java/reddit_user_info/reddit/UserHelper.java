package main.java.reddit_user_info.reddit;

import java.util.Comparator;
import java.util.List;

public class UserHelper {

    private List<User> users;

    public UserHelper() {
	this.users = User.getUsers();
    }

    public User getRedditUserWithMostKarma() {
	return users.stream().max(Comparator.comparingInt(User::getScore)).get();
    }

    public int getDifferenceInRecentPosts(User u1, User u2) {
	return Math.abs(u1.getScore() - u2.getScore());
    }

}
