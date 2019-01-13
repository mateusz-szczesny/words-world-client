package pl.politechnika.szczesny.words_world_client.model;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import pl.politechnika.szczesny.words_world_client.helper.SessionHelper;
import pl.politechnika.szczesny.words_world_client.service.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Statistics implements Serializable {
    private static volatile Statistics INSTANCE;

    public static Statistics getInstance() {
        if (INSTANCE == null) {
            synchronized (Statistics.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Statistics();
                    INSTANCE.translatedWordsCount = 0;
                    INSTANCE.correctlySwipedCards = new ArrayList<>();
                    INSTANCE.incorrectlySwipedCards = new ArrayList<>();
                }
            }
        }

        return INSTANCE;
    }

    private Statistics() {
        if (INSTANCE != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    @SerializedName("correctly_swiped_cards ")
    private ArrayList<Long> correctlySwipedCards;

    @SerializedName("incorrectly_swiped_cards ")
    private ArrayList<Long> incorrectlySwipedCards;

    @SerializedName("translated_words ")
    private Integer translatedWordsCount;

    public void addCorrectlySwipedCard(long id) {
        correctlySwipedCards.add(id);
    }

    public void addIncorrectlySwipedCard(long id) {
        incorrectlySwipedCards.add(id);
    }

    public void incrementTranslatedWordsCounter() {
        translatedWordsCount++;
    }

    public void pushStatistics(Application application) {
        String token = SessionHelper.getToken(application);
        ApiManager.getInstance().pushUserStatistics(token, this, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("STATISTICS", "SUCCESSFULLY PUSHED!");
                    flushStatistics();
                } else {
                    Log.d("STATISTICS", "API ERROR!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.d("STATISTICS", t.getMessage());
            }
        });
    }

    private void flushStatistics() {
        this.correctlySwipedCards = new ArrayList<>();
        this.incorrectlySwipedCards = new ArrayList<>();
        this.translatedWordsCount = 0;
    }

}
