package com.example.api.exception;

public class InvalidEmailTokenException extends Exception {
    public InvalidEmailTokenException() {
        super("Email token is invalid!");
    }
}
