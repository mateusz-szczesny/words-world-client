package pl.politechnika.szczesny.words_world_client.helper;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class ConstHelper {
    static final String TABOO_SCORE__SP = "TABOO_SCORE__SP";
    public static final String GOOGLE_API__KEY = "AIzaSyBmtY8VhmWWpfBOthj3Q728H7tt79-haFs";
    static final String USER__SP = "USER_SP";
    static final String TOKEN__SP = "TOKEN_SP";
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
}