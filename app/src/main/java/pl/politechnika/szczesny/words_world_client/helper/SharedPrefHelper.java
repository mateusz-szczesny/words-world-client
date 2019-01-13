package pl.politechnika.szczesny.words_world_client.helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import pl.politechnika.szczesny.words_world_client.model.Token;
import pl.politechnika.szczesny.words_world_client.model.User;


public class SharedPrefHelper {

    // GET SP FOR APP
    private static SharedPreferences getUserSharedPreferences(Application application) {
        return application.getSharedPreferences(Utils.USER__SP, Context.MODE_PRIVATE);
    }

    private static SharedPreferences getTabooSharedPreferences(Application application) {
        return application.getSharedPreferences(Utils.TABOO__SP, Context.MODE_PRIVATE);
    }

    private static SharedPreferences getDictionarySharedPreferences(Application application) {
        return application.getSharedPreferences(Utils.DICT__SP, Context.MODE_PRIVATE);
    }

    // USER STORAGE SP
    public static void storeUserInSP(User user, Application application) {
        SharedPreferences sharedPref = getUserSharedPreferences(application);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Utils.USER__SP, new Gson().toJson(user));
        editor.apply();
    }

    public static User getUserFromSP(Application application) {
        SharedPreferences sharedPref = getUserSharedPreferences(application);
        return new Gson().fromJson(sharedPref.getString(Utils.USER__SP, ""), User.class);
    }

    // TOKEN STORAGE SP
    public static void storeTokenInSP(Token token, Application application) {
        SharedPreferences sharedPref = getUserSharedPreferences(application);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Utils.TOKEN__SP, new Gson().toJson(token));
        editor.apply();
    }

    public static Token getTokenFormSP(Application application) {
        SharedPreferences sharedPref = getUserSharedPreferences(application);
        return new Gson().fromJson(sharedPref.getString(Utils.TOKEN__SP, ""), Token.class);
    }

    public static void flushUserSP(Application application) {
        SharedPreferences sharedPref = getUserSharedPreferences(application);
        sharedPref.edit().clear().apply();
    }
}

