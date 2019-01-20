package pl.politechnika.szczesny.words_world_client.activities;


import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.BindView;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.utils.SessionUtils;
import pl.politechnika.szczesny.words_world_client.models.Token;
import pl.politechnika.szczesny.words_world_client.models.User;
import pl.politechnika.szczesny.words_world_client.network.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static pl.politechnika.szczesny.words_world_client.utils.Utils.MINIMUM_USERNAME_LENGTH;
import static pl.politechnika.szczesny.words_world_client.utils.SharedPreferencesUtils.storeTokenInSP;
import static pl.politechnika.szczesny.words_world_client.utils.SharedPreferencesUtils.storeUserInSP;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 1;

    private ProgressDialog progressDialog;

    @BindView(R.id.input_username) EditText _usernameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signUpLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void signUp(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    public void login(View view) {
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
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                if (response.isSuccessful()) {
                    Token resToken = response.body();
                    storeTokenInSP(resToken, getApplication());
                    fetchUserAndLogIn();
                } else {
                    onLoginFailed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                Log.d("INTERNAL ERROR", t.getMessage());
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
        moveTaskToBack(true);
    }

    private void onLoginSuccess() {
        _loginButton.setEnabled(true);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void fetchUserAndLogIn() {
        String token = SessionUtils.getToken(getApplication());

        ApiManager.getInstance().fetchUser(token, new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User resUser = response.body();
                    storeUserInSP(resUser, getApplication());
                    onLoginSuccess();
                } else {
                    onLoginFailed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.d("LOGIN ERROR", t.getMessage());
            }
        });
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
        progressDialog.cancel();
    }

    private boolean validate() {
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
