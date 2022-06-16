package com.example.podcastplayer.screens.register;

import android.util.Log;

import com.example.podcastplayer.UserStorage;
import com.example.podcastplayer.api.PodcastApi;
import com.example.podcastplayer.api.RegistrationCommand;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterManager {
    private static final String LOG_KEY = "LOG_KEY@" + RegistrationCommand.class.getSimpleName();
    private RegisterActivity registerActivity;
    private final UserStorage userStorage;
    private final PodcastApi podcastApi;
    private final Retrofit retrofit;

    public RegisterManager(UserStorage userStorage, PodcastApi podcastApi, Retrofit retrofit) {
        this.userStorage = userStorage;
        this.podcastApi = podcastApi;
        this.retrofit = retrofit;
    }

    public void register(String firstName, String lastName, String email, String password) {
        Log.d(LOG_KEY, "register working 1");
        RegistrationCommand registrationCommand = new RegistrationCommand(email, email, password, firstName, lastName);
        Log.d(LOG_KEY, "register working 2");
        Log.d(LOG_KEY, registrationCommand.toString());
        Call<Void> call = this.podcastApi.postRegistration(registrationCommand);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d(LOG_KEY, "register");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(LOG_KEY, "register error");
            }
        });
    }
}
