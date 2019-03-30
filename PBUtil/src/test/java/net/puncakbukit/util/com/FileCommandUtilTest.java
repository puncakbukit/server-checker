package net.puncakbukit.util.com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.puncakbukit.util.PBUtilException;
import net.puncakbukit.util.que.FileQueryUtil;

public class FileCommandUtilTest {

	private static final String FILENAME = "FileCommandUtilTest.txt";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		FileCommandUtil.delete(new File(FILENAME));
	}

	@Test
	public void deleteGivenDirectoryThenSucceed() {
		String dirname = "dir" + File.separator + "subdir" + File.separator + "subsubdir";
		new File(dirname).mkdirs();
		String string = "string";
		String filename = dirname + File.separator + FILENAME;
		Charset encoding = StandardCharsets.UTF_8;
		FileCommandUtil.spitFile(string, filename, encoding);
		File dir = new File("dir");
		FileCommandUtil.delete(dir);
		assertFalse(dir.exists());
	}

	@Test(expected = PBUtilException.class)
	public void deleteGivenFileNullThenFail() {
		File file = null;
		FileCommandUtil.delete(file);
	}

	@Test
	public void deleteGivenFileThenSucceed() {
		String string = "string";
		String filename = FILENAME;
		Charset encoding = StandardCharsets.UTF_8;
		FileCommandUtil.spitFile(string, filename, encoding);
		File file = new File(filename);
		FileCommandUtil.delete(file);
		assertFalse(file.exists());
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void spitFileGivenAllThenSucceed() {
		String string = "string";
		String filename = FILENAME;
		Charset encoding = StandardCharsets.UTF_8;
		FileCommandUtil.spitFile(string, filename, encoding);
		assertEquals(string, FileQueryUtil.slurpFile(filename, encoding));
	}

	@Test(expected = PBUtilException.class)
	public void spitFileGivenEncodingNullThenFail() {
		String string = "string";
		String filename = FILENAME;
		Charset encoding = null;
		FileCommandUtil.spitFile(string, filename, encoding);
	}

	@Test(expected = PBUtilException.class)
	public void spitFileGivenFilenameEmptyThenFail() {
		String string = "string";
		String filename = "";
		Charset encoding = StandardCharsets.UTF_8;
		FileCommandUtil.spitFile(string, filename, encoding);
	}

	@Test(expected = PBUtilException.class)
	public void spitFileGivenFilenameNullThenFail() {
		String string = "string";
		String filename = null;
		Charset encoding = StandardCharsets.UTF_8;
		FileCommandUtil.spitFile(string, filename, encoding);
	}

	@Test(expected = PBUtilException.class)
	public void spitFileGivenStringNullThenFail() {
		String string = null;
		String filename = FILENAME;
		Charset encoding = StandardCharsets.UTF_8;
		FileCommandUtil.spitFile(string, filename, encoding);
	}

	@After
	public void tearDown() throws Exception {
	}

}
