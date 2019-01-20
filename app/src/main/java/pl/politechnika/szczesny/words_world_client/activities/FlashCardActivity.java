package pl.politechnika.szczesny.words_world_client.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.models.FlashCard;
import pl.politechnika.szczesny.words_world_client.models.Language;
import pl.politechnika.szczesny.words_world_client.models.Statistics;
import pl.politechnika.szczesny.words_world_client.models.Translation;
import pl.politechnika.szczesny.words_world_client.network.GoogleTranslate;
import pl.politechnika.szczesny.words_world_client.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlashCardActivity extends AppCompatActivity {
    public static final String CARDS = "CARDS";
    private static final String DEFAULT_INPUT_LANGUAGE = "pl";
    private List<FlashCard> cards = new ArrayList<>();
    private static final int RESULT_SPEECH = 1;
    private int position = 0;
    private int maxPosition = 0;

    @BindView(R.id.pattern_flag) TextView _patternFlag;
    @BindView(R.id.target_flag) TextView _targetFlag;
    @BindView(R.id.pattern_word) TextView _patternWord;
    @BindView(R.id.target_word) EditText _targetWord;
    @BindView(R.id.progress_bar) ProgressBar _progressBar;
    @BindView(R.id.progress_text) TextView _progressText;

    private FlashCard currentFlashCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        ButterKnife.bind(this);

        assignCards();
        init();
        updateProgress();
    }

    private void updateProgress() {
        _progressBar.setProgress(((position+1) / maxPosition) * 100);
        _progressBar.setSecondaryProgress(((position+2) / maxPosition) * 100);
        String progress = position+1 + " / " + maxPosition;
        _progressText.setText(progress);
    }

    private void init() {
        currentFlashCard = cards.get(position);

        Language inputLanguage = new Language();
        inputLanguage.setLanguageCode(DEFAULT_INPUT_LANGUAGE);
        Language patternLanguage = new Language();
        patternLanguage.setLanguageCode(currentFlashCard.getLanguageCode());
        _patternFlag.setText(Utils.returnFlagEmojiForLanguage(patternLanguage));
        _targetFlag.setText(Utils.returnFlagEmojiForLanguage(inputLanguage));

        _patternWord.setText(currentFlashCard.getWord());;
    }

    private void assignCards() {
        String stringCards = getIntent().getStringExtra(CARDS);
        if (stringCards != null) {
            Type type = new TypeToken<List<FlashCard>>() {
            }.getType();
            cards = new Gson().fromJson(stringCards, type);
            maxPosition = cards.size();
        } else {
            Log.d("DATA", "MISSING!");
        }
    }

    public void checkCurrent(View view) {
        String word = _targetWord.getText().toString();
        if (!"".equals(word.trim())) {
            GoogleTranslate.getInstance().translate(word, DEFAULT_INPUT_LANGUAGE, currentFlashCard.getLanguageCode(), new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String body = response.body() != null ? response.body().string() : null;
                            if (body != null && !"".equals(body.trim())) {
                                JSONObject responseBody = new JSONObject(body);
                                JSONArray array = responseBody.getJSONObject("data").getJSONArray("translations");

                                Type listType = new TypeToken<ArrayList<Translation>>() {
                                }.getType();
                                ArrayList<Translation> translations = new Gson().fromJson(array.toString(), listType);
                                String compareTo = translations.get(0).getTranslatedText();

                                if (compareTo.trim().toLowerCase().equals(currentFlashCard.getWord().trim().toLowerCase())) {
                                    onSuccessCheck();
                                } else {
                                    onFailCheck(false);
                                }
                            } else {
                                onFailCheck(false);
                            }
                        } catch (JSONException | IOException | NullPointerException e) {
                            e.printStackTrace();
                            onFailCheck(true);
                        }
                    } else {
                        onFailCheck(true);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Log.d("API ERROR", t.getMessage());
                    onFailCheck(true);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(),
                    "Opps! Musisz wpisać słowo!",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void onSuccessCheck() {
        makeToast(true);
        Statistics.getInstance().addCorrectlyAnsFlashCards(currentFlashCard.getId());
        moveCards();
    }

    private void onFailCheck(Boolean systemCause) {
        if (!systemCause) {
            makeToast(false);
            Statistics.getInstance().addIncorrectlyAnsFlashCards(currentFlashCard.getId());
            moveCards();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Błąd aplikacji!",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void makeToast(Boolean isCorrect) {
        View toastView = getLayoutInflater().inflate(isCorrect ? R.layout.toast_correct : R.layout.toast_incorrect, null);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.show();
    }

    private void moveCards() {
        position++;
        if (position < maxPosition) {
            currentFlashCard = cards.get(position);
            updateProgress();
            _patternWord.setText(currentFlashCard.getWord());
            _targetWord.setText("");
        } else {
            _patternWord.setText("Wróć później po więcej kart!");
            _targetWord.setText("");
            findViewById(R.id.check).setEnabled(false);
            findViewById(R.id.speech2text).setEnabled(false);
            _targetWord.setEnabled(false);
        }
    }

    public void convertToText(View view) {
        Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, DEFAULT_INPUT_LANGUAGE);

        try {
            startActivityForResult(intent, RESULT_SPEECH);
            _targetWord.setText("");
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Opps! Twoje urządzenie nie wspiera Speech to Text :(",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    _targetWord.setText(text.get(0));
                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Statistics.getInstance().pushStatistics(getApplication());
        this.finish();
    }
}
