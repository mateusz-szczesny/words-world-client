package pl.politechnika.szczesny.words_world_client.helper;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import pl.politechnika.szczesny.words_world_client.model.Achievement;

public class FontManager {

    private static final String STRING_RESOURCE = "string";
    private static final String ROOT = "fonts/";
    public static final String FONTAWESOME = ROOT + "fontawesome-webfont.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    public static String getIconCodeForAchievement(Application application, Achievement achievement) {
        return application.getApplicationContext().getResources().getString(application.getApplicationContext().getResources()
                .getIdentifier(achievement.getBadgeIcon(), STRING_RESOURCE, application.getPackageName()));
    }
}
