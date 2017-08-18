package com.n26.exception;

/**
 * The custom exception class for handling Internal exceptions.
 * 
 * @author Tanya
 * 
 */
public class InternalErrorException extends Exception {

    private static final long serialVersionUID = 1960250151257031680L;

    /**
     * Constructor. Initializes this exception as a wrapper for another exception, with an explanatory message.
     * 
     * @param msg the explanatory message
     * @param wrappedException the exception being wrapped
     */
    public InternalErrorException(String msg, Throwable wrappedException) {
        super(msg, wrappedException);
    }

    /**
     * Constructor. Initializes this exception as a wrapper for another exception.
     * 
     * @param wrappedException the exception being wrapped
     */
    public InternalErrorException(Throwable wrappedException) {
        super(wrappedException);
    }

    /**
     * 
     * @param msg
     */
    public InternalErrorException(String msg) {
        super(msg);
    }

}
