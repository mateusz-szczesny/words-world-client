package pl.politechnika.szczesny.words_world_client;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.politechnika.szczesny.words_world_client.helper.SessionHelper;
import pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper;
import pl.politechnika.szczesny.words_world_client.model.Credentials;
import pl.politechnika.szczesny.words_world_client.model.User;
import pl.politechnika.szczesny.words_world_client.service.ApiManager;
import pl.politechnika.szczesny.words_world_client.viewmodel.SessionUserViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";

    @BindView(R.id.input_username) EditText _usernameText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_first_name) EditText _firstNameText;
    @BindView(R.id.input_last_name) EditText _lastNameText;
    @BindView(R.id.submit) Button _submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        fillInputsWithUserData();

        _submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = SessionHelper.getToken(getApplication());
                Credentials credentials = new Credentials();
                credentials.firstName = _firstNameText.getText().toString();
                credentials.lastName = _lastNameText.getText().toString();
                if (token != null ) {
                    ApiManager.getInstance().updateUserData(token, credentials, new Callback<User>() {
                        @Override
                        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getBaseContext(), "Dane zaktualizowane!", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(getBaseContext(), "Błąd połączenia!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                            Log.d("INTERNAL ERROR", t.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void fillInputsWithUserData() {
        SessionUserViewModel sessionUserViewModel = ViewModelProviders.of(this).get(SessionUserViewModel.class);
        sessionUserViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
                    _usernameText.setHint(!"".equals(user.getUsername()) ? user.getUsername() : "");
                    _emailText.setHint(!"".equals(user.getEmail()) ? user.getEmail() : "");
                    _firstNameText.setHint(!"".equals(user.getFirstName()) ? user.getFirstName() : "Ustaw imię");
                    _lastNameText.setHint(!"".equals(user.getLastName()) ? user.getLastName() : "Ustaw nazwisko");
                }
            }
        });
    }
}
