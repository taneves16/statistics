package com.n26.exception;

/**
 * Excpetion to be thrown if the timestamp is of past
 * 
 * @author Tanya
 *
 */
public class InvalidTimeStampException extends Exception {

	private static final long serialVersionUID = 8322219333729456358L;
	
	/**
     * Constructor. Initializes this exception as a wrapper for another exception, with an explanatory message.
     * 
     * @param msg the explanatory message
     * @param wrappedException the exception being wrapped
     */
    public InvalidTimeStampException(String msg, Throwable wrappedException) {
        super(msg, wrappedException);
    }

    /**
     * Constructor. Initializes this exception as a wrapper for another exception.
     * 
     * @param wrappedException the exception being wrapped
     */
    public InvalidTimeStampException(Throwable wrappedException) {
        super(wrappedException);
    }

    /**
     * @param msg
     */
    public InvalidTimeStampException(String msg) {
        super(msg);
    }

}


