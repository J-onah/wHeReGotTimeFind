package com.example.wheregottimefind.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String email;
    private String tempAuthToken;
    private int id;

    String getTempAuthToken() {
        return tempAuthToken;
    }

    LoggedInUserView(String displayName, String email, String tempAuthToken, int id) {
        this.displayName = displayName;
        this.email = email;
        this.tempAuthToken = tempAuthToken;
        this.id = id;
    }

    String getDisplayName() {
        return displayName;
    }

    String getEmail() { return email; }

    int getId() { return id; }
}