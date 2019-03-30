package net.puncakbukit.util.que;

import net.puncakbukit.util.PBUtilException;

/**
 * Collection of query utilities to check variables.
 * 
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
public class CheckQueryUtil {

	/**
	 * Checks if an integer is not minus.
	 * 
	 * @param value
	 * @param name
	 */
	public static void checkNotMinus(int value, String name) {
		if (value < 0) {
			throw new PBUtilException(name + " is minus!");
		}
	}

	/**
	 * Checks if an object is not null.
	 * 
	 * @param object
	 * @param name
	 */
	public static void checkNotNull(Object object, String name) {
		if (object == null) {
			throw new PBUtilException(name + " is null!");
		}
	}

	/**
	 * Checks if a string is not null nor empty.
	 * 
	 * @param string
	 * @param name
	 */
	public static void checkNotNullNorEmpty(String string, String name) {
		if (string == null || string.isEmpty()) {
			throw new PBUtilException(name + " is null or empty!");
		}
	}

}
