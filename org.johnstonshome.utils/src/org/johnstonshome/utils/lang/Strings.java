/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.johnstonshome.utils.fun.Functional;

/**
 * This class contains common utility functions for dealing with Strings in 
 * Java. Some of these functions are common to String classes in other 
 * languages (such as {@link #split(String, String)} or {@link #join(String[], String)})
 *  whereas others are simply common operations necessary when dealing with 
 *  Strings.
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
	 * Denotes the standard line separator, or end-of-line character.
	 */
    public static final String EOL   = System.getProperty("line.separator");

    
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
		if (source == null || sep == null) {
			return null;
		}
		final List<String> list = new LinkedList<String>();
		if (!isEmpty(source)) {
			final StringTokenizer tokenizer = new StringTokenizer(source, sep, false);
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
		if (source == null || sep == null) {
			return null;
		}
		List<String> list = new LinkedList<String>();
		if (!isEmpty(source)) {
			final Matcher matcher = sep.matcher(source);
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
	 * Combine the list of strings provided with a copy of the separator
	 * string between them.
	 * 
	 * @param strings the list of strings to join
	 * @param sep the separator string
	 * @return a combined string 
	 */
	public static final String join(final List<String> strings, final String sep) {
		if (strings == null) {
			return null;
		}
		final StringBuilder builder = new StringBuilder();
		final int           last    = strings.size() -1;
		for (int i = 0; i < strings.size(); i++) {
			builder.append(strings.get(i));
			if (i < last) {
				builder.append(sep == null ? EMPTY : sep);
			}
		}
		return builder.toString();
	}
	
	/**
	 * Combine the array of strings provided with a copy of the separator
	 * string between them.
	 * 
	 * @param strings the array of strings to join
	 * @param sep the separator string
	 * @return a combined string 
	 */
	public static final String join(final String[] strings, final String sep) {
		if (strings == null) {
			return null;
		}
		final StringBuilder builder = new StringBuilder();
		final int           last    = strings.length -1;
		for (int i = 0; i < strings.length; i++) {
			builder.append(strings[i]);
			if (i < last) {
				builder.append(sep == null ? EMPTY : sep);
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
	public static final List<Character> explode(final String string) {
		final List<Character> results = new ArrayList<Character>(string == null ? 0 : string.length());
		if (string != null && !isEmpty(string)) {
			final char[] chars = string.toCharArray();
			for (final char next : chars) {
				results.add(new Character(next));
			}
		}
		return results;
	}

	/**
     * Interpolate a string, that is to extract variable references in
     * a template and replace them from a context. The context in this case
     * is a Map from String->? where the value's <code>toString()</code> 
     * method will be used to formulate the variable replacement.
     * 
     * The following example shows this in action.
     * 
     * <pre>
     *     Map<String, String> variables = new hashMap<String, String>();
     *     variables.put("author", "My Name");
     *     variables.put("location", "Here I Am");
     *     final String template = Strings.fromFile(new File("template.txt"));
     *     final String result = Strings.interpolate(template, variables, null);
     *     Strings.toFile(result, new File("my-file.txt"));
     * </pre>
	 * 
	 * @param <V> the type of values in the variable map, usually
	 *           String or Object
	 * @param template the template string containing variables. If the 
	 *           <em>template</em> is <code>null</code> the returned value 
     *           will be <code>null</code> also. 
	 * @param values values that may be inserted into the template. If the
	 *           map is <code>null</code> then all variables will be replaced
	 *           with <em>defaultValue</em>.
	 * @param defaultValue a default value to insert into the output if no 
	 *           value is found within <em>values</em> for a named variable.
	 *           If <em>defaultValue</em> is <code>null</code> then the 
	 *           value returned in the output will be of the form "!key-name!"
	 *           so that the output clearly signals the error to the user.
	 * @return the expanded form of the template with all variables either 
	 *           replaced from <em>values</em>, or with <em>defaultValue</em>.
	 */
    public static <V> String interpolate(final String template, final Map<String, V> values, final String defaultValue) {
    	if (template == null) {
    		return null;
    	}
        final Pattern       VARIABLE = Pattern.compile("\\$\\{([^\\}]+)\\}");
        final Matcher       matcher  = VARIABLE.matcher(template);
        final StringBuilder output   = new StringBuilder();
        
        int start = 0;
        while (matcher.find()) {
            if (matcher.start() - start > 0) {
                output.append(template.substring(start, matcher.start()));
            }
            final String mapKey = matcher.group(1);
            if (values != null && values.containsKey(mapKey)) {
                output.append(values.get(mapKey).toString());
            } else {
            	if (defaultValue != null) {
            		output.append(defaultValue);
            	} else {
            		output.append(String.format("!%s!", mapKey));
            	}
            }
            start = matcher.end();
        }
        if (start < template.length()) {
            output.append(template.substring(start));
        }
        return output.toString();
    }

    /**
     * Read a file entirely into a string value, the caller is responsible
     * for ensuring that the file is representable as a string.
     * 
     * @param file the file to read from; if <em>file</em> is <code>null</code>
     *           the return will be <code>null</code>.
     * @return the string contents of the file, or <code>null</code> on error.
     */
    public static String fromFile(final File file) {
        try {
            return fromReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            return null;
        }
    }
    
    /**
     * Write the given string to the specified file, this will always 
     * overwrite the existing contents replacing it with the value of
     * <em>source</em>.
     * 
     * @param source the string to write to the file; if <em>source</em>
     *          is <code>null</code> then the file will be created 
     *          empty.
     * @param file the file descriptor that denotes the file to write to;
     *          if <em>file</em> is <code>null</code> the function
     *          returns <code>false</code>.
     * @return <code>true</code> if the file was written successfully, 
     *          else <code>false</code>.
     */
    public static boolean toFile(final String source, final File file) {
    	return toFile(source, file, false);
    }

    /**
     * Write the given string to the specified file.
     * 
     * @param source the string to write to the file; if <em>source</em>
     *          is <code>null</code> then the file will be created 
     *          empty.
     * @param file the file descriptor that denotes the file to write to;
     *          if <em>file</em> is <code>null</code> the function
     *          returns <code>false</code>.
     * @param append if <code>true</code> the content specified will be
     *          appended to <em>file</em>, else the file will be replaced
     *          with the content of <em>source</em>.
     * @return <code>true</code> if the file was written successfully, 
     *          else <code>false</code>.
     */
    public static boolean toFile(final String source, final File file, final boolean append) {
    	boolean ok = (file != null && file.canWrite());
    	if (ok) {
    		Writer writer = null;
    		try {
				writer = new FileWriter(file, append);
				if (source != null) {
					writer.write(source);
				}
			} catch (IOException e) {
				e.printStackTrace();
				ok = false;
    		} finally {
    			if (writer != null) {
    				try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
    			}
			}
    	}
    	return ok;
    }
    
    /**
     * Read a stream entirely into a string value, the caller is responsible
     * for ensuring that the stream is representable as a string.
     * 
     * Note that this function does not close the {@link InputStream}, this is 
     * expected to be performed by the caller.
     * 
     * @param stream the stream to read from; if <em>stream</em> is 
     *           <code>null</code>the return will be <code>null</code>.
     * @return the string contents of the stream, or <code>null</code> on error.
     */
    public static String fromInputStream(final InputStream stream) {
    	if (stream == null) {
    		return null;
    	}
        return fromReader(new InputStreamReader(stream));
    }
    
    /**
     * Read from a <em>reader</em> entirely into a string value, the caller is 
     * responsible for ensuring that the reader is representable as a string.
     * 
     * Note that this function does not close the {@link Reader}, this is 
     * expected to be performed by the caller.
     * 
     * @param reader the reader to read from; if <em>stream</em> is 
     *           <code>null</code>the return will be <code>null</code>.
     * @return the string contents of the reader, or <code>null</code> on error.
     */
    public static String fromReader(final Reader reader) {
    	if (reader == null) {
    		return null;
    	}
        final BufferedReader buffered = new BufferedReader(reader);
        final StringBuilder  builder  = new StringBuilder();
        String               line     = null;
        try {
            while ((line = buffered.readLine()) != null) {
                builder.append(line);
                builder.append(EOL);
            }
            return builder.toString();        
        } catch (IOException e) {
            return null;
        }
    }

}
