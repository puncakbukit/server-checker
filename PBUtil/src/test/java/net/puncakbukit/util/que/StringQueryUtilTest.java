package net.puncakbukit.util.que;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.puncakbukit.util.PBUtilException;

public class StringQueryUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void removeHTMLGivenAllThenSucceed() {
		String string = "This is an <p>HTML paragraph</p>.";
		assertEquals("This is an .", StringQueryUtil.removeHTML(string));
	}

	@Test
	public void removeHTMLGivenDoubleClosedTagThenSucceed() {
		String string = "This is an <p>HTML paragraph</p></p>.";
		assertEquals("This is an .", StringQueryUtil.removeHTML(string));
	}

	@Test
	public void removeHTMLGivenDoubleOpenedTagThenSucceed() {
		String string = "This is an <p><p>HTML paragraph</p>.";
		assertEquals("This is an .", StringQueryUtil.removeHTML(string));
	}

	@Test
	public void removeHTMLGivenFullHtmlThenSucceed() {
		String string = "<p>HTML paragraph</p>";
		assertEquals("", StringQueryUtil.removeHTML(string));
	}

	@Test
	public void removeHTMLGivenInnerTagThenSucceed() {
		String string = "This is an <p>HTML <span>paragraph</span></p>.";
		assertEquals("This is an .", StringQueryUtil.removeHTML(string));
	}

	@Test
	public void removeHTMLGivenNonHtmlThenSucceed() {
		String string = "This is an .";
		assertEquals("This is an .", StringQueryUtil.removeHTML(string));
	}

	@Test
	public void removeHTMLGivenStringEmptyThenSucceed() {
		String string = "";
		assertEquals("", StringQueryUtil.removeHTML(string));
	}

	@Test(expected = PBUtilException.class)
	public void removeHTMLGivenStringNullThenFail() {
		String string = null;
		assertEquals("", StringQueryUtil.removeHTML(string));
	}

	@Test
	public void removeHTMLGivenUnclosedTagThenSucceed() {
		String string = "This is an <p>HTML paragraph.";
		assertEquals("This is an <p>HTML paragraph.", StringQueryUtil.removeHTML(string));
	}

	@Test
	public void removeHTMLGivenUnmatchedTagThenSucceed() {
		String string = "This is an <p>HTML paragraph</span>.";
		assertEquals("This is an .", StringQueryUtil.removeHTML(string));
	}

	@Test
	public void removeHTMLGivenUnopenedTagThenSucceed() {
		String string = "This is an HTML paragraph</p>.";
		assertEquals("This is an HTML paragraph</p>.", StringQueryUtil.removeHTML(string));
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
