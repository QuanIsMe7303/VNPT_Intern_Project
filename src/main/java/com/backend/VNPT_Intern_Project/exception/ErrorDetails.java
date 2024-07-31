package com.backend.vnptproject.exception;

import java.util.Date;

public class ErrorDetails {
    private String error;
    private String message;
    private int statusCode;
    private Date timestamp;

    public ErrorDetails(String error, String message, int statusCode, Date timestamp) {
        super();
        this.error = error;
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
