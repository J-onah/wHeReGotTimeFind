package com.example.wheregottimefind.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.wheregottimefind.data.LoginRepository;
import com.example.wheregottimefind.data.Result;
import com.example.wheregottimefind.data.model.LoggedInUser;
import com.example.wheregottimefind.R;
import com.example.wheregottimefind.data.pojo.User;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        loginRepository.login(username, password, new OnLoginListener(){
            @Override
            public void onLoginResult(Result result) {
                System.out.println("onLoginResult!! " + result);
                if (result instanceof Result.Success) {
                    User data = ((Result.Success<User>) result).getData();
                    loginResult.setValue(new LoginResult(
                            new LoggedInUserView(
                                    String.valueOf(data.getDisplay_name()), data.getName(),
                                    data.getTemp_auth_token(), data.getId()
                            )
                    ));
                } else if (result instanceof Result.NotRegistered) {
                    loginResult.setValue(new LoginResult(R.string.not_registered));
                } else {
                    loginResult.setValue(new LoginResult(R.string.login_failed));
                }
            }
        });
    }

    public void login(String username, String password, String verificationCode) {
        if (verificationCode.equals("123456")) {
            loginRepository.register(username, password, new OnLoginListener(){
                @Override
                public void onLoginResult(Result result) {
                    if (result instanceof Result.Success) {
                        login(username, password);
                    }
                }
            });
        } else {
            loginResult.setValue(new LoginResult(R.string.wrong_verification));
        }
//        // can be launched in a separate asynchronous job
//        Result<LoggedInUser> result = loginRepository.register(username, password, verificationCode);
//
//        if (result instanceof Result.Success) {
//            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
//            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName(), data.getEmail())));
//        } else if (result instanceof Result.NotRegistered) {
//            loginResult.setValue(new LoginResult(R.string.not_registered));
//        } else if (result instanceof Result.WrongVerification) {
//            loginResult.setValue(new LoginResult(R.string.wrong_verification));
//        } else {
//            loginResult.setValue(new LoginResult(R.string.login_failed));
//        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return true;
//        return password != null && password.trim().length() > 5;
    }
}