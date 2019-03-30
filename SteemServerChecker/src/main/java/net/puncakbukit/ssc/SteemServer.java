package net.puncakbukit.ssc;

import java.util.Objects;

/**
 * Data structure to hold STEEM server status.
 * 
 * 
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
public class SteemServer {

	public static final String UP = "UP";
	public static final String DOWN = "DOWN";

	public String server;
	public String ranBy;
	public String status;
	public String cause;
	public String lastChanged;
	public String lastChecked;

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof SteemServer)) {
			return false;
		}
		SteemServer s = (SteemServer) o;
		return (s.server == null ? server == null ? true : false : s.server.equals(server))
				&& (s.ranBy == null ? ranBy == null ? true : false : s.ranBy.equals(ranBy))
				&& (s.status == null ? status == null ? true : false : s.status.equals(status))
				&& (s.cause == null ? cause == null ? true : false : s.cause.equals(cause))
				&& (s.lastChanged == null ? lastChanged == null ? true : false : s.lastChanged.equals(lastChanged))
				&& (s.lastChecked == null ? lastChecked == null ? true : false : s.lastChecked.equals(lastChecked));
	}

	@Override
	public int hashCode() {
		return Objects.hash(server, ranBy, status, cause, lastChanged, lastChecked);
	}

	@Override
	public String toString() {
		return "{\"server\":\"" + server + "\", \"ranBy\":\"" + ranBy + "\", \"status\":\"" + status
				+ "\", \"cause\":\"" + cause + "\", \"lastChanged\":\"" + lastChanged + "\", \"lastChecked\":\""
				+ lastChecked + "\"}";
	}

}
