package com.example.podcastplayer.api;

public class LoginCommand {
    public String email;
    public String password;

    public LoginCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
