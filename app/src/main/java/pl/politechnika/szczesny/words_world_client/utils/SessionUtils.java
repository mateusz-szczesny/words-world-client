package pl.politechnika.szczesny.words_world_client.utils;

import android.app.Application;

import pl.politechnika.szczesny.words_world_client.models.Token;

import static pl.politechnika.szczesny.words_world_client.utils.SharedPreferencesUtils.getTokenFormSP;

public class SessionUtils {
    public static boolean isSessionActive(Application application) {
        Token token = getTokenFormSP(application);
        return token != null && !token.getToken().isEmpty();
    }

    public static String getToken(Application application) {
        Token token = getTokenFormSP(application);
        return token.getToken();
    }
}
