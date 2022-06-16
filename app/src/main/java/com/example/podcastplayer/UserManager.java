package com.example.podcastplayer;

import android.util.Log;

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

public class UserManager {
    private static final String LOG_KEY = "LOG_KEY@" + LoginActivity.class.getSimpleName();
    private LoginActivity loginActivity;
    private final UserStorage userStorage;
    private Call<LoginResponse> call;

    public UserManager(UserStorage userStorage) {
        this.userStorage = userStorage;
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
            Gson gson = new GsonBuilder().serializeNulls().create();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:5156/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
            PodcastApi podcastApi = retrofit.create(PodcastApi.class);

            this.call = podcastApi.postLogin(new LoginCommand(email, password));
            this.updateProgress();
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    UserManager.this.call = null;
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
                    UserManager.this.call = null;
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
