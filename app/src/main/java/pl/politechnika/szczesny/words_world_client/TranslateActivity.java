package pl.politechnika.szczesny.words_world_client;


import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import pl.politechnika.szczesny.words_world_client.helper.ConstHelper;
import pl.politechnika.szczesny.words_world_client.model.TranslatedVoice;
import pl.politechnika.szczesny.words_world_client.model.Translation;
import pl.politechnika.szczesny.words_world_client.service.GoogleTextToSpeech;
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
                String toTranslate = _textToTranslate.getText().toString();
                String srcLang = ConstHelper.langName2langCode.get(_srcLanguage.getSelectedItem().toString());
                String trgLang = ConstHelper.langName2langCode.get(_targetLanguage.getSelectedItem().toString());

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
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
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
                                            } catch (JSONException | IOException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getBaseContext(), "Nie można przetłumaczyć", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Log.d("API ERROR", t.getMessage());
                                    }
                                }
                        );
                    } else {
                        setTranslatedText("");
                    }
                }
            }
        });



        _speakText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = _translatedText.getText().toString();
                String lang = _targetLanguage.getSelectedItem().toString();

                if (!"".equals(text.trim()) && !"".equals(lang.trim())) {
                    GoogleTextToSpeech.getInstance().synthesize(text, lang, new Callback<TranslatedVoice>() {
                        @Override
                        public void onResponse(Call<TranslatedVoice> call, Response<TranslatedVoice> response) {
                            if (response.isSuccessful()) {
                                TranslatedVoice translatedVoice = response.body();

                                System.out.println(">>>>>>>>>>>>>>>>>>" + translatedVoice.getAudioContent());
                                // TODO: run queried voice
                            } else {
                                Toast.makeText(getBaseContext(), "Nie można odtworzyć", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TranslatedVoice> call, Throwable t) {
                            Log.d("API ERROR", t.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void setTranslatedText(String text) {
        _translatedText.setText(text);
    }

}
