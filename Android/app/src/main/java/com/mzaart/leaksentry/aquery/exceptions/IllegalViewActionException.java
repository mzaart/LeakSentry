package com.mzaart.leaksentry.aquery.exceptions;

public class IllegalViewActionException extends RuntimeException {

    public IllegalViewActionException() {
        super("Can't perform action on view.");
    }
}
