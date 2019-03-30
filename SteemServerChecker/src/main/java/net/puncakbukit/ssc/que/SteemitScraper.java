package net.puncakbukit.ssc.que;

import java.util.List;

import org.jsoup.nodes.Document;

import net.puncakbukit.ssc.SteemServer;

/**
 * Scraper to scrape Steemit site for list of STEEM servers.
 * 
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
public interface SteemitScraper {

	/**
	 * Builder of the scraper.
	 */
	public interface Builder {

		SteemitScraper build();

		Builder withDocument(Document document);

		Builder withServers(List<SteemServer> servers);

		Builder withTimeout(int i);

		Builder withURL(String string);
	}

	/**
	 * Jsoup document containing site content.
	 * 
	 * @return
	 */
	Document getDocument();

	/**
	 * List of servers scraped.
	 * 
	 * @return
	 */
	List<SteemServer> getServers();

	/**
	 * Timeout when connecting the site.
	 * 
	 * @return
	 */
	int getTimeout();

	/**
	 * URL of the site.
	 * 
	 * @return
	 */
	String getUrl();

	/**
	 * Scrapes the site for list of STEEM servers.
	 * 
	 * @return
	 */
	SteemitScraper scrape();

}
