package net.puncakbukit.ssf.que;

import java.util.List;

import net.puncakbukit.ssc.SteemServer;

/**
 * Finds public STEEM API servers (a.k.a full nodes) that are alive.
 * 
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
public interface ServerFinder {
	interface Builder {
		/**
		 * Builds the server finder.
		 * 
		 * @return
		 */
		ServerFinder build();

		/**
		 * List of down servers.
		 * 
		 * @param downServers
		 * @return
		 */
		Builder withDownServers(List<SteemServer> downServers);

		/**
		 * JSON representation of the servers.
		 * 
		 * @param json
		 * @return
		 */
		Builder withJson(String json);

		/**
		 * List of servers.
		 * 
		 * @param servers
		 * @return
		 */
		Builder withServers(List<SteemServer> servers);

		/**
		 * Timeout when connecting URL.
		 * 
		 * @param timeout
		 * @return
		 */
		Builder withTimeout(int timeout);

		/**
		 * List of up servers.
		 * 
		 * @param upServers
		 * @return
		 */
		Builder withUpServers(List<SteemServer> upServers);

		/**
		 * URL of JSON file.
		 * 
		 * @param url
		 * @return
		 */
		Builder withUrl(String url);
	}

	/**
	 * Download JSON file if needed and finds the servers.
	 * @return 
	 */
	ServerFinder find();

	/**
	 * Finds a longest down server.
	 * 
	 * @return
	 */
	SteemServer findLongestDown();

	/**
	 * Finds a longest up server.
	 * 
	 * @return
	 */
	SteemServer findLongestUp();

	/**
	 * Finds a random alive server.
	 * 
	 * @return
	 */
	SteemServer findRandom();

	/**
	 * List of down servers.
	 * 
	 * @return
	 */
	List<SteemServer> getDownServers();

	/**
	 * JSON representation of the servers.
	 * 
	 * @return
	 */
	String getJson();

	/**
	 * List of servers.
	 * 
	 * @return
	 */
	List<SteemServer> getServers();

	/**
	 * Timeout when connecting URL.
	 * 
	 * @return
	 */
	int getTimeout();

	/**
	 * List of up servers.
	 * 
	 * @return
	 */
	List<SteemServer> getUpServers();

	/**
	 * URL of JSON file.
	 * 
	 * @return
	 */
	String getUrl();
}
