package com.example.podcastplayer;

import android.app.Application;
import android.preference.PreferenceManager;

import com.example.podcastplayer.api.PodcastApi;
import com.example.podcastplayer.screens.login.LoginManager;
import com.example.podcastplayer.screens.register.RegisterManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private LoginManager loginManager;
    private RegisterManager registerManager;
    private UserStorage userStorage;
    private Retrofit retrofit;
    private PodcastApi podcastApi;

    @Override
    public void onCreate() {
        super.onCreate();

        Gson gson = new GsonBuilder().serializeNulls().create();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5156/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        this.podcastApi = retrofit.create(PodcastApi.class);

        this.userStorage = new UserStorage(PreferenceManager.getDefaultSharedPreferences(this));
        this.loginManager = new LoginManager(this.userStorage, this.podcastApi, this.retrofit);
        this.registerManager = new RegisterManager(this.userStorage, this.podcastApi, this.retrofit);
    }

    public LoginManager getLoginManager() {
        return this.loginManager;
    }

    public UserStorage getUserStorage() {
        return this.userStorage;
    }

    public RegisterManager getRegisterManager() {
        return this.registerManager;
    }

    public Retrofit getRetrofit() {
        return this.retrofit;
    }

    public PodcastApi getPodcastApi() {
        return this.podcastApi;
    }
}
