package pl.politechnika.szczesny.words_world_client.activities;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.BindView;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.models.Token;
import pl.politechnika.szczesny.words_world_client.network.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static pl.politechnika.szczesny.words_world_client.utils.Utils.MINIMUM_PASSWORD_LENGTH;
import static pl.politechnika.szczesny.words_world_client.utils.Utils.MINIMUM_USERNAME_LENGTH;
import static pl.politechnika.szczesny.words_world_client.utils.SharedPreferencesUtils.storeTokenInSP;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private ProgressDialog progressDialog;

    @BindView(R.id.input_username) EditText _usernameText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_signup) Button _signUpButton;
    @BindView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
    }

    public void signUp(View view) {
        Log.d(TAG, "SignUp");

        if (!validate()) {
            onSignUpFailed();
            return;
        }

        _signUpButton.setEnabled(false);

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String username = _usernameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        ApiManager.getInstance().registerUser(username, email, password, new Callback<Token>() {
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                if (response.isSuccessful()) {
                    Token resToken = response.body();
                    storeTokenInSP(resToken, getApplication());
                    onSignUpSuccess();
                }else {
                    onSignUpFailed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                Log.d("INTERNAL ERROR", t.getMessage());
            }
        });
    }

    public void backToLogin(View view) {
        finish();
    }

    private void onSignUpSuccess() {
        _signUpButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    private void onSignUpFailed() {
        Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_LONG).show();
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        _signUpButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty() || username.length() < MINIMUM_USERNAME_LENGTH) {
            _usernameText.setError("at least 4 characters");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < MINIMUM_PASSWORD_LENGTH) {
            _passwordText.setError("at least 6 characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}