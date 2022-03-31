package com.example.wheregottimefind.data;

import com.example.wheregottimefind.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        String displayName;

        try {
            // TODO: handle loggedInUser authentication
            // Get front part of email
            int atIndex = username.indexOf('@');
            if (atIndex == -1) {
                throw new IllegalAccessException("No @ in username");
            } else {
                displayName = capitaliseName(username.substring(0, atIndex));
            }

            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            displayName);
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    private static String capitaliseName(String emailFront){
        String words[] = emailFront.split("_");
        String res = "";

        for (String w: words){
            String first = w.substring(0,1);
            String others = w.substring(1);
            res += first.toUpperCase() + others + " ";
        }
        return res.trim();
    }
}