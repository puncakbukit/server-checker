package net.puncakbukit.ssf;

/**
 * The only exception used in this library.
 * It extends RuntimeException so not needed to be catched.
 * User is free whether to catch it or not.
 * 
 * @author <a href="http://steemit.com/@puncakbukit">puncakbukit</a>
 *
 */
public class SteemServerFinderException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3315791085575605856L;

	public SteemServerFinderException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SteemServerFinderException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public SteemServerFinderException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public SteemServerFinderException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public SteemServerFinderException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
