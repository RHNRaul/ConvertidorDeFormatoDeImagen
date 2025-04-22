package rhn.exceptions;

public class ConvertidorException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConvertidorException(String message) {
		super(message);
	}

	public ConvertidorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConvertidorException(Throwable cause) {
		super(cause);
	}
	
}
