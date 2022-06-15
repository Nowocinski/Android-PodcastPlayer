package com.example.podcastplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import com.example.podcastplayer.api.LoginCommand;
import com.example.podcastplayer.api.LoginResponse;
import com.example.podcastplayer.api.PodcastApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    public static final String LOG_KEY = "LOG_KEY@" + LoginActivity.class.getSimpleName();
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.registerButton)
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.registerButton)
    public void registerClicked() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.loginButton)
    public void loginClicked() {
        String email = this.emailEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();
        boolean hasErrors = false;
        if (email.isEmpty()) {
            this.emailEditText.setError(getString(R.string.this_field_cant_be_empty));
            hasErrors = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.emailEditText.setError(getString(R.string.not_an_email));
            hasErrors = true;
        }
        if (password.isEmpty()) {
            this.passwordEditText.setError(getString(R.string.this_field_cant_be_empty));
            hasErrors = true;
        } else if (password.length() < 6) {
            this.passwordEditText.setError(getString(R.string.password_too_short));
            hasErrors = true;
        }

        if(!hasErrors) {
            this.login(email, password);
        }
    }

    private void login(String email, String password) {
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
        Call<LoginResponse> call = podcastApi.postLogin(new LoginCommand(email, password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d(LOG_KEY, "Response code: " + response.code());

                LoginResponse loginResponse = response.body();
                Log.d(LOG_KEY, "Email: " + loginResponse.email);
                Log.d(LOG_KEY, "FirstName: " + loginResponse.firstName);
                Log.d(LOG_KEY, "LastName: " + loginResponse.lastName);
                Log.d(LOG_KEY, "Id: " + loginResponse.id);
                Log.d(LOG_KEY, "Token: " + loginResponse.token);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d(LOG_KEY, t.getMessage());
            }
        });
    }
}