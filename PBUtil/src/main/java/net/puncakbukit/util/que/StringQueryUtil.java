package net.puncakbukit.util.que;

/**
 * Collection of query utilities related to string.
 * 
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
public class StringQueryUtil {

	/**
	 * Removes all &lt;html&gt;HTML blocks&lt;/html&gt; from string.
	 * 
	 * @param string
	 * @return
	 */
	public static String removeHTML(String string) {
		return string.replaceAll("(?s)<[a-zA-Z]+[^>]*>.*</[a-zA-Z]+>", "");
	}

}
