package com.example.wheregottimefind.ui.login;

import android.app.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.wheregottimefind.MainActivity;
import com.example.wheregottimefind.R;
import com.example.wheregottimefind.ui.login.LoginViewModel;
import com.example.wheregottimefind.ui.login.LoginViewModelFactory;
import com.example.wheregottimefind.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private final String TAG = "login_activity_tag";
    SharedPreferences sharedPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final EditText verificationCodeEditText = binding.verificationCode;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;
        sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // Check SharedPreferences and auto login
        String display_name = sharedPref.getString(getString(R.string.display_name_key), "");
        String userid = sharedPref.getString(getString(R.string.userid_key), "");

//        Log.i(TAG, "[SharedPref] display_name: " + display_name);
//        Log.i(TAG, "[SharedPref] userid: " + userid);

        if (!userid.isEmpty() && !display_name.isEmpty()) {
            goToMainActivity(display_name, userid);
        }

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    if (R.string.not_registered == loginResult.getError()) {
                        showVerificationCode();
                    } else {
                        showLoginFailed(loginResult.getError());
                    }
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
//                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(TextView.VISIBLE);
                if (verificationCodeEditText.getText().toString().isEmpty()) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                } else {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(),
                            verificationCodeEditText.getText().toString());
                }
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

        // Set SharedPreferences
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.display_name_key), model.getDisplayName());
        editor.putString(getString(R.string.userid_key), model.getEmail());
        editor.putString(getString(R.string.temp_auth_key), model.getTempAuthToken());
        editor.apply();

        // Explicit Intent to main activity
        goToMainActivity(model.getDisplayName(), model.getEmail());
    }

    private void goToMainActivity(String display_name, String userid) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.putExtra(MainActivity.DISPLAYNAMEEXTRA, display_name);
        mainIntent.putExtra(MainActivity.USERIDEXTRA, userid);
        startActivity(mainIntent);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void showVerificationCode() {
        Log.i(TAG, "Show verification code");
        EditText verification_code = findViewById(R.id.verification_code);
        verification_code.setVisibility(TextView.VISIBLE);
    }
}