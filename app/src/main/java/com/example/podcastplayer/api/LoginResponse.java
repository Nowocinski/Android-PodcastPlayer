package com.example.podcastplayer.api;

public class LoginResponse {
    public String token;
    public String id;
    public String firstName;
    public String lastName;
    public String email;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
