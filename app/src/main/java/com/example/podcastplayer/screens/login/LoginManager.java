package com.example.podcastplayer.screens.login;

import android.util.Log;

import com.example.podcastplayer.UserStorage;
import com.example.podcastplayer.api.LoginCommand;
import com.example.podcastplayer.api.LoginResponse;
import com.example.podcastplayer.api.PodcastApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginManager {
    private static final String LOG_KEY = "LOG_KEY@" + LoginActivity.class.getSimpleName();
    private LoginActivity loginActivity;
    private final UserStorage userStorage;
    private Call<LoginResponse> call;
    private Retrofit retrofit;
    private PodcastApi podcastApi;

    public LoginManager(UserStorage userStorage, PodcastApi podcastApi, Retrofit retrofit) {
        this.userStorage = userStorage;
        this.podcastApi = podcastApi;
        this.retrofit = retrofit;
    }

    public void onAttach(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        this.updateProgress();
    }

    public void onStop() {
        this.loginActivity = null;
    }

    public void login(String email, String password) {
        if (this.call == null) {
            this.call = podcastApi.postLogin(new LoginCommand(email, password));
            this.updateProgress();
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginManager.this.call = null;
                    updateProgress();
                    if (response.isSuccessful()) {
                        Log.d(LOG_KEY, "Response code: " + response.code());

                        LoginResponse loginResponse = response.body();
                        Log.d(LOG_KEY, "Response: " + loginResponse.toString());
                        userStorage.login(loginResponse);
                        if (loginActivity != null) {
                            loginActivity.loginSuccess();
                        }
                    } else {
                        try {
                            if (loginActivity != null) {
                                // TODO: Poprawić zwracany błąd przez serwer.
                                loginActivity.showError(response.errorBody().string());
                            }
                        } catch (Exception e) {
                            Log.d(LOG_KEY, "e.getMessage(): " + e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    LoginManager.this.call = null;
                    updateProgress();
                    if (loginActivity != null) {
                        loginActivity.showError(t.getLocalizedMessage());
                    }
                }
            });
        }
    }

    private void updateProgress() {
        if (this.loginActivity != null) {
            this.loginActivity.showProgress(this.call != null);
        }
    }
}
