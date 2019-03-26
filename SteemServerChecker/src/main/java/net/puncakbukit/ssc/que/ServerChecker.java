package net.puncakbukit.ssc.que;

/**
 * Checker to check STEEM server whether it is UP or DOWN.
 * 
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
public interface ServerChecker {

	/**
	 * Builder of the checker.
	 */
	public interface Builder {

		ServerChecker build();

		Builder withAccount(String account);

		Builder withCause(String cause);

		Builder withProtocol(String protocol);

		Builder withServer(String server);

		Builder withStatus(String status);

		Builder withTimeout(int timeout);

	}

	static final String UP = "UP";

	static final String DOWN = "DOWN";

	/**
	 * Checks the server.
	 * 
	 * @return this checker
	 */
	ServerChecker check();

	/**
	 * STEEM account to use.
	 * 
	 * @return
	 */
	String getAccount();

	/**
	 * Cause of DOWN.
	 * 
	 * @return
	 */
	String getCause();

	/**
	 * Protocol of the server.
	 * 
	 * @return
	 */
	String getProtocol();

	/**
	 * Server name.
	 * 
	 * @return
	 */
	String getServer();

	/**
	 * Status of server: UP or DOWN.
	 * 
	 * @return
	 */
	String getStatus();

	/**
	 * Timeout when connecting the server.
	 * 
	 * @return
	 */
	int getTimeout();

}
