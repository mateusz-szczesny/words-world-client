package pl.politechnika.szczesny.words_world_client.service;


import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.politechnika.szczesny.words_world_client.model.Credentials;
import pl.politechnika.szczesny.words_world_client.model.Language;
import pl.politechnika.szczesny.words_world_client.model.Statistics;
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
        Call<User> userDataCall = dataService.getUserData(token.getToken());
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

    public void getUserById(Token token, long id, Callback<User> callback) {
        Call<User> call = dataService.getUserById(token.getToken(), id);
        call.enqueue(callback);
    }

    public void getLanguages(Token token, Callback<List<Language>> callback) {
        Call<List<Language>> call = dataService.getLanguages(token.getToken());
        call.enqueue(callback);
    }

    public void subscribeLanguage(Token token, long id, Callback<Void> callback) {
        Call<Void> call = dataService.subscribeLanguage(token.getToken(), id);
        call.enqueue(callback);
    }

    public void unsubscribeLanguage(Token token, long id, Callback<Void> callback) {
        Call<Void> call = dataService.unsubscribeLanguage(token.getToken(), id);
        call.enqueue(callback);
    }

    public void getFriends(Token token, Callback<List<User>> callback) {
        Call<List<User>> call = dataService.getFollowings(token.getToken());
        call.enqueue(callback);
    }

    public void followUser(Token token, long id, Callback<Void> callback) {
        Call<Void> call = dataService.followUser(token.getToken(), id);
        call.enqueue(callback);
    }

    public void unfollowUser(Token token, long id, Callback<Void> callback) {
        Call<Void> call = dataService.unfollowUser(token.getToken(), id);
        call.enqueue(callback);
    }

    public void updateUserData(Token token, Credentials credentials, Callback<User> callback) {
        Call<User> call = dataService.updateUserData(token.getToken(), credentials);
        call.enqueue(callback);
    }

    public void pushUserStatistics(Token token, Statistics statistics, Callback<Void> callback) {
        Call<Void> call = dataService.pushUserStatistics(token.getToken(), statistics);
        call.enqueue(callback);
    }
}
