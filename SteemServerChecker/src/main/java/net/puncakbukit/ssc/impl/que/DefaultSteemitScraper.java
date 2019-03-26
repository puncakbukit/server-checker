package net.puncakbukit.ssc.impl.que;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.puncakbukit.ssc.SteemServer;
import net.puncakbukit.ssc.SteemServerCheckerException;
import net.puncakbukit.ssc.que.SteemitScraper;
import net.puncakbukit.util.que.CheckQueryUtil;

/**
 * Default implementation of SteemitScraper interface.
 * 
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
public class DefaultSteemitScraper implements SteemitScraper {

	public static class Builder implements SteemitScraper.Builder {
		private String url;
		private int timeout;
		private List<SteemServer> servers;

		@Override
		public SteemitScraper build() {
			return new DefaultSteemitScraper(url, timeout, servers);
		}

		@Override
		public SteemitScraper.Builder withServers(List<SteemServer> servers) {
			this.servers = servers;
			return this;
		}

		@Override
		public SteemitScraper.Builder withTimeout(int timeout) {
			this.timeout = timeout;
			return this;
		}

		@Override
		public SteemitScraper.Builder withURL(String url) {
			this.url = url;
			return this;
		}
	}

	private Logger log = LoggerFactory.getLogger(DefaultSteemitScraper.class);
	private final String url;
	private final int timeout;
	private final List<SteemServer> servers;

	private DefaultSteemitScraper(String url, int timeout, List<SteemServer> servers) {
		this.url = url;
		this.timeout = timeout;
		this.servers = servers;
	}

	@Override
	public List<SteemServer> getServers() {
		return servers;
	}

	@Override
	public int getTimeout() {
		return timeout;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public SteemitScraper scrape() {
		try {
			CheckQueryUtil.checkNotNullNorEmpty(url, "url");
			Connection connection = Jsoup.connect(url);
			CheckQueryUtil.checkNotMinus(timeout, "timeout");
			connection.timeout(timeout);
			Document doc = connection.get();
			log.info(doc.title());
			Element table = doc.selectFirst(".wikitable");
			Elements rows = table.select("tr");
			List<SteemServer> servers = new ArrayList<>();
			for (int i = 1; i < rows.size(); i++) { // first row is the col names so skip it.
				Elements cols = rows.get(i)
						.select("td");
				String ssl = cols.get(1)
						.text();
				String status = cols.get(5)
						.text();
				if (status.equals("Operational") && ssl.equals("YES")) {
					SteemServer server = new SteemServer();
					server.server = cols.get(0)
							.text();
					server.ranBy = cols.get(4)
							.text();
					servers.add(server);
				}
			}
			return new DefaultSteemitScraper(url, timeout, servers);
		} catch (IOException e) {
			throw new SteemServerCheckerException("scrape", e);
		}
	}

}
