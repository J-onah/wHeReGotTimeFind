package com.example.wheregottimefind.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String email;
    private String tempAuthToken;

    String getTempAuthToken() {
        return tempAuthToken;
    }

    LoggedInUserView(String displayName, String email, String tempAuthToken) {
        this.displayName = displayName;
        this.email = email;
        this.tempAuthToken = tempAuthToken;
    }

    String getDisplayName() {
        return displayName;
    }

    String getEmail() { return email; }
}