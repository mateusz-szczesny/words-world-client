package pl.politechnika.szczesny.words_world_client.models;

import android.app.Application;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import pl.politechnika.szczesny.words_world_client.utils.SessionUtils;
import pl.politechnika.szczesny.words_world_client.network.ApiManager;
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
                    INSTANCE.correctlyAnsFlashCards = new ArrayList<>();
                    INSTANCE.incorrectlyAnsFlashCards = new ArrayList<>();
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

    @SerializedName("correctly_swiped_cards")
    private ArrayList<Integer> correctlySwipedCards;
    @SerializedName("incorrectly_swiped_cards")
    private ArrayList<Integer> incorrectlySwipedCards;
    @SerializedName("correctly_ans_flashcards")
    private ArrayList<Integer> correctlyAnsFlashCards;
    @SerializedName("incorrectly_ans_flashcards")
    private ArrayList<Integer> incorrectlyAnsFlashCards;
    @SerializedName("translated_words")
    private Integer translatedWordsCount;

    public void addCorrectlySwipedCard(Integer id) {
        correctlySwipedCards.add(id);
    }

    public void addIncorrectlySwipedCard(Integer id) {
        incorrectlySwipedCards.add(id);
    }

    public void addCorrectlyAnsFlashCards(Integer id) {
        correctlyAnsFlashCards.add(id);
    }

    public void addIncorrectlyAnsFlashCards(Integer id) {
        incorrectlyAnsFlashCards.add(id);
    }

    public void incrementTranslatedWordsCounter() {
        translatedWordsCount++;
    }

    public void pushStatistics(final Application application) {
        String token = SessionUtils.getToken(application);
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
        correctlyAnsFlashCards = new ArrayList<>();
        incorrectlyAnsFlashCards = new ArrayList<>();
        this.translatedWordsCount = 0;
    }

    public Integer getCurrentGameScore(){
        return this.correctlySwipedCards.size() - this.incorrectlySwipedCards.size();
    }

}
