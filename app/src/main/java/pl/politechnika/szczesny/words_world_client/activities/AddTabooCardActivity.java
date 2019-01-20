package pl.politechnika.szczesny.words_world_client.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
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

    @BindView(R.id.key_word) TextView _keyWord;
    @BindView(R.id.BL1) TextView _BL1;
    @BindView(R.id.BL2) TextView _BL2;
    @BindView(R.id.BL3) TextView _BL3;
    @BindView(R.id.BL4) TextView _BL4;
    @BindView(R.id.BL5) TextView _BL5;

    @BindView(R.id.owner) TextView _owner;
    @BindView(R.id.language) Spinner _language;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);
        ButterKnife.bind(this);

        assignAuthor();
        fillSpinnerWithSupportedLanguages();
    }

    public void submit(View view) {
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

    private void assignAuthor() {
        SessionUserViewModel sessionUserViewModel = ViewModelProviders.of(this).get(SessionUserViewModel.class);
        sessionUserViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
                    String author = "Autor: " + user.getUsername();
                    _owner.setText(author);
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

        LanguageWrapper(Language language) {
            this.language = language;
        }

        int getLanguageId(){
            return this.language.getId();
        }

        @NonNull
        @Override
        public String toString() {
            return Utils.returnFlagEmojiForLanguage(this.language);
        }
    }
}

