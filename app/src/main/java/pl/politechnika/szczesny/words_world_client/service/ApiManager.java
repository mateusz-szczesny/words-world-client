package pl.politechnika.szczesny.words_world_client.service;


import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.politechnika.szczesny.words_world_client.model.Token;
import pl.politechnika.szczesny.words_world_client.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private static AuthApi authService;
    private static DataApi dataService;
    private static ApiManager apiManager;

    private ApiManager() {

        final String END_POINT = "https://words-world-api.o-and-m.ovh/";

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

        authService = retrofit.create(AuthApi.class);
        dataService = retrofit.create(DataApi.class);

    }

    public static ApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public void authenticate(String username, String password, Callback<Token> callback) {
        Call<Token> tokenCall = authService.login(username ,password);
        tokenCall.enqueue(callback);
    }

    public void fetchUser(Token token, Callback<User> callback) {
        Call<User> userDataCall = authService.getUserData(token.getToken());
        userDataCall.enqueue(callback);
    }

    public void registerUser(String username, String email, String password, Callback<Token> callback) {
        Call<Token> tokenCall = authService.register(username, email, password);
        tokenCall.enqueue(callback);
    }

    public void getUsersByFilter(Token token, String filter, Callback<List<User>> callback) {
        Call<List<User>> call = dataService.getUsersByFilter(token.getToken(), filter);
        call.enqueue(callback);
    }
}
