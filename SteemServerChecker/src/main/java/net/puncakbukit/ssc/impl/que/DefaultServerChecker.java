package net.puncakbukit.ssc.impl.que;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import net.puncakbukit.ssc.SteemServerCheckerException;
import net.puncakbukit.ssc.que.ServerChecker;
import net.puncakbukit.util.que.CheckQueryUtil;

/**
 * Default implementation of ServerChecker interface.
 * 
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
public class DefaultServerChecker implements ServerChecker {

	public static class Builder implements ServerChecker.Builder {

		private String protocol;
		private String server;
		private int timeout;
		private String account;
		private String status;
		private String cause;

		@Override
		public ServerChecker build() {
			return new DefaultServerChecker(protocol, server, timeout, account, status, cause);
		}

		@Override
		public ServerChecker.Builder withAccount(String account) {
			this.account = account;
			return this;
		}

		@Override
		public ServerChecker.Builder withCause(String cause) {
			this.cause = cause;
			return this;
		}

		@Override
		public ServerChecker.Builder withProtocol(String protocol) {
			this.protocol = protocol;
			return this;
		}

		@Override
		public ServerChecker.Builder withServer(String server) {
			this.server = server;
			return this;
		}

		@Override
		public ServerChecker.Builder withStatus(String status) {
			this.status = status;
			return this;
		}

		@Override
		public ServerChecker.Builder withTimeout(int timeout) {
			this.timeout = timeout;
			return this;
		}
	}

	private Logger log = LoggerFactory.getLogger(DefaultServerChecker.class);
	private final String protocol;
	private final String server;
	private final int timeout;
	private final String account;
	private final String status;

	private final String cause;

	private DefaultServerChecker(String protocol, String server, int timeout, String account, String status,
			String cause) {
		super();
		this.protocol = protocol;
		this.server = server;
		this.timeout = timeout;
		this.account = account;
		this.status = status;
		this.cause = cause;
	}

	@Override
	public ServerChecker check() {
		try {
			SteemJConfig myConfig = SteemJConfig.getInstance();
			myConfig.setEndpointURIs(new ArrayList<Pair<URI, Boolean>>());
			CheckQueryUtil.checkNotNullNorEmpty(protocol, "protocol");
			CheckQueryUtil.checkNotNullNorEmpty(server, "server");
			myConfig.addEndpointURI(new URI(protocol + "://" + server), true);
			CheckQueryUtil.checkNotMinus(timeout, "timeout");
			myConfig.setResponseTimeout(timeout);
			CheckQueryUtil.checkNotNullNorEmpty(account, "account");
			myConfig.setDefaultAccount(new AccountName(account));
			String status = ServerChecker.DOWN;
			String cause = "";
			try {
				SteemJ steemJ = new SteemJ();
				SignedBlockWithInfo block = steemJ.getBlock(steemJ.getDynamicGlobalProperties()
						.getLastIrreversibleBlockNum());
				if (block != null && block.getBlockId() != null) {
					status = ServerChecker.UP;
				} else {
					cause = "Block or block id is null.";
				}
			} catch (SteemCommunicationException | SteemResponseException e) {
				// Logs and resumes
				log.error("check", e);
				cause = e.getCause()
						.toString();
			}
			return new DefaultServerChecker(protocol, server, timeout, account, status, cause);
		} catch (URISyntaxException e) {
			throw new SteemServerCheckerException("check", e);
		}
	}

	@Override
	public String getAccount() {
		return account;
	}

	@Override
	public String getCause() {
		return cause;
	}

	@Override
	public String getProtocol() {
		return protocol;
	}

	@Override
	public String getServer() {
		return server;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public int getTimeout() {
		return timeout;
	}

}
