package pl.politechnika.szczesny.words_world_client.helper;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import pl.politechnika.szczesny.words_world_client.model.Token;
import pl.politechnika.szczesny.words_world_client.model.User;
import pl.politechnika.szczesny.words_world_client.service.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper.getTokenFormSP;
import static pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper.storeUserInSP;

public class SessionHelper {
    public static boolean isSessionActive(Application application) {
        Token token = getTokenFormSP(application);
        return token != null && !token.getToken().isEmpty();
    }

    public static String getToken(Application application) {
        Token token = getTokenFormSP(application);
        return token.getToken();
    }

    public static void updateUserData(final Application application) {
        String token = SessionHelper.getToken(application);
        ApiManager.getInstance().fetchUser(token, new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                User resUser = response.body();
                storeUserInSP(resUser, application);
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.d("INTERNAL ERROR", "CANNOT FETCH USER DATA");
            }
        });
    }
}
