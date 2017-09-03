package com.mzaart.leaksentry.addSensor;

public class Response {

    public boolean succeeded;
    public String error;

    public Response(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public Response(boolean succeeded, String error) {
        this.succeeded = succeeded;
        this.error = error;
    }

    // empty constructor for Gson
    public Response() {}

    public boolean isSuccessful() {
        return succeeded;
    }
}
