package com.backend.VNPT_Intern_Project.exception;

import lombok.Data;

import java.util.Date;

@Data
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
}
