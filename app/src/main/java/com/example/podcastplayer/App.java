package com.example.podcastplayer;

import android.app.Application;
import android.preference.PreferenceManager;

import com.example.podcastplayer.screens.login.LoginManager;

public class App extends Application {
    private LoginManager loginManager;
    private UserStorage userStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        this.userStorage = new UserStorage(PreferenceManager.getDefaultSharedPreferences(this));
        this.loginManager = new LoginManager(userStorage);
    }

    public LoginManager getUserManager() {
        return this.loginManager;
    }

    public UserStorage getUserStorage() {
        return this.userStorage;
    }
}
