package pl.politechnika.szczesny.words_world_client.utils;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

import pl.politechnika.szczesny.words_world_client.models.Language;

public class Utils {

    static final String USER__SP = "USER__SP";
    public static final String USER__ID = "USER_ID";

    public static final String GOOGLE_API__KEY = "AIzaSyBmtY8VhmWWpfBOthj3Q728H7tt79-haFs";

    static final String TOKEN__SP = "TOKEN__SP";

    public static final int MINIMUM_PASSWORD_LENGTH = 6;
    public static final int MINIMUM_USERNAME_LENGTH = 4;

    public static final Integer MINIMAL_CARD_COUNT = 5;
    public static final Integer DEFAULT_CARD_COUNT = 5;
    public static final Integer MAXIMAL_CARD_COUNT = 15;

    public static final Map<String, Integer> tabooLevel2CardColor = new HashMap<String, Integer>() {{
        put("EASY", Color.parseColor("#e1f7d5"));
        put("MEDIUM", Color.parseColor("#c9c9ff"));
        put("HARD", Color.parseColor("#ffbdbd"));
        put("INSANE", Color.parseColor("#ffbdbd"));
        put("NOT ENOUGH STATS", Color.WHITE);
    }};

    public static final Map<String, String> flashCardLabel2DifficultyLevel = new HashMap<String, String>() {{
        put("Poziom I", "EASY");
        put("Poziom II", "MEDIUM");
        put("Poziom III", "HARD");
        put("Poziom IV", "INSANE");
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
}
