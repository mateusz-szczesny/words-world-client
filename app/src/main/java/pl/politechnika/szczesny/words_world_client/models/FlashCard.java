package pl.politechnika.szczesny.words_world_client.models;

import com.google.gson.annotations.SerializedName;

public class FlashCard {
    @SerializedName("id")
    private int id;
    @SerializedName("word")
    private String word;
    @SerializedName("language")
    private String languageCode;

    public FlashCard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
