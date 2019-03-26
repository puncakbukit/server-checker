package net.puncakbukit.util.com;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import net.puncakbukit.util.PBUtilException;

/**
 * Collection of command utilities related to file.
 * 
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
public class FileCommandUtil {

	/**
	 * Spits string as content into a file.
	 * 
	 * @param string
	 * @param filename
	 * @param encoding
	 */
	public static void spitFile(String string, String filename, Charset encoding) {
		try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename), encoding)) {
			writer.write(string);
		} catch (IOException e) {
			throw new PBUtilException("spitFile", e);
		}
	}
}
