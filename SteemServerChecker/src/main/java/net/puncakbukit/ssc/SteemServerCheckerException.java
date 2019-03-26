package net.puncakbukit.ssc;

/**
 * The only exception used in this application.
 * It extends RuntimeException such that not needed to be catched.
 * Users are free whether to catch it or not.
 * 
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
public class SteemServerCheckerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1769687610369542219L;

	public SteemServerCheckerException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SteemServerCheckerException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public SteemServerCheckerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public SteemServerCheckerException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public SteemServerCheckerException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
