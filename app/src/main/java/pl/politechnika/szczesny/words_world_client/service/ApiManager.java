package pl.politechnika.szczesny.words_world_client.service;


import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.politechnika.szczesny.words_world_client.model.Card;
import pl.politechnika.szczesny.words_world_client.model.Credentials;
import pl.politechnika.szczesny.words_world_client.model.Language;
import pl.politechnika.szczesny.words_world_client.model.Statistics;
import pl.politechnika.szczesny.words_world_client.model.Token;
import pl.politechnika.szczesny.words_world_client.model.User;
import pl.politechnika.szczesny.words_world_client.taboo.TabooCard;
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

    public void fetchUser(String token, Callback<User> callback) {
        Call<User> userDataCall = dataService.getUserData(token);
        userDataCall.enqueue(callback);
    }

    public void registerUser(String username, String email, String password, Callback<Token> callback) {
        Call<Token> tokenCall = authService.register(username, email, password);
        tokenCall.enqueue(callback);
    }

    public void getUsersByFilter(String token, String filter, Callback<List<User>> callback) {
        Call<List<User>> call = dataService.getUsersByFilter(token, filter);
        call.enqueue(callback);
    }

    public void getUserById(String token, long id, Callback<User> callback) {
        Call<User> call = dataService.getUserById(token, id);
        call.enqueue(callback);
    }

    public void getLanguages(String token, Callback<List<Language>> callback) {
        Call<List<Language>> call = dataService.getLanguages(token);
        call.enqueue(callback);
    }

    public void subscribeLanguage(String token, long id, Callback<Void> callback) {
        Call<Void> call = dataService.subscribeLanguage(token, id);
        call.enqueue(callback);
    }

    public void unsubscribeLanguage(String token, long id, Callback<Void> callback) {
        Call<Void> call = dataService.unsubscribeLanguage(token, id);
        call.enqueue(callback);
    }

    public void getFriends(String token, Callback<List<User>> callback) {
        Call<List<User>> call = dataService.getFollowings(token);
        call.enqueue(callback);
    }

    public void followUser(String token, long id, Callback<Void> callback) {
        Call<Void> call = dataService.followUser(token, id);
        call.enqueue(callback);
    }

    public void unfollowUser(String token, long id, Callback<Void> callback) {
        Call<Void> call = dataService.unfollowUser(token, id);
        call.enqueue(callback);
    }

    public void updateUserData(String token, Credentials credentials, Callback<User> callback) {
        Call<User> call = dataService.updateUserData(token, credentials);
        call.enqueue(callback);
    }

    public void pushUserStatistics(String token, Statistics statistics, Callback<Void> callback) {
        Call<Void> call = dataService.pushUserStatistics(token, statistics);
        call.enqueue(callback);
    }

    public void randomCards(String token, long languageId, Integer cardsCount, Callback<List<Card>> callback) {
        Call<List<Card>> call = dataService.randTabooCards(token, languageId, cardsCount);
        call.enqueue(callback);
    }

    public void getMyCards(String token, Callback<List<Card>> callback) {
        Call<List<Card>> call = dataService.getMyCards(token);
        call.enqueue(callback);
    }

    public void createNewCard(String token, String keyWord, String blackList, int languageId, Callback<Void> callback) {
        Call<Void> call = dataService.createNewCard(token, keyWord, blackList, languageId);
        call.enqueue(callback);
    }
}
