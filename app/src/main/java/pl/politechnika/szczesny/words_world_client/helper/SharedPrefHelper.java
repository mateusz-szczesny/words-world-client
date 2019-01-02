package pl.politechnika.szczesny.words_world_client.helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import pl.politechnika.szczesny.words_world_client.model.Token;
import pl.politechnika.szczesny.words_world_client.model.User;


public class SharedPrefHelper {

    public static void storeUserInSP(User user, Application application) {
        SharedPreferences sharedPref = getUserSharedPreferences(application);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ConstHelper.USER__SP, new Gson().toJson(user));
        editor.apply();
    }

    public static User getUserFormSP(Application application) {
        SharedPreferences sharedPref = getUserSharedPreferences(application);
        return new Gson().fromJson(sharedPref.getString(ConstHelper.USER__SP, ""), User.class);
    }

    public static void storeTokenInSP(Token token, Application application) {
        SharedPreferences sharedPref = getUserSharedPreferences(application);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ConstHelper.TOKEN__SP, new Gson().toJson(token));
        editor.apply();
    }

    public static Token getTokenFormSP(Application application) {
        SharedPreferences sharedPref = getUserSharedPreferences(application);
        return new Gson().fromJson(sharedPref.getString(ConstHelper.TOKEN__SP, ""), Token.class);
    }

    public static void flushSP(Application application) {
        SharedPreferences sharedPref = getUserSharedPreferences(application);
        sharedPref.edit().clear().apply();
    }

    private static SharedPreferences getUserSharedPreferences(Application application) {
        return application.getSharedPreferences(ConstHelper.USER__SP, Context.MODE_PRIVATE);
    }

    public static void flushTabooScore(Application application) {
        SharedPreferences sharedPref = getUserSharedPreferences(application);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ConstHelper.TABOO_SCORE__SP, 0);
        editor.apply();
    }

    public static int getTabooScore(Application application) {
        SharedPreferences sharedPref = getUserSharedPreferences(application);
        return sharedPref.getInt(ConstHelper.TABOO_SCORE__SP, 0);
    }

    public static void incrementTabooScore(Application application, int points) {
        SharedPreferences sharedPref = getUserSharedPreferences(application);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ConstHelper.TABOO_SCORE__SP, getTabooScore(application) + points);
        editor.apply();
    }
}

