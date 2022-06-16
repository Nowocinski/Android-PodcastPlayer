package com.example.podcastplayer.screens.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.podcastplayer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

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
            // TODO: 2022-06-15
        }
    }
}