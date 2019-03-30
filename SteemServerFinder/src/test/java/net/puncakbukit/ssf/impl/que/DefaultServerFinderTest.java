package net.puncakbukit.ssf.impl.que;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.puncakbukit.ssf.que.ServerFinder;
import net.puncakbukit.util.PBUtilException;

public class DefaultServerFinderTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void findGivenJsonThenSucceed() {
		String json = "[{\"server\":\"api.steemit.com\",\"ranBy\":\"Steemit Inc.\",\"status\":\"UP\",\"cause\":\"\",\"lastChanged\":\"2019-03-30T06:17:03.428746Z\",\"lastChecked\":\"2019-03-30T10:17:03.594208Z\"},{\"server\":\"api.steemitdev.com\",\"ranBy\":\"Steemit Inc.\",\"status\":\"DOWN\",\"cause\":\"com.google.api.client.http.HttpResponseException: 503 Service Temporarily Unavailable\",\"lastChanged\":\"2019-03-30T02:17:03.967790Z\",\"lastChecked\":\"2019-03-30T10:17:04.061057Z\"},{\"server\":\"api.steemitstage.com\",\"ranBy\":\"Steemit Inc.\",\"status\":\"DOWN\",\"cause\":\"com.google.api.client.http.HttpResponseException: 503 Service Temporarily Unavailable\",\"lastChanged\":\"2019-03-28T13:47:14.591059Z\",\"lastChecked\":\"2019-03-30T10:17:04.485592Z\"},{\"server\":\"api.steem.house\",\"ranBy\":\"@gtg\",\"status\":\"UP\",\"cause\":\"\",\"lastChanged\":\"2019-03-30T06:17:05.029296Z\",\"lastChecked\":\"2019-03-30T10:17:05.159318Z\"},{\"server\":\"appbasetest.timcliff.com\",\"ranBy\":\"@timcliff\",\"status\":\"DOWN\",\"cause\":\"eu.bittrade.libs.steemj.exceptions.SteemResponseException: Bad Cast:Invalid cast from type \\u0027array_type\\u0027 to Object\",\"lastChanged\":\"2019-03-28T13:47:14.691685Z\",\"lastChecked\":\"2019-03-30T10:17:05.228503Z\"},{\"server\":\"appbase.buildteam.io\",\"ranBy\":\"@themarkymark\",\"status\":\"DOWN\",\"cause\":\"java.net.UnknownHostException: appbase.buildteam.io\",\"lastChanged\":\"2019-03-28T13:47:14.704286Z\",\"lastChecked\":\"2019-03-30T10:17:05.232035Z\"},{\"server\":\"gtg.steem.house:8090\",\"ranBy\":\"@gtg\",\"status\":\"DOWN\",\"cause\":\"com.google.api.client.http.HttpResponseException: 400 Bad Request\",\"lastChanged\":\"2019-03-28T19:17:05.698574Z\",\"lastChecked\":\"2019-03-30T10:17:05.319221Z\"},{\"server\":\"rpc.curiesteem.com\",\"ranBy\":\"@curie\",\"status\":\"DOWN\",\"cause\":\"com.google.api.client.http.HttpResponseException: 403 Forbidden\",\"lastChanged\":\"2019-03-28T13:47:14.851967Z\",\"lastChecked\":\"2019-03-30T10:17:05.368658Z\"},{\"server\":\"rpc.steemliberator.com\",\"ranBy\":\"@netuoso\",\"status\":\"DOWN\",\"cause\":\"java.net.SocketTimeoutException: connect timed out\",\"lastChanged\":\"2019-03-28T13:48:14.916047Z\",\"lastChecked\":\"2019-03-30T10:18:05.464535Z\"},{\"server\":\"rpc.steemviz.com\",\"ranBy\":\"@ausbitbank\",\"status\":\"UP\",\"cause\":\"\",\"lastChanged\":\"2019-03-30T08:18:05.615135Z\",\"lastChecked\":\"2019-03-30T10:18:05.594637Z\"},{\"server\":\"steemd.minnowsupportproject.org\",\"ranBy\":\"@followbtcnews\",\"status\":\"UP\",\"cause\":\"\",\"lastChanged\":\"2019-03-30T08:18:05.675964Z\",\"lastChecked\":\"2019-03-30T10:18:05.652720Z\"},{\"server\":\"steemd.privex.io\",\"ranBy\":\"@privex\",\"status\":\"UP\",\"cause\":\"\",\"lastChanged\":\"2019-03-30T09:18:06.905909Z\",\"lastChecked\":\"2019-03-30T10:18:07.059493Z\"},{\"server\":\"rpc.usesteem.com\",\"ranBy\":\"@themarkymark\",\"status\":\"DOWN\",\"cause\":\"eu.bittrade.libs.steemj.exceptions.SteemResponseException: Bad Cast:Invalid cast from type \\u0027array_type\\u0027 to Object\",\"lastChanged\":\"2019-03-28T13:48:16.218027Z\",\"lastChecked\":\"2019-03-30T10:18:07.173107Z\"}]";
		ServerFinder finder = new DefaultServerFinder.Builder().withJson(json)
				.build()
				.find();
		assertEquals(
				"{\"server\":\"api.steemit.com\", \"ranBy\":\"Steemit Inc.\", \"status\":\"UP\", \"cause\":\"\", \"lastChanged\":\"2019-03-30T06:17:03.428746Z\", \"lastChecked\":\"2019-03-30T10:17:03.594208Z\"}",
				finder.findLongestUp()
						.toString());
		assertEquals(
				"{\"server\":\"appbasetest.timcliff.com\", \"ranBy\":\"@timcliff\", \"status\":\"DOWN\", \"cause\":\"eu.bittrade.libs.steemj.exceptions.SteemResponseException: Bad Cast:Invalid cast from type 'array_type' to Object\", \"lastChanged\":\"2019-03-28T13:47:14.691685Z\", \"lastChecked\":\"2019-03-30T10:17:05.228503Z\"}",
				finder.findLongestDown()
						.toString());
		assertEquals("UP", finder.findRandom().status);
	}

	@Test(expected = PBUtilException.class)
	public void findGivenTimeoutMinusThenFail() {
		String url = "http://";
		int timeout = -1;
		new DefaultServerFinder.Builder().withUrl(url)
				.withTimeout(timeout)
				.build()
				.find();
	}

	@Test(expected = PBUtilException.class)
	public void findGivenUrlNullThenFail() {
		String url = null;
		int timeout = 10000;
		new DefaultServerFinder.Builder().withUrl(url)
				.withTimeout(timeout)
				.build()
				.find();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
