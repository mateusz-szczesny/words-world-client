package pl.politechnika.szczesny.words_world_client.helper;

import android.app.Application;
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
        ApiManager.getInstance().fetchUser(getTokenFormSP(application), new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User resUser = response.body();
                storeUserInSP(resUser, application);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("INTERNAL ERROR", "CANNOT FETCH USER DATA");
            }
        });
    }
}
