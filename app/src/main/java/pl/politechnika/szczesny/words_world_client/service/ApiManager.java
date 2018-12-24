package pl.politechnika.szczesny.words_world_client.service;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.politechnika.szczesny.words_world_client.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private static AuthApi authService;
    private static DataApi dataService;
    private static ApiManager apiManager;

    public enum StuffType { BORROWED, LENT }

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

    public void loginUser(String email, String password, Callback<User> callback) {
        Call<User> userCall = authService.login(email,password);
        userCall.enqueue(callback);
    }

    public void registerUser(String email, String password, String passwordConfirmation,String username,  Callback<User> callback) {
        Call<User> userCall = authService.register(email, password, passwordConfirmation, username);
        userCall.enqueue(callback);
    }
}
