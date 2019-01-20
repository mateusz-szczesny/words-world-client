package pl.politechnika.szczesny.words_world_client.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.adapters.AchievementsGridAdapter;
import pl.politechnika.szczesny.words_world_client.adapters.LanguagesMiniAdapter;
import pl.politechnika.szczesny.words_world_client.utils.SessionUtils;
import pl.politechnika.szczesny.words_world_client.utils.Utils;
import pl.politechnika.szczesny.words_world_client.models.User;
import pl.politechnika.szczesny.words_world_client.network.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherUserActivity extends AppCompatActivity {

    @BindView(R.id.username) TextView _username;
    @BindView(R.id.overall_score) TextView _overallScore;
    @BindView(R.id.follow_or_unfollow) Button _changeFollowing;
    @BindView(R.id.achievements_list) RecyclerView _achievementsList;
    @BindView(R.id.languages_list) RecyclerView _languagesList;

    private User _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);
        ButterKnife.bind(this);

        fetchUserDetails();
    }

    private void fetchUserDetails() {
        ProgressDialog _progressDialog = new ProgressDialog(OtherUserActivity.this);
        _progressDialog.setIndeterminate(true);
        _progressDialog.setMessage("Pobieram dane...");
        _progressDialog.show();

        String token = SessionUtils.getToken(getApplication());
        ApiManager.getInstance().getUserById(token, getIntent().getIntExtra(Utils.USER__ID, 0), new Callback<User>() {
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
                Log.d("API ERROR", t.getMessage());
                onFetchFailed();
            }
        });

        _progressDialog.dismiss();
    }

    private void onFetchSuccess() {
        _username.setText(!"".equals(_user.getUsername()) ? _user.getUsername() : "");
        _overallScore.setText(_user.getOverallScore().getScore() != null ?
                String.valueOf(_user.getOverallScore().getScore()) : "0");

        refreshButton();
        assignAchievements();
        assignLanguages();
    }

    private void refreshButton() {
        if (_user.getFriend()) {
            _changeFollowing.setBackgroundColor(Color.rgb(211,47,47));
            _changeFollowing.setText("Nie obserwuj");
            _changeFollowing.setOnClickListener(unfollowUser);
        } else {
            _changeFollowing.setBackgroundColor(Color.rgb(76,175,80));
            _changeFollowing.setText("Obserwuj");
            _changeFollowing.setOnClickListener(followUser);
        }
    }

    private void assignAchievements() {
        GridLayoutManager glm = new GridLayoutManager(getApplicationContext(), 3);
        final AchievementsGridAdapter adapter = new AchievementsGridAdapter(getApplication());

        _achievementsList.setLayoutManager(glm);
        _achievementsList.setAdapter(adapter);
        _achievementsList.setItemAnimator(new DefaultItemAnimator());
        adapter.setAchievements(_user.getAchievements());
    }

    private void assignLanguages() {
        _languagesList = findViewById(R.id.languages_list);

        GridLayoutManager glm = new GridLayoutManager(getApplicationContext(), 3);
        final LanguagesMiniAdapter adapter = new LanguagesMiniAdapter();

        _languagesList.setLayoutManager(glm);
        _languagesList.setAdapter(adapter);
        _languagesList.setItemAnimator(new DefaultItemAnimator());
        adapter.setLanguages(_user.getSelectedLanguages());

    }

    private android.view.View.OnClickListener followUser = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View view) {
            String token = SessionUtils.getToken(getApplication());
            ApiManager.getInstance().followUser(token, _user.getId(), new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        _user.setFriend(true);
                        refreshButton();
                    } else {
                        Toast.makeText(getApplicationContext(), "Nie można obserwować! Błąd!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Log.d("API ERROR", t.getMessage());
                }
            });
        }
    };

    private android.view.View.OnClickListener unfollowUser = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View view) {
            String token = SessionUtils.getToken(getApplication());
            ApiManager.getInstance().unfollowUser(token, _user.getId(), new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        _user.setFriend(false);
                        refreshButton();
                    } else {
                        Toast.makeText(getApplicationContext(), "Nie można obserwować! Błąd!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Log.d("API ERROR", t.getMessage());
                }
            });
        }
    };

    private void onFetchFailed() {
        Toast.makeText(getApplicationContext(), "Nie można pobrać danych użytkownika!", Toast.LENGTH_LONG).show();
        finish();
    }
}
