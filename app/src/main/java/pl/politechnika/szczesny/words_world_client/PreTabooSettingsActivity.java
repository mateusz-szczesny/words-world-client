package pl.politechnika.szczesny.words_world_client;

import android.app.ProgressDialog;
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
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.politechnika.szczesny.words_world_client.helper.SessionHelper;
import pl.politechnika.szczesny.words_world_client.helper.Utils;
import pl.politechnika.szczesny.words_world_client.model.Card;
import pl.politechnika.szczesny.words_world_client.model.Language;
import pl.politechnika.szczesny.words_world_client.model.User;
import pl.politechnika.szczesny.words_world_client.service.ApiManager;
import pl.politechnika.szczesny.words_world_client.viewmodel.SessionUserViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreTabooSettingsActivity extends AppCompatActivity {

    @BindView(R.id.language_picker) NumberPicker _langPicker;
    @BindView(R.id.cc_picker) NumberPicker _ccPicker;
    @BindView(R.id.go_ahead) Button _play;
    @BindView(R.id.kda) TextView _kda;
    @BindView(R.id.totalSwipes) TextView _totalSwipes;
    @BindView(R.id.correctSwipes) TextView _correctSwipes;

    private Integer ccSetting;
    private long langIdSetting;
    private List<Language> languages;

    private ProgressDialog progressDialog;


    private SessionUserViewModel sessionUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_taboo_settings);
        ButterKnife.bind(this);

        fillPickers();

        sessionUserViewModel = ViewModelProviders.of(this).get(SessionUserViewModel.class);
        sessionUserViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
                    updateKDR(user);
                }
            }
        });

        _play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(PreTabooSettingsActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Uruchamianie...");
                progressDialog.show();

                String token = SessionHelper.getToken(getApplication());
                ApiManager.getInstance().randomCards(token, langIdSetting, ccSetting, new Callback<List<Card>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Card>> call, @NonNull Response<List<Card>> response) {
                        if (response.isSuccessful()) {
                            List<Card> cards = response.body();

                            Type type = new TypeToken<List<Card>>() {}.getType();
                            String json = new Gson().toJson(cards, type);

                            Intent intent = new Intent(getBaseContext(), TabooActivity.class);
                            intent.putExtra(TabooActivity.CARDS, json);
                            progressDialog.cancel();
                            startActivity(intent);
                        } else {
                            Toast.makeText(getBaseContext(), "Błąd połączenia!", Toast.LENGTH_LONG).show();
                            progressDialog.cancel();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Card>> call, @NonNull Throwable t) {
                        Log.d("API ERROR", t.getMessage());
                        progressDialog.cancel();
                    }
                });
            }
        });
    }

    private void updateKDR(User user) {
        String percentageStat = String.valueOf(user.getTabooEfficiency() * 100.0) + "%";
        _kda.setText(percentageStat);
        _totalSwipes.setText(String.valueOf(user.getTotalSwipesTbo()));
        _correctSwipes.setText(String.valueOf(user.getCorrectSwipesTbo()));
    }

    private void fillPickers() {
        String token = SessionHelper.getToken(getApplication());
        ApiManager.getInstance().getLanguages(token, new Callback<List<Language>>() {
            @Override
            public void onResponse(@NonNull Call<List<Language>> call, @NonNull Response<List<Language>> response) {
                if (response.isSuccessful()) {
                    languages = response.body();
                    assignLanguages(languages != null ? languages : new ArrayList<Language>());
                } else {
                    Toast.makeText(getBaseContext(), "Błąd połączenia!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Language>> call, @NonNull Throwable t) {
                Log.d("API ERROR", t.getMessage());
            }
        });

        ccSetting = Utils.DEFAULT_CARD_COUNT;
        _ccPicker.setMinValue(Utils.MINIMAL_CARD_COUNT);
        _ccPicker.setMaxValue(Utils.MAXIMAL_CARD_COUNT);
        _ccPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                ccSetting = newVal;
            }
        });
    }

    private void assignLanguages(final List<Language> languages) {
        _langPicker.setMinValue(0);
        _langPicker.setMaxValue(languages.size()-1);
        String[] nameArray = new String[languages.size()];
        for (int i = 0; i < languages.size(); i++) {
            nameArray[i] = Utils.returnFlagEmojiForLanguage(languages.get(i));
            if (i == 0){
                langIdSetting = languages.get(i).getId();
            }
        }
        _langPicker.setDisplayedValues(nameArray);

        _langPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                langIdSetting = languages.get(newVal).getId();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sessionUserViewModel.refreshData();
    }
}
