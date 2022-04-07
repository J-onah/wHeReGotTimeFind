package com.example.wheregottimefind.data;

import android.util.Log;

import com.example.wheregottimefind.backendAPI.AsyncUpdate;
import com.example.wheregottimefind.backendAPI.BackendApi;
import com.example.wheregottimefind.data.model.LoggedInUser;
import com.example.wheregottimefind.data.pojo.User;
import com.example.wheregottimefind.ui.login.OnLoginListener;

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

    public void login(String username, String password, OnLoginListener loginListener) {
        String displayName;
        String hashedPasswordString;

        try {
            hashedPasswordString = hashPassword(password);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Could not hash password!" + e);
            return;
        }

        try {
            // Get front part of email
            int atIndex = username.indexOf('@');
            if (atIndex == -1) {
                throw new IllegalAccessException("No @ in username");
            } else if (username.endsWith("sutd.edu.sg")) {

                // Call login api
                BackendApi.login(username, hashedPasswordString, user -> {
//                    System.out.println("-----login user: " + user);
                    if (user == null) {
                        loginListener.onLoginResult(new Result.Error(new IOException("Null result")));
                    } else if (user.getLogin_error() == null || user.getLogin_error().isEmpty()) {
                        loginListener.onLoginResult(new Result.Success<>(user));
                    } else if (user.getLogin_error().equals("user not found")) {
                        loginListener.onLoginResult(new Result.NotRegistered(new IOException("User not registered!")));
                    } else {
                        loginListener.onLoginResult(new Result.Error(new IOException(user.getLogin_error())));
                    }
                });
            } else {
                throw new IllegalAccessException("Not SUTD Email");
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            loginListener.onLoginResult(new Result.Error(e));
        }
    }

    public void register(String username, String password, OnLoginListener loginListener) {
        String displayName;
        String hashedPasswordString;


        try {
            hashedPasswordString = hashPassword(password);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Could not hash password!" + e);
            return;
        }

        try {
            // Call register api
            BackendApi.register(username, hashedPasswordString, user -> {
//                System.out.println("-----register user: " + user);
                if (user == null) {
                    loginListener.onLoginResult(new Result.Error(new IOException("Null result")));
                } else if (user.getId() != 0) {
                    loginListener.onLoginResult(new Result.Success<>(user));
                } else {
                    loginListener.onLoginResult(new Result.Error(new IOException(user.getLogin_error())));
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            loginListener.onLoginResult(new Result.Error(e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    // Hash password (https://www.baeldung.com/java-password-hashing)
    private String hashPassword(String password) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes(StandardCharsets.UTF_8));

//        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.US_ASCII));
        String hashedPasswordString = new String(md.digest());
        return hashedPasswordString;
    }
}