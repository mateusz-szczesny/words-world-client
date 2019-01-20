package pl.politechnika.szczesny.words_world_client.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.utils.SessionUtils;
import pl.politechnika.szczesny.words_world_client.models.Credentials;
import pl.politechnika.szczesny.words_world_client.models.User;
import pl.politechnika.szczesny.words_world_client.network.ApiManager;
import pl.politechnika.szczesny.words_world_client.viewmodel.SessionUserViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {
    @BindView(R.id.input_username) EditText _usernameText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_first_name) EditText _firstNameText;
    @BindView(R.id.input_last_name) EditText _lastNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        fillInputsWithUserData();
    }

    public void submit(View view) {
        String token = SessionUtils.getToken(getApplication());
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
