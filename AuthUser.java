package com.example.retrofitauthentication;

import com.google.gson.annotations.SerializedName;

public class AuthUser {

    private String token;

    public AuthUser(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
