package com.example.wheregottimefind.data;

import android.util.Log;

import com.example.wheregottimefind.backendAPI.BackendApi;
import com.example.wheregottimefind.data.model.LoggedInUser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private static final String TAG = "login_data_source";

    public Result<LoggedInUser> login(String username, String password) {
        String displayName;
        String hashedPasswordString;

        // Hash password (https://www.baeldung.com/java-password-hashing)
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);

            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            hashedPasswordString = new String(hashedPassword, StandardCharsets.UTF_8);
            System.out.println("password:hashed " + password + ":" + hashedPasswordString);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Could not hash password!" + e);
        }

        // Registration logic
//        if (username.equals("new_user@sutd.edu.sg")) {
//            return new Result.NotRegistered(new IllegalAccessException("User not registered!"));
//        }

        try {
            // TODO: handle loggedInUser authentication
            // Get front part of email
            int atIndex = username.indexOf('@');
            if (atIndex == -1) {
                throw new IllegalAccessException("No @ in username");
            } else if (username.endsWith("sutd.edu.sg")) {
                displayName = capitaliseName(username.substring(0, atIndex));

                // Call login api
                BackendApi.get
                LoggedInUser fakeUser =
                        new LoggedInUser(
                                username,
                                displayName);

                return new Result.Success<>(fakeUser);
            } else {
                throw new IllegalAccessException("Does not end with sutd.edu.sg");
            }

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<LoggedInUser> register(String username, String password, String verificationCode) {
        String displayName;

        if ("123456".equals(verificationCode)) {
            System.out.println("register call login");
            try {
                // TODO: handle loggedInUser authentication
                // Get front part of email
                int atIndex = username.indexOf('@');
                if (atIndex == -1) {
                    throw new IllegalAccessException("No @ in username");
                } else if (username.endsWith("sutd.edu.sg")) {
                    displayName = capitaliseName(username.substring(0, atIndex));
                } else {
                    throw new IllegalAccessException("Does not end with sutd.edu.sg");
                }

                LoggedInUser fakeUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                displayName);

                return new Result.Success<>(fakeUser);
            } catch (Exception e) {
                return new Result.Error(new IOException("Error logging in", e));
            }
        } else {
            return new Result.WrongVerification(new IOException("Error logging in"));
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