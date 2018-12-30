package pl.politechnika.szczesny.words_world_client.helper;

import android.app.Application;

import pl.politechnika.szczesny.words_world_client.model.Token;

import static pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper.getTokenFormSP;

public class SessionHelper {
    public static boolean isSessionActive(Application application) {
        Token token = getTokenFormSP(application);
        return token != null && !token.getToken().isEmpty();
    }

    public static String getToken(Application application) {
        Token token = getTokenFormSP(application);
        return token.getToken();
    }
}
