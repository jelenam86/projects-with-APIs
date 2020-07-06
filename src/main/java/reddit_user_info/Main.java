package main.java.reddit_user_info;

import java.io.IOException;
import java.util.Scanner;

import main.java.reddit_user_info.reddit.User;
import main.java.reddit_user_info.reddit.UserHelper;

public class Main {

    private static String getUsername(Scanner scanner) {
	System.out.print("Enter username: ");
	return scanner.next();
    }

    public static void main(String[] args) throws IOException {

	Scanner scanner = new Scanner(System.in);

	do {
	    User u1 = new User(getUsername(scanner));
	    User u2 = new User(getUsername(scanner));

	    UserHelper helper = new UserHelper();
	    User user = helper.getRedditUserWithMostKarma();

	    System.out.println("User with most Karma on recent post: " + user.getUsername());
	    System.out.println("Difference is: " + helper.getDifferenceInRecentPosts(u1, u2));

	    System.out.println("Do you want to compare more users?");
	} while (scanner.next().equalsIgnoreCase("yes"));

	scanner.close();
    }

}
