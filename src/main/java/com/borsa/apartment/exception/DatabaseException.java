package com.borsa.apartment.exception;

public class DatabaseException extends RuntimeException{
    public DatabaseException(String message) {
        super(message);
    }
}
