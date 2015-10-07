package edu.sjsu.cmpe275.lab1.exception;

/**
 * UnauthorizedException
 * Created on September 29th, 2015
 * @author Ruchi Agarwal
 */
public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** Constructor. */
    public UnauthorizedException() {}

    /** Parameterized constructor. */
    public UnauthorizedException(String arg0) {
        super(arg0);
    }

}
