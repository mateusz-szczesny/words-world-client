package pl.politechnika.szczesny.words_world_client.helper;

import android.app.Application;
import android.graphics.Color;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import pl.politechnika.szczesny.words_world_client.model.Language;
import pl.politechnika.szczesny.words_world_client.model.Statistics;
import pl.politechnika.szczesny.words_world_client.model.Token;
import pl.politechnika.szczesny.words_world_client.service.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Utils {
    static final String TABOO__SP = "TABOO__SP";
    static final String TABOO_SCORE__SP = "TABOO_SCORE__SP";
    static final String TABOO_STAT__SP = "TABOO_STAT__SP";

    static final String DICT__SP = "DICT__SP";
    static final String DICT__STAT__SP = "DICT__STAT__SP";
    public static final Integer ONE = 1;

    static final String USER__SP = "USER__SP";
    public static final String USER__ID = "USER_ID";

    public static final String GOOGLE_API__KEY = "AIzaSyBmtY8VhmWWpfBOthj3Q728H7tt79-haFs";

    static final String TOKEN__SP = "TOKEN__SP";

    public static final int MINIMUM_PASSWORD_LENGTH = 6;
    public static final int MINIMUM_USERNAME_LENGTH = 4;

    public static final Map<String, String> lang2TabooFile = new HashMap<String, String>() {{
        put("Angielski", "en_EN.json");
        put("Francuski", "fr_FR.json");
        put("Hiszpański", "es_ES.json");
        put("Niemiecki", "de_DE.json");
    }};

    public static final Map<String, Integer> TabooLevel2CardColor = new HashMap<String, Integer>() {{
        put("EASY", Color.parseColor("#e1f7d5"));
        put("MEDIUM", Color.parseColor("#c9c9ff"));
        put("HARD", Color.parseColor("#ffbdbd"));
    }};

    public static final Map<String, Integer> TabooLevel2Reward = new HashMap<String, Integer>() {{
        put("EASY", 1);
        put("MEDIUM", 2);
        put("HARD", 3);
    }};

    public static final Map<String, Integer> achievementLevel2Color = new HashMap<String, Integer>() {{
        put("1", Color.parseColor("#a77044"));
        put("2", Color.parseColor("#a7a7ad"));
        put("3", Color.parseColor("#fee101"));
        put("4", Color.parseColor("#b9f2ff"));
    }};

    public static String returnFlagEmojiForLanguage(Language language) {
        int flagOffset = 0x1F1E6;
        int asciiOffset = 0x41;

        String country = language.getLanguageCode().toUpperCase();

        if ("EN".equals(country)) country = "GB";

        int firstChar = Character.codePointAt(country, 0) - asciiOffset + flagOffset;
        int secondChar = Character.codePointAt(country, 1) - asciiOffset + flagOffset;

        return new String(Character.toChars(firstChar))
                + new String(Character.toChars(secondChar));
    }

    public static void pushStatistics(final Application application) {
        int translatedWords = SharedPrefHelper.getDictionaryStats(application);
        int tabooSwipedCards = SharedPrefHelper.getTabooStats(application);

        Statistics statistics = new Statistics();
        statistics.translatedWords = translatedWords;
        statistics.correctlySwipedTabooCards = tabooSwipedCards;

        Token token = SharedPrefHelper.getTokenFormSP(application);
        ApiManager.getInstance().pushUserStatistics(token, statistics, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("STATISTICS", "SUCCESSFULLY PUSHED!");
                    SharedPrefHelper.flushTabooStats(application);
                    SharedPrefHelper.flushTranslatedWordsCount(application);
                } else {

                    Log.d("STATISTICS", "API ERROR!");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("RETROFIT", "STAT PUSH FAILED");
            }
        });
    }
}
