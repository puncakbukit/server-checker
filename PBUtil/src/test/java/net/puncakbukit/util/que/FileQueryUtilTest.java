package net.puncakbukit.util.que;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.puncakbukit.util.PBUtilException;

public class FileQueryUtilTest {

	private static final String FILENAME = "FileQueryUtilTest.txt";
	private static final String FILECONTENT = "FileQueryUtilTest";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void slurpFileGivenAllThenSucceed() {
		String filename = FILENAME;
		Charset encoding = StandardCharsets.UTF_8;
		assertEquals(FILECONTENT, FileQueryUtil.slurpFile(filename, encoding));
	}

	@Test(expected = PBUtilException.class)
	public void slurpFileGivenEncodingNullThenFail() {
		String filename = FILENAME;
		Charset encoding = null;
		assertEquals(FILECONTENT, FileQueryUtil.slurpFile(filename, encoding));
	}

	@Test(expected = PBUtilException.class)
	public void slurpFileGivenFilenameNullThenFail() {
		String filename = null;
		Charset encoding = StandardCharsets.UTF_8;
		assertEquals(FILECONTENT, FileQueryUtil.slurpFile(filename, encoding));
	}

	@After
	public void tearDown() throws Exception {
	}

}
