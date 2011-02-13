/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.lang;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.johnstonshome.utils.fun.Functional;

/**
 * This class contains utility classes 
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class Strings {
	
	/**
	 * Denotes the empty, non-null String.
	 */
	public static final String EMPTY = "";
	
	/**
	 * Determine whether the string is empty, which includes null.
	 * 
	 * @param string a string value to test
	 * @return <code>true</code> if the string is either <code>null</code>
	 *     or empty.
	 */
	public static final boolean isEmpty(final String string) {
		return (string == null || string.equals(Strings.EMPTY));
	}

	/**
	 * Split the string into a list of values using the indicated string as a
	 * separator. The separator strings are not returned as a part of the list.
	 * 
	 * @param source the source string to split
	 * @param sep the separator string
	 * @return a list of strings representing the set of separated values from 
	 *     the source
	 */
	public static List<String> split(final String source, final String sep) {
		List<String> list = new LinkedList<String>();
		// TODO: null guards
		if (!isEmpty(source)) {
			StringTokenizer tokenizer = new StringTokenizer(source, sep, false);
			while (tokenizer.hasMoreTokens()) {
				list.add((String)tokenizer.nextElement());
			}
		}
		return list;
	}

	/**
	 * Split the string into a list of values using the indicated regular
	 * expression {@link Pattern} as a separator. The separator values
	 * are not returned as a part of the list.
	 * 
	 * @param source the source string to split
	 * @param sep the separator pattern
	 * @return a list of strings representing the set of separated values from 
	 *     the source
	 */
	public static List<String> split(final String source, final Pattern sep) {
		List<String> list = new LinkedList<String>();
		// TODO: null guards
		if (!isEmpty(source)) {
			Matcher matcher = sep.matcher(source);
			int start = 0;
			while (matcher.find(start)) {
				list.add(source.substring(start, matcher.start()));
				start = matcher.end();
			}
			list.add(source.substring(start));
		}
		return list;
	}

	/**
	 * Combine the set of strings provided with a copy of the separator
	 * string between them.
	 * 
	 * @param strings the list of strings to join
	 * @param sep the separator string
	 * @return a combined string 
	 */
	public static final String join(final List<String> strings, final String sep) {
		StringBuilder builder = new StringBuilder();
		// TODO: null guards
		final int last = strings.size() -1;
		for (int i = 0; i < strings.size(); i++) {
			builder.append(strings.get(i));
			if (i < last) {
				builder.append(sep);
			}
		}
		return builder.toString();
	}
	
	/**
	 * Split a string into a List of Characters, this is akin to the String
	 * method {@link String#toCharArray()} but returns a {@link List} which
	 * can be manipulated by the {@link Functional} utilities.
	 * 
	 * @param string a string to convert into a character list
	 * @return a list containing the characters of the string.
	 */
	public static final List<Character> explode(String string) {
		List<Character> results = new ArrayList<Character>(string == null ? 0 : string.length());
		if (!isEmpty(string)) {
			final char[] chars = string.toCharArray();
			for (final char next : chars) {
				results.add(new Character(next));
			}
		}
		return results;
	}

}
