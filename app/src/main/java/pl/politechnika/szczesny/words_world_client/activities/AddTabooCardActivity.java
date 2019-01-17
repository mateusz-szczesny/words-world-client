package pl.politechnika.szczesny.words_world_client.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.utils.SessionUtils;
import pl.politechnika.szczesny.words_world_client.utils.Utils;
import pl.politechnika.szczesny.words_world_client.models.Language;
import pl.politechnika.szczesny.words_world_client.models.User;
import pl.politechnika.szczesny.words_world_client.network.ApiManager;
import pl.politechnika.szczesny.words_world_client.viewmodel.LanguageViewModel;
import pl.politechnika.szczesny.words_world_client.viewmodel.SessionUserViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTabooCardActivity extends AppCompatActivity {

    private TextView _keyWord;
    private TextView _BL1;
    private TextView _BL2;
    private TextView _BL3;
    private TextView _BL4;
    private TextView _BL5;

    private TextView _owner;
    private Spinner _language;
    private Button _submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);

        connectViews();
        assignAuthor();
        fillSpinnerWithSupportedLanguages();

        _submit.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                String keyWord = _keyWord.getText().toString().toUpperCase();
                List<String> forbiddenWordsInput = new ArrayList<>();
                forbiddenWordsInput.add(_BL1.getText().toString().toUpperCase());
                forbiddenWordsInput.add(_BL2.getText().toString().toUpperCase());
                forbiddenWordsInput.add(_BL3.getText().toString().toUpperCase());
                forbiddenWordsInput.add(_BL4.getText().toString().toUpperCase());
                forbiddenWordsInput.add(_BL5.getText().toString().toUpperCase());
                String blackList = TextUtils.join("; ", forbiddenWordsInput);
                int languageId = ((LanguageWrapper)_language.getSelectedItem()).getLanguageId();

                if ("".equals(keyWord.trim()) || "".equals(blackList.trim())) {
                    onCreteFailed();
                } else {
                    String token = SessionUtils.getToken(getApplication());
                    ApiManager.getInstance().createNewCard(token, keyWord, blackList, languageId, new Callback<Void>() {
                        @Override
                        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                            if (response.isSuccessful()) {
                                onCreateSuccess();
                            } else {
                                onCreteFailed();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                            Log.d("API ERROR", t.getMessage());
                            onCreteFailed();
                        }
                    });
                }
            }
        });
    }

    private void connectViews() {
        _keyWord = findViewById(R.id.key_word);
        _BL1 = findViewById(R.id.BL1);
        _BL2 = findViewById(R.id.BL2);
        _BL3 = findViewById(R.id.BL3);
        _BL4 = findViewById(R.id.BL4);
        _BL5 = findViewById(R.id.BL5);
        _owner = findViewById(R.id.owner);
        _language = findViewById(R.id.language);
        _submit = findViewById(R.id.submit);
    }

    private void assignAuthor() {
        SessionUserViewModel sessionUserViewModel = ViewModelProviders.of(this).get(SessionUserViewModel.class);
        sessionUserViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
                    _owner.setText("Autor: " + user.getUsername());
                }
            }
        });
    }

    private void fillSpinnerWithSupportedLanguages() {
        final List<LanguageWrapper> list = new ArrayList<>();
        LanguageViewModel languageViewModel = ViewModelProviders.of(this).get(LanguageViewModel.class);
        languageViewModel.getLanguages().observe(this, new Observer<List<Language>>() {
            @Override
            public void onChanged(@Nullable List<Language> languages) {
                if (languages != null && !languages.isEmpty()) {

                    List<LanguageWrapper> languagesWrapper = new ArrayList<>();
                    for (Language language : languages) {
                        languagesWrapper.add(new LanguageWrapper(language));
                    }
                    list.addAll(languagesWrapper);
                }
                ArrayAdapter<LanguageWrapper> spinnerArrayAdapter = new ArrayAdapter<>(
                        getApplicationContext(), R.layout.spinner_item, list);
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                _language.setAdapter(spinnerArrayAdapter);
            }
        });
    }

    private void onCreateSuccess() {
        Toast.makeText(getBaseContext(), "Karta została dodana!", Toast.LENGTH_LONG).show();
        this.finish();

    }

    private void onCreteFailed() {
        Toast.makeText(getBaseContext(), "Błąd! Sprawdz czy wszystkie pola są wypełnione...", Toast.LENGTH_LONG).show();
    }

    class LanguageWrapper {
        Language language;

        public LanguageWrapper(Language language) {
            this.language = language;
        }

        public int getLanguageId(){
            return this.language.getId();
        }

        @Override
        public String toString() {
            return Utils.returnFlagEmojiForLanguage(this.language);
        }
    }
}

