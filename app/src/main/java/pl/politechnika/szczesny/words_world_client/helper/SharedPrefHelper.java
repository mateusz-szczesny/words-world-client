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

    public static User getUserFormSP(Application application) {
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

    // TABOO GAME SP
    public static void flushTabooScore(Application application) {
        SharedPreferences sharedPref = getTabooSharedPreferences(application);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Utils.TABOO_SCORE__SP, 0);
        editor.apply();
    }

    public static int getTabooScore(Application application) {
        SharedPreferences sharedPref = getTabooSharedPreferences(application);
        return sharedPref.getInt(Utils.TABOO_SCORE__SP, 0);
    }

    public static void incrementTabooScore(Application application, int points) {
        SharedPreferences sharedPref = getTabooSharedPreferences(application);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Utils.TABOO_SCORE__SP, getTabooScore(application) + points);
        editor.apply();
    }

    // TABOO STATS SP
    public static void incrementTabooStats(Application application, int no) {
        SharedPreferences sharedPref = getTabooSharedPreferences(application);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Utils.TABOO_STAT__SP, getTabooStats(application) + no);
        editor.apply();
    }

    static void flushTabooStats(Application application) {
        SharedPreferences sharedPref = getTabooSharedPreferences(application);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Utils.TABOO_STAT__SP, 0);
        editor.apply();
    }

    static int getTabooStats(Application application) {
        SharedPreferences sharedPref = getTabooSharedPreferences(application);
        return sharedPref.getInt(Utils.TABOO_STAT__SP, 0);
    }


    // DICTIONARY SP
    public static void incrementTranslatedWords(Application application, int no) {
        SharedPreferences sharedPref = getDictionarySharedPreferences(application);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Utils.DICT__STAT__SP, getDictionaryStats(application) + no);
        editor.apply();
    }

    static void flushTranslatedWordsCount(Application application) {
        SharedPreferences sharedPref = getDictionarySharedPreferences(application);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Utils.DICT__STAT__SP, 0);
        editor.apply();
    }

    static int getDictionaryStats(Application application) {
        SharedPreferences sharedPref = getDictionarySharedPreferences(application);
        return sharedPref.getInt(Utils.DICT__STAT__SP, 0);
    }
}

