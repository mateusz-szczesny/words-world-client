package pl.politechnika.szczesny.words_world_client.models;

import com.google.gson.annotations.SerializedName;

public class RandomWord {
    @SerializedName("id")
    private int id;
    @SerializedName("word")
    private String word;
    @SerializedName("language")
    private Language language;

    public RandomWord() {
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
