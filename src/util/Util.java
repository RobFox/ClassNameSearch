package util;

import java.util.ArrayList;
import java.util.List;

public class Util {

    /**
     * Splits a String based on the given SplitDecider
     * @param string String to split
     * @param decider decides when to split
     * @return split Strings as a list
     */
	public static List<String> split(String string, SplitDecider decider) {
		StringBuffer word = new StringBuffer();
		List<String> words = new ArrayList<String>();
		for (int i = 0; i < string.length(); i++) {
			if (decider.split(string.charAt(i)) && word.length() > 0) {
				words.add(word.toString());
				word.setLength(0);
			}
			word.append(string.charAt(i));
		}
		words.add(word.toString());
		word.setLength(0);
		return words;
	}

}
