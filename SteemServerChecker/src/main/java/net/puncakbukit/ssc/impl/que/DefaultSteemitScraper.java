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
		private Document document;

		@Override
		public SteemitScraper build() {
			return new DefaultSteemitScraper(url, timeout, servers, document);
		}

		@Override
		public net.puncakbukit.ssc.que.SteemitScraper.Builder withDocument(Document document) {
			this.document = document;
			return this;
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
	private final Document document;

	private DefaultSteemitScraper(String url, int timeout, List<SteemServer> servers, Document document) {
		this.url = url;
		this.timeout = timeout;
		this.servers = servers;
		this.document = document;
	}

	@Override
	public Document getDocument() {
		return document;
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

	private Document retrieveDoc() {
		try {
			CheckQueryUtil.checkNotNullNorEmpty(url, "url");
			Connection connection = Jsoup.connect(url);
			CheckQueryUtil.checkNotMinus(timeout, "timeout");
			connection.timeout(timeout);
			return connection.get();
		} catch (IOException e) {
			throw new SteemServerCheckerException("retrieveDoc", e);
		}
	}

	@Override
	public SteemitScraper scrape() {
		final Document document = this.document == null ? retrieveDoc() : this.document;
		log.info("scrape - " + document.title());
		final Element table = document.selectFirst(".wikitable");
		final Elements rows = table.select("tr");
		final List<SteemServer> servers = new ArrayList<>();
		for (int i = 1; i < rows.size(); i++) { // first row is the col names so skip it.
			try {
				final Elements cols = rows.get(i)
						.select("td");
				final String ssl = cols.get(1)
						.text();
				final String status = cols.get(5)
						.text();
				if (status.equals("Operational") && ssl.equals("YES")) {
					SteemServer server = new SteemServer();
					server.server = cols.get(0)
							.text()
							.trim();
					server.ranBy = cols.get(4)
							.text()
							.trim();
					servers.add(server);
				}
			} catch (Exception e) {
				// logs and resumes
				log.warn("scrape", e);
			}
		}
		return new DefaultSteemitScraper(url, timeout, servers, document);
	}

}
