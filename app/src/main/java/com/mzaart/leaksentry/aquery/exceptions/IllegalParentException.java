package com.mzaart.leaksentry.aquery.exceptions;

public class IllegalParentException extends RuntimeException {

    public IllegalParentException() {
        super("The view's parent isn't a view.");
    }
}
