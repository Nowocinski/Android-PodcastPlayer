package com.example.podcastplayer.screens.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.podcastplayer.App;
import com.example.podcastplayer.MainActivity;
import com.example.podcastplayer.R;
import com.example.podcastplayer.screens.register.RegisterActivity;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private static final String LOG_KEY = "LOG_KEY@" + LoginActivity.class.getSimpleName();
    private LoginManager loginManager;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.loginButton)
    TextView loginButton;
    @BindView(R.id.registerButton)
    TextView registerButton;
    @BindView(R.id.emailTextInputLayout)
    TextInputLayout emailTextInputLayout;
    @BindView(R.id.passwordTextInputLayout)
    TextInputLayout passwordTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        // TODO: W przyszłości tworzenie obiektu powinno odbywać się przy pomocy dependency injection.
        this.loginManager = ((App)this.getApplication()).getLoginManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.loginManager.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.loginManager.onStop();
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
            this.emailTextInputLayout.setError(getString(R.string.this_field_cant_be_empty));
            hasErrors = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.emailTextInputLayout.setError(getString(R.string.not_an_email));
            hasErrors = true;
        } else {
            this.emailTextInputLayout.setError(null);
        }
        if (password.isEmpty()) {
            this.passwordTextInputLayout.setError(getString(R.string.this_field_cant_be_empty));
            hasErrors = true;
        } else if (password.length() < 6) {
            this.passwordTextInputLayout.setError(getString(R.string.password_too_short));
            hasErrors = true;
        } else {
            this.passwordTextInputLayout.setError(null);
        }

        if(!hasErrors) {
            this.loginManager.login(email, password);
        }
    }

    public void loginSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    public void showError(String error) {
        Toast.makeText(LoginActivity.this, "message: " + error, Toast.LENGTH_LONG).show();
    }

    public void showProgress(boolean progress) {
        this.loginButton.setEnabled(!progress);
    }
}