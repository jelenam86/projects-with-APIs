package main.java.random_wiki;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class Main {

    public static void main(String[] args) throws JSONException, IOException, URISyntaxException {
	
	Scanner scanner = new Scanner(System.in);
	String answer = "yes";
	JSONObject article;

	while (answer.equalsIgnoreCase("yes")) {
	    System.out.println("Type \"yes\" to read, \"stop\" to exit, ENTER or any other character(s) to continue.");
	    article = Wikipedia.getRandomArticle();
	    System.out.println("Would you like to read the article: \"" + Wikipedia.getArticleTitle(article) + "\"?");
	    String read = scanner.nextLine();
	    if (read.equalsIgnoreCase("yes")) {
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
		    Desktop.getDesktop().browse(new URI(Wikipedia.getArticleURL(Wikipedia.getArticleID(article))));
		}
		System.out.println("When you ready to continue, press ENTER.");
		System.in.read();
		System.out.println("Do you want to read another article?");
		answer = scanner.next();
	    }
	    if (read.equalsIgnoreCase("stop"))
		answer = read;
	    System.out.println();
	}

	scanner.close();
    }

}
