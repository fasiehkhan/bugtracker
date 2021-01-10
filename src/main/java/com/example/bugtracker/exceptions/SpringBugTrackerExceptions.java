package com.example.bugtracker.exceptions;

public class SpringBugTrackerExceptions extends RuntimeException{
    public SpringBugTrackerExceptions(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringBugTrackerExceptions(String exMessage) {
        super(exMessage);
    }
}
