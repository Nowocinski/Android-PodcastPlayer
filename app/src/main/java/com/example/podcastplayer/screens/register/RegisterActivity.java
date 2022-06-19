package com.example.podcastplayer.screens.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.podcastplayer.App;
import com.example.podcastplayer.R;
import com.example.podcastplayer.api.RegistrationCommand;
import com.example.podcastplayer.screens.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_KEY = "LOG_KEY@" + RegistrationCommand.class.getSimpleName();
    private RegisterManager registerManager;
    @BindView(R.id.firstNameEditText)
    EditText firstNameEditText;
    @BindView(R.id.lastNameEditText)
    EditText lastNameEditText;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        // TODO: W przyszłości tworzenie obiektu powinno odbywać się przy pomocy dependency injection.
        this.registerManager = ((App)this.getApplication()).getRegisterManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.registerManager.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.registerManager.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.register, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_register) {
            this.tryToRegister();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void tryToRegister() {
        String firstName = this.firstNameEditText.getText().toString();
        String lastName = this.lastNameEditText.getText().toString();
        String email = this.emailEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();
        boolean hasErrors = false;

        if (firstName.isEmpty()) {
            this.firstNameEditText.setError(getString(R.string.this_field_cant_be_empty));
            hasErrors = true;
        }
        if (lastName.isEmpty()) {
            this.lastNameEditText.setError(getString(R.string.this_field_cant_be_empty));
            hasErrors = true;
        }
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
            this.registerManager.register(
                    this.firstNameEditText.getText().toString(),
                    this.lastNameEditText.getText().toString(),
                    this.emailEditText.getText().toString(),
                    this.passwordEditText.getText().toString());
        }
    }

    public void registerSuccess() {
        this.startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        Toast.makeText(RegisterActivity.this, R.string.registered_account, Toast.LENGTH_LONG).show();
        finish();
    }

    public void showError(String error) {
        Toast.makeText(RegisterActivity.this, "message: " + error, Toast.LENGTH_LONG).show();
    }

    public void showProgress(boolean progress) {
        // TODO: Dodać blokowanie przycisk, który jest w toolbarze.
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}