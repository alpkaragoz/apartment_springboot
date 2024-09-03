package com.borsa.apartment.exception;

public class FavoriteAlreadyExistsException extends RuntimeException{
    public FavoriteAlreadyExistsException(String message) {
        super(message);
    }
}
