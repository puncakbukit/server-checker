package net.puncakbukit.ssc;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import net.puncakbukit.ssc.impl.que.DefaultServerChecker;
import net.puncakbukit.ssc.impl.que.DefaultSteemitScraper;
import net.puncakbukit.ssc.que.ServerChecker;
import net.puncakbukit.ssc.que.SteemitScraper;
import net.puncakbukit.util.com.FileCommandUtil;
import net.puncakbukit.util.que.CheckQueryUtil;
import net.puncakbukit.util.que.FileQueryUtil;
import net.puncakbukit.util.que.StringQueryUtil;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Main class implementing Runnable which then run by Picocli framework.
 *
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
@Command(name = SteemServerChecker.APP_NAME, mixinStandardHelpOptions = true, version = SteemServerChecker.APP_VERSION)
public class SteemServerChecker implements Runnable {

	private static Logger LOG = LoggerFactory.getLogger(SteemServerChecker.class);

	public static final String APP_NAME = "STEEM Server Checker";
	public static final String APP_DESCRIPTION = "Checks STEEM public RPC servers (full nodes) whether accessible or not.";
	public static final String APP_VERSION = "1.0";

	public static void main(String[] args) {
		CommandLine.run(new SteemServerChecker(), args);
	}

	@Option(names = { "-v", "--verbose" }, description = "Verbose mode. Helpful for troubleshooting. "
			+ "Multiple -v options increase the verbosity.")
	private boolean[] verbose = new boolean[0];

	@Option(names = { "-p", "--protocol" }, description = "URL protocol of the STEEM server.")
	private String protocol = "https";

	@Option(names = { "-u", "--url" }, description = "URL of site that lists STEEM public servers to check.")
	private String url = "https://www.steem.center/index.php?title=Public_Websocket_Servers";

	@Option(names = { "-t", "--steemitTimeout" }, description = "Timeout when checking the Steemit site (in ms).")
	private int steemitTimeout = 30000;

	@Option(names = { "-s", "--serverTimeout" }, description = "Timeout when checking the STEEM server (in ms).")
	private int serverTimeout = 30000;

	@Option(names = { "-a", "--account" }, description = "STEEM account to use when checking the STEEM server.")
	private String account = "puncakbukit";

	@Option(names = { "-o",
			"--output-file" }, description = "Output file to save list of STEEM servers in JSON format.")
	private String outputFile = "/var/www/servers.puncakbukit.net/public_html/servers.json";
	// private String outputFile = "servers.json";

	/**
	 * Finds last changed time of a server status.
	 * 
	 * @param server	the current server status
	 * @param servers	the list of previous server status
	 * @return 			the last changed time
	 */
	private String findLastChanged(SteemServer server, List<SteemServer> servers) {
		for (SteemServer s : servers) {
			if (s.server.equals(server.server)) {
				if (s.status.equals(server.status)) {
					if (s.cause.equals(server.cause)) {
						return s.lastChanged;
					} else {
						return Instant.now()
								.toString();
					}
				} else {
					return Instant.now()
							.toString();
				}
			}
		}
		return Instant.now()
				.toString();
	}

	/**
	 * Initializes the application.
	 */
	private void init() {
		if (verbose.length > 1) {
			LOG.trace("init entered..");
		}
		LOG.info("init - " + APP_NAME + " " + APP_VERSION);
		LOG.info("init - " + APP_DESCRIPTION);
		if (verbose.length > 0) {
			try {
				LOG.debug("init - JAR path: " + new File(SteemServerChecker.class.getProtectionDomain()
						.getCodeSource()
						.getLocation()
						.toURI()).getCanonicalPath());
			} catch (URISyntaxException | IOException e) {
				LOG.error("init", e);
			}
			// LOG.debug("init - Working directory: " + System.getProperty("user.dir"));
			Properties properties = System.getProperties();
			LOG.debug("init - System properties:");
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				LOG.debug("init - " + entry.getKey() + " = " + entry.getValue());
			}
			Map<String, String> env = System.getenv();
			LOG.debug("init - Environment variables:");
			for (String envName : env.keySet()) {
				LOG.debug("init - " + envName + "=" + env.get(envName));
			}
		}
		if (verbose.length > 1) {
			LOG.trace("init exits.");
		}
	}

	/**
	 * Runs main algorithm.
	 */
	@Override
	public void run() {
		if (verbose.length > 1) {
			LOG.trace("run entered..");
		}
		init();
		//
		// Scrapes the Steemit site for list of STEEM servers.
		//
		SteemitScraper scraper = new DefaultSteemitScraper.Builder().withURL(url)
				.withTimeout(steemitTimeout)
				.build()
				.scrape();
		if (verbose.length > 0) {
			LOG.debug("run - " + Arrays.toString(scraper.getServers()
					.toArray(new SteemServer[0])));
		}
		//
		// Reads existing output file in JSON format for list of previous server status.
		//
		Gson gson = new Gson();
		CheckQueryUtil.checkNotNullNorEmpty(outputFile, "outputFile");
		if (!new File(outputFile).exists()) {
			FileCommandUtil.spitFile("", outputFile, StandardCharsets.UTF_8);
		}
		SteemServer[] arr = gson.fromJson(FileQueryUtil.slurpFile(outputFile, StandardCharsets.UTF_8),
				SteemServer[].class);
		List<SteemServer> servers = Arrays.asList(arr == null ? new SteemServer[0] : arr);
		//
		// Loops the list of servers scraped.
		//
		for (SteemServer server : scraper.getServers()) {
			try {
				//
				// Checks the server if it is UP or DOWN.
				//
				if (verbose.length > 0) {
					LOG.debug("run - " + server.server);
				}
				ServerChecker checker = new DefaultServerChecker.Builder().withProtocol(protocol)
						.withServer(server.server)
						.withTimeout(serverTimeout)
						.withAccount(account)
						.build()
						.check();
				server.status = checker.getStatus();
				server.cause = StringQueryUtil.removeHTML(checker.getCause())
						.trim();
				server.lastChanged = findLastChanged(server, servers);
				server.lastChecked = Instant.now()
						.toString();
				if (verbose.length > 0) {
					LOG.debug("run - " + server);
				}
			} catch (Exception e) {
				// logs and resumes
				LOG.error("run", e);
			}
		}
		//
		// Writes list of server status to output file in JSON format.
		//
		FileCommandUtil.spitFile(gson.toJson(scraper.getServers()), outputFile, StandardCharsets.UTF_8);
		if (verbose.length > 1) {
			LOG.trace("run exits.");
		}
	}

}
