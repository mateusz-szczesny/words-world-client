package pl.politechnika.szczesny.words_world_client.helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import pl.politechnika.szczesny.words_world_client.model.User;


public class SharedPrefHelper {

    public static User getUserFormSP(Application application){
        SharedPreferences sharedPref = application.getSharedPreferences(ConstHelper.USER__SP, Context.MODE_PRIVATE);
        return new Gson().fromJson(sharedPref.getString(ConstHelper.USER_DATA__SP, ""), User.class);
    }

    public static void storeUserInSharedPrefs(User user, Application application) {
        SharedPreferences sharedPref = application.getSharedPreferences(ConstHelper.USER__SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(ConstHelper.USER_LOGIN_STATUS__SP, true);
        editor.putString(ConstHelper.USER_DATA__SP, new Gson().toJson(user));
        editor.apply();
    }

    public static void removeUserFromSP(Application application) {
        SharedPreferences sharedPref = application.getSharedPreferences(ConstHelper.USER__SP, Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
    }
}
