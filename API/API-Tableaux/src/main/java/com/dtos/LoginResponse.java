package com.dtos;

public class LoginResponse {
    private String token;

    private long expiresIn;

    private int isAdmin;

    private long cptId;

    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public LoginResponse setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }

    public long getCptId() {
        return cptId;
    }

    public LoginResponse setCptId(long cptId) {
        this.cptId = cptId;
        return this;
    }
}