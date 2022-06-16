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
    private Call<Void> call;

    public RegisterManager(UserStorage userStorage, PodcastApi podcastApi, Retrofit retrofit) {
        this.userStorage = userStorage;
        this.podcastApi = podcastApi;
        this.retrofit = retrofit;
    }

    public void onAttach(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
        this.updateProgress();
    }

    public void onStop() {
        this.registerActivity = null;
    }

    public void register(String firstName, String lastName, String email, String password) {
        RegistrationCommand registrationCommand = new RegistrationCommand(email, email, password, firstName, lastName);
        if (this.call == null) {
            this.call = this.podcastApi.postRegistration(registrationCommand);
            this.updateProgress();
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    RegisterManager.this.call = null;
                    updateProgress();
                    if (response.isSuccessful()) {
                        if (registerActivity != null) {
                            registerActivity.registerSuccess();
                        }
                    } else {
                        try {
                            if (registerActivity != null) {
                                // TODO: Poprawić zwracany błąd przez serwer.
                                registerActivity.showError(response.errorBody().string());
                            }
                        } catch (Exception e) {
                            Log.d(LOG_KEY, "e.getMessage(): " + e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    RegisterManager.this.call = null;
                    updateProgress();
                    if (registerActivity != null) {
                        // TODO: Poprawić zwracany błąd przez serwer.
                        registerActivity.showError(t.getLocalizedMessage());
                    }
                }
            });
        }
    }

    private void updateProgress() {
        if (this.registerActivity != null) {
            this.registerActivity.showProgress(this.call != null);
        }
    }
}
