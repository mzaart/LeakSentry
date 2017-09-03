package com.mzaart.leaksentry.aquery.exceptions;

public class ViewNotFoundException extends RuntimeException {

    public ViewNotFoundException() {
        super("View not found. The id used doesn't correspond to any view");
    }
}
