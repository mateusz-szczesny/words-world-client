package pl.politechnika.szczesny.words_world_client.service;


import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.politechnika.szczesny.words_world_client.helper.ConstHelper;
import pl.politechnika.szczesny.words_world_client.model.Language;
import pl.politechnika.szczesny.words_world_client.model.TextToSpeechRequest;
import pl.politechnika.szczesny.words_world_client.model.TranslatedVoice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleTextToSpeech {
    private static TranslateService translateService;
    private static GoogleTextToSpeech apiManager;

    private GoogleTextToSpeech() {

        final String END_POINT = "https://texttospeech.googleapis.com/";

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        translateService = retrofit.create(TranslateService.class);

    }

    public static GoogleTextToSpeech getInstance() {
        if (apiManager == null) {
            apiManager = new GoogleTextToSpeech();
        }
        return apiManager;
    }

    public void synthesize(String text, String language, Callback<TranslatedVoice> callback) {
        Call<TranslatedVoice> translateCall = translateService.synthesize(
                ConstHelper.GOOGLE_API__KEY,
                new TextToSpeechRequest(text, language)
        );
        translateCall.enqueue(callback);
    }
}
