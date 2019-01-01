package pl.politechnika.szczesny.words_world_client;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.BindView;
import pl.politechnika.szczesny.words_world_client.model.Token;
import pl.politechnika.szczesny.words_world_client.model.User;
import pl.politechnika.szczesny.words_world_client.service.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static pl.politechnika.szczesny.words_world_client.helper.ConstHelper.MINIMUM_USERNAME_LENGTH;
import static pl.politechnika.szczesny.words_world_client.helper.SessionHelper.isSessionActive;
import static pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper.getTokenFormSP;
import static pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper.storeTokenInSP;
import static pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper.storeUserInSP;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 1;

    ProgressDialog progressDialog;

    @BindView(R.id.input_username) EditText _usernameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signUpLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (isSessionActive(getApplication())) {
            fetchUserAndLogIn();
        }

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signUpLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Uwierzytelnianie...");
        progressDialog.show();

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        ApiManager.getInstance().authenticate(username, password, new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Token resToken = response.body();
                    storeTokenInSP(resToken, getApplication());
                    fetchUserAndLogIn();
                } else {
                    onLoginFailed();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.d("INTERNAL ERROR", "CANNOT LOG IN");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                fetchUserAndLogIn();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void fetchUserAndLogIn() {
        ApiManager.getInstance().fetchUser(getTokenFormSP(getApplication()), new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User resUser = response.body();
                    storeUserInSP(resUser, getApplication());
                    onLoginSuccess();
                } else {
                    onLoginFailed();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("INTERNAL ERROR", "CANNOT FETCH USER DATA");
            }
        });
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
        progressDialog.cancel();
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();


        if (username.isEmpty() || username.length() < MINIMUM_USERNAME_LENGTH) {
            _usernameText.setError("przynajmniej 4 znaki");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            _passwordText.setError("przynajmniej 6 znaków");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
