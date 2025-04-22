package rhn.exceptions;

public class SalidaException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SalidaException(String message) {
		super(message);
	}

	public SalidaException(String message, Throwable cause) {
		super(message, cause);
	}

	public SalidaException(Throwable cause) {
		super(cause);
	}

}
