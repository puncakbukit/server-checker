package net.puncakbukit.ssc.impl.que;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.puncakbukit.ssc.SteemServer;
import net.puncakbukit.ssc.que.SteemitScraper;
import net.puncakbukit.util.PBUtilException;

public class DefaultSteemitScraperTest {

	private static final String FILENAME = "DefaultSteemitScraperTest.html";
	private static final List<SteemServer> SERVERS = new ArrayList<>();

	static {
		SteemServer server = new SteemServer();
		server.server = "api.steemit.com";
		server.ranBy = "Steemit Inc.";
		SERVERS.add(server);
		server = new SteemServer();
		server.server = "api.steemitdev.com";
		server.ranBy = "Steemit Inc.";
		SERVERS.add(server);
		server = new SteemServer();
		server.server = "api.steemitstage.com";
		server.ranBy = "Steemit Inc.";
		SERVERS.add(server);
		server = new SteemServer();
		server.server = "api.steem.house";
		server.ranBy = "@gtg";
		SERVERS.add(server);
		server = new SteemServer();
		server.server = "appbasetest.timcliff.com";
		server.ranBy = "@timcliff";
		SERVERS.add(server);
		server = new SteemServer();
		server.server = "appbase.buildteam.io";
		server.ranBy = "@themarkymark";
		SERVERS.add(server);
		server = new SteemServer();
		server.server = "gtg.steem.house:8090";
		server.ranBy = "@gtg";
		SERVERS.add(server);
		server = new SteemServer();
		server.server = "rpc.curiesteem.com";
		server.ranBy = "@curie";
		SERVERS.add(server);
		server = new SteemServer();
		server.server = "rpc.steemliberator.com";
		server.ranBy = "@netuoso";
		SERVERS.add(server);
		server = new SteemServer();
		server.server = "rpc.steemviz.com";
		server.ranBy = "@ausbitbank";
		SERVERS.add(server);
		server = new SteemServer();
		server.server = "steemd.minnowsupportproject.org";
		server.ranBy = "@followbtcnews";
		SERVERS.add(server);
		server = new SteemServer();
		server.server = "steemd.privex.io";
		server.ranBy = "@privex";
		SERVERS.add(server);
		server = new SteemServer();
		server.server = "rpc.usesteem.com";
		server.ranBy = "@themarkymark";
		SERVERS.add(server);
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void scrapeGivenDocumentThenSucceed() throws IOException {
		SteemitScraper scraper = new DefaultSteemitScraper.Builder()
				.withDocument(Jsoup.parse(new File(FILENAME), StandardCharsets.UTF_8.name(), ""))
				.build()
				.scrape();
		assertThat(scraper.getServers(), equalTo(SERVERS));
	}

	@Test(expected = PBUtilException.class)
	public void scrapeGivenTimeoutMinusThenFail() throws IOException {
		String url = "https://";
		int timeout = -1;
		new DefaultSteemitScraper.Builder().withURL(url)
				.withTimeout(timeout)
				.build()
				.scrape();
	}

	@Test(expected = PBUtilException.class)
	public void scrapeGivenUrlNullThenFail() throws IOException {
		String url = null;
		int timeout = 10000;
		new DefaultSteemitScraper.Builder().withURL(url)
				.withTimeout(timeout)
				.build()
				.scrape();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
