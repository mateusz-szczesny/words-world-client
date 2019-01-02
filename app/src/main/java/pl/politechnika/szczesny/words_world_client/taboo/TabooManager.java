package pl.politechnika.szczesny.words_world_client.taboo;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.politechnika.szczesny.words_world_client.helper.ConstHelper;
import pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper;
import pl.politechnika.szczesny.words_world_client.model.Language;
import pl.politechnika.szczesny.words_world_client.model.User;

public class TabooManager {

    private static TabooManager INSTANCE;
    private static Application application;

    private TabooManager(Context context) {
        application = (Application)context.getApplicationContext();
    }

    public static TabooManager getInstance(Context context) throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new TabooManager(context);
        }
        return  INSTANCE;
    }

    public List getCards() throws IOException {
        List allCards = new ArrayList<>();
        List<String> files = new ArrayList<>();
        User user = SharedPrefHelper.getUserFormSP(application);
        for (Language lang : user.getSelectedLanguages()) {
            files.add(ConstHelper.lang2TabooFile.get(lang.getName()));
        }
        for(String fileName : files) {
            InputStream is = application.getApplicationContext().getAssets().open("taboo/" + fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            Type listType = new TypeToken<ArrayList<Card>>(){}.getType();
            List<Card> cards = new Gson().fromJson(new String(buffer, "UTF-8"), listType);
            allCards.addAll(cards);
        }

        Collections.shuffle(allCards);
        return allCards;
    }
}
