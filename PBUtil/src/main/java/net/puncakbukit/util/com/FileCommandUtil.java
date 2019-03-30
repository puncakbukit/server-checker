package net.puncakbukit.util.com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.puncakbukit.util.PBUtilException;
import net.puncakbukit.util.que.CheckQueryUtil;

/**
 * Collection of command utilities related to file.
 * 
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
public class FileCommandUtil {
	private static Logger LOG = LoggerFactory.getLogger(FileCommandUtil.class);

	private static void _delete(File file) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				_delete(f);
			}
		}
		file.delete();
	}

	/**
	 * Deletes a file or a directory (recursively).
	 * 
	 * @param file
	 */
	public static void delete(File file) {
		CheckQueryUtil.checkNotNull(file, "file");
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				_delete(f);
			}
		}
		try {
			if (!file.delete()) {
				LOG.warn("delete - Failed to delete file: " + file.getCanonicalPath());
			} else {
				LOG.info("delete - " + file.getCanonicalPath() + " deleted!");
			}
		} catch (IOException e) {
			// logs and resumes
			LOG.error("delete - ", e);
		}
	}

	/**
	 * Spits string as content into a file.
	 * 
	 * @param string
	 * @param filename
	 * @param encoding
	 */
	public static void spitFile(String string, String filename, Charset encoding) {
		CheckQueryUtil.checkNotNullNorEmpty(filename, "filename");
		CheckQueryUtil.checkNotNull(encoding, "encoding");
		try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename), encoding)) {
			CheckQueryUtil.checkNotNull(string, "string");
			writer.write(string);
			LOG.info("spitFile - " + filename + " written!");
		} catch (IOException e) {
			throw new PBUtilException("spitFile", e);
		}
	}
}
