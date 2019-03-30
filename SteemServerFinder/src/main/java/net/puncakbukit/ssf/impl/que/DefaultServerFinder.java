package net.puncakbukit.ssf.impl.que;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import net.puncakbukit.ssc.SteemServer;
import net.puncakbukit.ssf.SteemServerFinderException;
import net.puncakbukit.ssf.que.ServerFinder;
import net.puncakbukit.util.que.CheckQueryUtil;

/**
 * Default implementation of server finder.
 * 
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
public class DefaultServerFinder implements ServerFinder {

	public static class Builder implements ServerFinder.Builder {

		private String url;
		private int timeout;
		private String json;
		private List<SteemServer> servers;
		private List<SteemServer> upServers;
		private List<SteemServer> downServers;

		@Override
		public ServerFinder build() {
			return new DefaultServerFinder(url, timeout, json, servers, upServers, downServers);
		}

		@Override
		public ServerFinder.Builder withDownServers(List<SteemServer> downServers) {
			this.downServers = downServers;
			return this;
		}

		@Override
		public ServerFinder.Builder withJson(String json) {
			this.json = json;
			return this;
		}

		@Override
		public ServerFinder.Builder withServers(List<SteemServer> servers) {
			this.servers = servers;
			return this;
		}

		@Override
		public ServerFinder.Builder withTimeout(int timeout) {
			this.timeout = timeout;
			return this;
		}

		@Override
		public ServerFinder.Builder withUpServers(List<SteemServer> upServers) {
			this.upServers = upServers;
			return this;
		}

		@Override
		public ServerFinder.Builder withUrl(String url) {
			this.url = url;
			return this;
		}

	}

	private Logger log = LoggerFactory.getLogger(DefaultServerFinder.class);
	private final String url;
	private final int timeout;
	private final String json;
	private final List<SteemServer> servers;
	private final List<SteemServer> upServers;
	private final List<SteemServer> downServers;

	public DefaultServerFinder(String url, int timeout, String json, List<SteemServer> servers,
			List<SteemServer> upServers, List<SteemServer> downServers) {
		this.url = url;
		this.timeout = timeout;
		this.json = json;
		this.servers = servers;
		this.upServers = upServers;
		this.downServers = downServers;
	}

	@Override
	public ServerFinder find() {
		final String json = this.json == null ? retrieveJson() : this.json;
		log.info("find - " + json);
		//
		// Reads existing output file in JSON format for list of server status.
		//
		SteemServer[] arr = new Gson().fromJson(json, SteemServer[].class);
		List<SteemServer> servers = Arrays.asList(arr == null ? new SteemServer[0] : arr);
		List<SteemServer> upServers = new ArrayList<SteemServer>();
		List<SteemServer> downServers = new ArrayList<SteemServer>();
		for (SteemServer server : servers) {
			if (server.server != null && server.status != null) {
				if (server.status.equals(SteemServer.UP)) {
					upServers.add(server);
				} else {
					downServers.add(server);
				}
			}
		}
		return new DefaultServerFinder(url, timeout, json, servers, upServers, downServers);
	}

	@Override
	public SteemServer findLongestDown() {
		if (servers == null) {
			return find().findLongestDown();
		} else {
			long longest = -1;
			SteemServer longestServer = null;
			for (SteemServer server : downServers) {
				long interval = Instant.parse(server.lastChecked)
						.getEpochSecond()
						- Instant.parse(server.lastChanged)
								.getEpochSecond();
				if (interval > longest) {
					longest = interval;
					longestServer = server;
				}
			}
			return longestServer;
		}
	}

	@Override
	public SteemServer findLongestUp() {
		if (servers == null) {
			return find().findLongestUp();
		} else {
			long longest = -1;
			SteemServer longestServer = null;
			for (SteemServer server : upServers) {
				long interval = Instant.parse(server.lastChecked)
						.getEpochSecond()
						- Instant.parse(server.lastChanged)
								.getEpochSecond();
				if (interval > longest) {
					longest = interval;
					longestServer = server;
				}
			}
			return longestServer;
		}
	}

	@Override
	public SteemServer findRandom() {
		if (servers == null) {
			return find().findRandom();
		} else {
			Collections.shuffle(upServers);
			return upServers.get(0);
		}
	}

	@Override
	public List<SteemServer> getDownServers() {
		return downServers;
	}

	@Override
	public String getJson() {
		return json;
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
	public List<SteemServer> getUpServers() {
		return upServers;
	}

	@Override
	public String getUrl() {
		return url;
	}

	private String retrieveJson() {
		try {
			CheckQueryUtil.checkNotNullNorEmpty(url, "url");
			Connection connection = Jsoup.connect(url)
					.ignoreContentType(true);
			CheckQueryUtil.checkNotMinus(timeout, "timeout");
			connection.timeout(timeout);
			return connection.execute()
					.body();
		} catch (IOException e) {
			throw new SteemServerFinderException("retrieveJson", e);
		}
	}

}
