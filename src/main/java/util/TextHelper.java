package main.java.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class TextHelper {

    public static void writeToFile(String text, String path) throws IOException {
	File file = new File(path);
	file.createNewFile();
	FileUtils.write(file, text + "\n\n", "UTF-8", true);
    }

    public static String cleanUpText(String[] prefixes, String text) {
	String withoutPrefix = text;
	for (String prefix : prefixes)
	    withoutPrefix = removePrefix(withoutPrefix, prefix);
	return capitalize(withoutPrefix);
    }

    public static boolean isLonger(String text, int maxLength) {
	return text.length() > maxLength;
    }

    private static boolean checkPunctationAtTheEnd(String sentence) {
	return sentence.charAt(sentence.length() - 1) == '.';
    }

    private static String capitalizeSentence(String sentence) {
	if (sentence.isBlank() || sentence.isEmpty())
	    return sentence;
	String end = checkPunctationAtTheEnd(sentence) ? "" : ".";
	return sentence.substring(0, 1).toUpperCase() + sentence.substring(1) + end;
    }

    private static String capitalize(String text) {
	String s = "";
	String[] sentences = text.split("\\. ");
	for (String sentence : sentences)
	    s += capitalizeSentence(sentence) + " ";
	return s.substring(0, s.length() - 1);
    }

    private static String removePrefix(String text, String prefix) {
	return text.startsWith(prefix) ? text.substring(prefix.length()) : text;
    }

}
