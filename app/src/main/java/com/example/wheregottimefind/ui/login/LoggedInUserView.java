package com.example.wheregottimefind.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String userid;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, String userid) {
        this.displayName = displayName;
        this.userid = userid;
    }

    String getDisplayName() {
        return displayName;
    }

    String getUserid() { return userid; }
}