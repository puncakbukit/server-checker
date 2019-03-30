package net.puncakbukit.util.que;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.puncakbukit.util.PBUtilException;

/**
 * Collection of query utilities related to file.
 * 
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
public class FileQueryUtil {

	/**
	 * Slurps file content into string.
	 * 
	 * @param filename
	 * @param encoding
	 * @return
	 */
	public static String slurpFile(String filename, Charset encoding) {
		CheckQueryUtil.checkNotNullNorEmpty(filename, "filename");
		CheckQueryUtil.checkNotNull(encoding, "encoding");
		try {
			return new String(Files.readAllBytes(Paths.get(filename)), encoding);
		} catch (IOException e) {
			throw new PBUtilException("slurpFile", e);
		}
	}

}
