package pl.politechnika.szczesny.words_world_client.service;


import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.politechnika.szczesny.words_world_client.helper.ConstHelper;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;

public class GoogleManager {
    private static TranslateService translateService;
    private static GoogleManager apiManager;

    private GoogleManager() {

        final String END_POINT = "https://translation.googleapis.com/";

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

    public static GoogleManager getInstance() {
        if (apiManager == null) {
            apiManager = new GoogleManager();
        }
        return apiManager;
    }

    public void translate(String srcText, String srcLang, String trgLang, Callback<ResponseBody> callback) {
        Call<ResponseBody> translateCall = translateService.translate(srcText, trgLang, srcLang, ConstHelper.GOOGLE_API__KEY);
        translateCall.enqueue(callback);
    }
}
