package net.puncakbukit.ssc;

/**
 * Data structure to hold STEEM server status.
 * 
 * 
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
public class SteemServer {
	public String server;
	public String ranBy;
	public String status;
	public String cause;
	public String lastChanged;
	public String lastChecked;

	@Override
	public String toString() {
		return "{\"server\":\"" + server + "\", \"ranBy\":\"" + ranBy + "\", \"status\":\"" + status
				+ "\", \"cause\":\"" + cause + "\", \"lastChanged\":\"" + lastChanged + "\", \"lastChecked\":\""
				+ lastChecked + "\"}";
	}

}
