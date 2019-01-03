package pl.politechnika.szczesny.words_world_client;


import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import pl.politechnika.szczesny.words_world_client.helper.ConstHelper;
import pl.politechnika.szczesny.words_world_client.model.Translation;
import pl.politechnika.szczesny.words_world_client.service.GoogleTranslate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TranslateActivity extends AppCompatActivity {

    @BindView(R.id.src_language) Spinner _srcLanguage;
    @BindView(R.id.target_language) Spinner _targetLanguage;
    @BindView(R.id.to_translate) TextView _textToTranslate;
    @BindView(R.id.translated_text) TextView _translatedText;
    @BindView(R.id.voice) ImageButton _speakText;
    @BindView(R.id.translate) Button _translate;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        ButterKnife.bind(this);

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, ConstHelper.supportedLangToTranslate);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        _srcLanguage.setAdapter(spinnerArrayAdapter);
        _targetLanguage.setAdapter(spinnerArrayAdapter);

        _translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translate();
            }
        });

        _targetLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setUpNewLocaleForTTF(adapterView, i);
                translate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        _speakText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakTranslated();
            }
        });
    }

    private void speakTranslated() {
        final String text = _translatedText.getText().toString();

        if (!"".equals(text.trim())) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);

        }
    }

    private void setUpNewLocaleForTTF(AdapterView<?> adapterView, int position) {
        final String lang = adapterView.getItemAtPosition(position).toString();

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(ConstHelper.langName2Locale.get(lang));
                }
            }
        });
    }

    private void translate() {
        final String toTranslate = _textToTranslate.getText().toString();
        final String srcLang = ConstHelper.langName2langCode.get(_srcLanguage.getSelectedItem().toString());
        final String trgLang = ConstHelper.langName2langCode.get(_targetLanguage.getSelectedItem().toString());

        if ("".equals(srcLang) || "".equals(trgLang)) {
            Toast.makeText(getBaseContext(), "Musisz wybrać język!", Toast.LENGTH_LONG).show();

        } else {
            if (!"".equals(toTranslate.trim())) {

                GoogleTranslate.getInstance().translate(
                        toTranslate,
                        ConstHelper.langName2langCode.get(_srcLanguage.getSelectedItem().toString()),
                        ConstHelper.langName2langCode.get(_targetLanguage.getSelectedItem().toString()),
                        new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    try {
                                        String body = response.body().string();
                                        JSONObject responseBody = new JSONObject(body);
                                        JSONArray array = responseBody.getJSONObject("data").getJSONArray("translations");

                                        Type listType = new TypeToken<ArrayList<Translation>>(){}.getType();
                                        ArrayList<Translation> translations = new Gson().fromJson(array.toString(), listType);

                                        StringBuilder output = new StringBuilder();
                                        for (Translation t : translations) {
                                            output.append(t.getTranslatedText());
                                        }

                                        setTranslatedText(output.toString());
                                    } catch (JSONException | IOException | NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    if (srcLang != null && srcLang.equals(trgLang)) {
                                        setTranslatedText(toTranslate);
                                    } else {
                                        Toast.makeText(getBaseContext(), "Nie można przetłumaczyć", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                                Log.d("API ERROR", t.getMessage());
                            }
                        }
                );
            } else {
                setTranslatedText("");
            }
        }
    }

    private void setTranslatedText(String text) {
        _translatedText.setText(text);
    }
}
