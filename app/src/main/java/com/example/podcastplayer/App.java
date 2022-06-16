package com.example.podcastplayer;

import android.app.Application;
import android.preference.PreferenceManager;

public class App extends Application {
    private UserManager userManager;
    private UserStorage userStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        this.userStorage = new UserStorage(PreferenceManager.getDefaultSharedPreferences(this));
        this.userManager = new UserManager(userStorage);
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
