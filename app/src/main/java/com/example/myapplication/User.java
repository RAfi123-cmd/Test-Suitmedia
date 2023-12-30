package com.example.myapplication;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String avatarUrl;

    public User(String firstName, String lastName, String email, String avatarUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
