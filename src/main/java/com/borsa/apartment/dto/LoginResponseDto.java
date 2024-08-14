package com.borsa.apartment.dto;

public class LoginResponseDto {
    private String token;
    private String message;

    public LoginResponseDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
