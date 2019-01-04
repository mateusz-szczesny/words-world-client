package pl.politechnika.szczesny.words_world_client;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.politechnika.szczesny.words_world_client.helper.SessionHelper;
import pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper;
import pl.politechnika.szczesny.words_world_client.helper.Utils;
import pl.politechnika.szczesny.words_world_client.model.Token;
import pl.politechnika.szczesny.words_world_client.model.User;
import pl.politechnika.szczesny.words_world_client.service.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherUserActivity extends AppCompatActivity {

    @BindView(R.id.first_name) TextView _firstName;
    @BindView(R.id.last_name) TextView _lastName;
    @BindView(R.id.username) TextView _username;
    @BindView(R.id.follow_or_unfollow) Button _changeFollowing;
    @BindView(R.id.achievements_list) RecyclerView _achievementsList;
    @BindView(R.id.languages_list) RecyclerView _languagesList;

    private ProgressDialog _progressDialog;
    private User _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);
        ButterKnife.bind(this);

        fetchUserDetails();
    }

    private void fetchUserDetails() {
        _progressDialog = new ProgressDialog(OtherUserActivity.this);
        _progressDialog.setIndeterminate(true);
        _progressDialog.setMessage("Pobieram dane...");
        _progressDialog.show();

        Token token = SharedPrefHelper.getTokenFormSP(getApplication());
        ApiManager.getInstance().getUserById(token, getIntent().getLongExtra(Utils.USER__ID, 0), new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()){
                    _user = response.body();
                    onFetchSuccess();
                } else {
                    onFetchFailed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.d("API ERROR", "CANNOT FETCH USER DATA");
                onFetchFailed();
            }
        });

        _progressDialog.dismiss();
    }

    private void onFetchSuccess() {
        _firstName.setText(!"".equals(_user.getFirstName()) ? _user.getFirstName() : "");
        _lastName.setText(!"".equals(_user.getLastName()) ? _user.getLastName() : "");
        _username.setText(!"".equals(_user.getUsername()) ? _user.getUsername() : "");

        refreshButton();
    }

    private void refreshButton() {
        if (_user.getFriend()) {
            _changeFollowing.setBackgroundColor(Color.rgb(255,189,189));
            _changeFollowing.setText("Przestań obserwować");
            _changeFollowing.setOnClickListener(unfollowUser);
        } else {
            _changeFollowing.setBackgroundColor(Color.rgb(225,247,213));
            _changeFollowing.setText("Obserwuj");
            _changeFollowing.setOnClickListener(followUser);
        }
    }

    android.view.View.OnClickListener followUser = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View view) {
            Token token = SharedPrefHelper.getTokenFormSP(getApplication());
            ApiManager.getInstance().followUser(token, _user.getId(), new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        _user.setFriend(true);
                        refreshButton();
                        SessionHelper.updateUserData(getApplication());
                    } else {
                        Toast.makeText(getApplicationContext(), "Nie można obserwować! Błąd!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Log.d("API ERROR", "CANNOT FOLLOW USER");
                }
            });
        }
    };

    android.view.View.OnClickListener unfollowUser = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View view) {
            Token token = SharedPrefHelper.getTokenFormSP(getApplication());
            ApiManager.getInstance().unfollowUser(token, _user.getId(), new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        _user.setFriend(false);
                        refreshButton();
                        SessionHelper.updateUserData(getApplication());
                    } else {
                        Toast.makeText(getApplicationContext(), "Nie można obserwować! Błąd!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Log.d("API ERROR", "CANNOT FOLLOW USER");
                }
            });
        }
    };

    private void onFetchFailed() {
        Toast.makeText(getApplicationContext(), "Nie można pobrać danych użytkownika!", Toast.LENGTH_LONG).show();
        finish();
    }
}
