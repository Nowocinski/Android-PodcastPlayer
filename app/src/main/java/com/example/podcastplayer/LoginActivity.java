package com.example.podcastplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
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

    }
}