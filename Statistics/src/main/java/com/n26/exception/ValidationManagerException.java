package com.n26.exception;

public class ValidationManagerException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
     * Constructor. Initializes this exception as a wrapper for another exception, with an explanatory message.
     * 
     * @param msg the explanatory message
     * @param wrappedException the exception being wrapped
     */
    public ValidationManagerException(String msg, Throwable wrappedException) {
        super(msg, wrappedException);
    }

    /**
     * Constructor. Initializes this exception as a wrapper for another exception.
     * 
     * @param wrappedException the exception being wrapped
     */
    public ValidationManagerException(Throwable wrappedException) {
        super(wrappedException);
    }

    /**
     * @param msg
     */
    public ValidationManagerException(String msg) {
        super(msg);
    }

}


