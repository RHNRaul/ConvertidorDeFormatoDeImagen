package rhn.exceptions;

public class BuscadorException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8828112998682330998L;

	public BuscadorException(String message) {
		super(message);
	}

	public BuscadorException(String message, Throwable cause) {
		super(message, cause);
	}

	public BuscadorException(Throwable cause) {
		super(cause);
	}

}
