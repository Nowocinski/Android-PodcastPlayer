package com.example.podcastplayer;

import android.content.SharedPreferences;

import com.example.podcastplayer.api.LoginResponse;

public class UserStorage {
    public static final String TOKEN = "token";
    public static final String ID = "id";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    private final SharedPreferences sharedPreferences;

    public UserStorage(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void login(LoginResponse loginResponse) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, loginResponse.token);
        editor.putString(ID, loginResponse.id);
        editor.putString(FIRST_NAME, loginResponse.firstName);
        editor.putString(LAST_NAME, loginResponse.lastName);
        editor.putString(EMAIL, loginResponse.email);
        editor.apply();
    }

    public boolean hasToLogin() {
        return this.sharedPreferences.getString(this.TOKEN, "").isEmpty();
    }

    public void logout() {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getFullName() {
        return String.format("%s %s",
                this.sharedPreferences.getString(this.FIRST_NAME, ""),
                this.sharedPreferences.getString(this.LAST_NAME, ""));
    }

    public String getEmail() {
        return this.sharedPreferences.getString(this.EMAIL, "");
    }
}
